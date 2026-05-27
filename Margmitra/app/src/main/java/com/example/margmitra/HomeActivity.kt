package com.example.margmitra

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
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

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Handle system bar insets (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Map Fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize Location Manager
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true


        mMap.uiSettings.isMyLocationButtonEnabled = true  // ✅ My Location button enable
        mMap.isMyLocationEnabled = true
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
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startLocationUpdates() {
        if (!::mMap.isInitialized) {
            Log.e("HomeActivity", "Map not initialized before starting location updates.")
            return
        }

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                Log.d("HomeActivity", "Location Changed: ${location.latitude}, ${location.longitude}")

                mMap.clear()
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f))

                fetchAddress(location)
            }

            override fun onProviderDisabled(provider: String) {
                Toast.makeText(this@HomeActivity, "GPS disabled. Please enable GPS.", Toast.LENGTH_LONG).show()
            }

            override fun onProviderEnabled(provider: String) {
                Toast.makeText(this@HomeActivity, "GPS enabled", Toast.LENGTH_SHORT).show()
            }
        }

        try {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000L,
                    10f,
                    locationListener
                )
            }
        } catch (e: SecurityException) {
            Log.e("HomeActivity", "SecurityException: ${e.message}")
        }
    }

    private fun fetchAddress(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            val geocoder = Geocoder(this@HomeActivity, Locale.getDefault())
            var fullAddress = "Address not found"
            try {
                val addresses: List<Address>? =
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    fullAddress = addresses[0].getAddressLine(0)
                }
            } catch (e: IOException) {
                Log.e("HomeActivity", "Geocoder IOException: ${e.message}")
                fullAddress = "Unable to fetch address"
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@HomeActivity, fullAddress, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            Toast.makeText(
                this,
                "Location permission is required to display your current location.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onStop() {
        super.onStop()
        if (::locationManager.isInitialized && ::locationListener.isInitialized) {
            locationManager.removeUpdates(locationListener)
        }
    }
}
