package com.example.mapactivity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
// Consider using FusedLocationProviderClient for modern location updates
// import com.google.android.gms.location.LocationCallback
// import com.google.android.gms.location.LocationRequest
// import com.google.android.gms.location.LocationResult
// import com.google.android.gms.location.LocationServices
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001 // Use val for constants

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Make sure R.layout.activity_main is correct

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true // Example: Enable zoom controls

        // Check for permissions and start location updates only when the map is ready
        checkLocationPermissionAndSetup()
    }

    private fun checkLocationPermissionAndSetup() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startLocationUpdates() {
        // Ensure map is initialized before adding markers
        if (!::mMap.isInitialized) {
            Log.e("MainActivity", "Map not initialized before starting location updates.")
            return
        }

        // Define location listener here or ensure it's initialized
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.d("MainActivity", "Location Changed: ${location.latitude}, ${location.longitude}")
                val currentLatLng = LatLng(location.latitude, location.longitude)

                mMap.clear() // Clear previous markers if you only want one
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f)) // Use animateCamera for smoother movement

                // Perform geocoding on a background thread
                fetchAddress(location)
            }

            override fun onProviderDisabled(provider: String) {
                Toast.makeText(
                    this@MainActivity,
                    "GPS provider disabled. Please enable GPS.",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onProviderEnabled(provider: String) {
                Toast.makeText(this@MainActivity, "GPS provider enabled.", Toast.LENGTH_SHORT)
                    .show()
            }

            @Deprecated("Deprecated in API level 29") // For older APIs
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        // Request location updates (ensure permission is granted before this call)
        try {
            // Check permission again, just in case (though checkLocationPermissionAndSetup should handle it)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000L, // 5 seconds
                    10f,   // 10 meters
                    locationListener
                )
                // Also consider Network Provider for faster initial fix or indoor scenarios
                // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10f, locationListener)
            } else {
                Log.w("MainActivity", "Location permission not granted when trying to request updates.")
            }
        } catch (e: SecurityException) {
            Log.e("MainActivity", "SecurityException requesting location updates: ${e.message}")
            Toast.makeText(this, "Location permission error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchAddress(location: Location) {
        // Use Coroutines for background work
        CoroutineScope(Dispatchers.IO).launch {
            val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
            var fullAddress = "Address not found"
            try {
                @Suppress("DEPRECATION") // getFromLocation is deprecated on API 33+ but needed for compatibility
                val addresses: List<Address>? =
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)

                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val sb = StringBuilder()
                    // Construct a more readable address
                    sb.append(address.getAddressLine(0) ?: "")
                    // Optionally add more details if needed and available
                    // val city = address.locality
                    // val state = address.adminArea
                    // val country = address.countryName
                    // val postalCode = address.postalCode
                    // if (city != null) sb.append("\nCity: $city")
                    // if (state != null) sb.append("\nState: $state")
                    // if (country != null) sb.append("\nCountry: $country")
                    fullAddress = sb.toString()
                }
            } catch (e: IOException) {
                Log.e("MainActivity", "Geocoder IOException: ${e.message}")
                fullAddress = "Service not available to fetch address."
            } catch (e: IllegalArgumentException) {
                Log.e("MainActivity", "Geocoder IllegalArgumentException: ${e.message}")
                fullAddress = "Invalid latitude or longitude."
            }

            // Switch back to the main thread to update UI (show Toast)
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, fullAddress, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                if (::mMap.isInitialized) { // Ensure map is ready
                    startLocationUpdates()
                } else {
                    // Map is not ready yet, onMapReady will call checkLocationPermissionAndSetup
                    Log.d("MainActivity", "Permission granted, map not ready yet. Will start updates in onMapReady.")
                }
            } else {
                // Permission denied
                Toast.makeText(
                    this,
                    "Location permission is required to show your current location on the map.",
                    Toast.LENGTH_LONG
                ).show()
                // Optionally, you could disable location-dependent features or guide the user to settings
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Stop location updates when the activity is no longer visible to save battery
        if (::locationManager.isInitialized && ::locationListener.isInitialized) {
            locationManager.removeUpdates(locationListener)
            Log.d("MainActivity", "Location updates stopped in onStop.")
        }
    }

    // No need for the extra OnMapReady and onMapReady(p0: GoogleMap)
}
