package com.example.bluetooth
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Objects


class MainActivity : AppCompatActivity() {
    private  lateinit var adaptar : BluetoothAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


      val btn =  findViewById<Button>(R.id.button);
        btn.setOnClickListener {
            var blutooth = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager;
             adaptar = blutooth.adapter;
            if (adaptar==null){
                Toast.makeText(this,"blutooth not supported", Toast.LENGTH_LONG).show();
                return@setOnClickListener;
            }
             Toast.makeText(this,"blutooth  supported", Toast.LENGTH_LONG).show();

            if (!adaptar.isEnabled){
                val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
            }else{
                checkPermission();
            }


        }
    }

    public  fun checkPermission(){
        var requiredPermission = mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION);
        if (android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.S){
            requiredPermission.add(Manifest.permission.BLUETOOTH_SCAN);
            requiredPermission.add(Manifest.permission.BLUETOOTH_CONNECT);
        }
        var missingPermission = requiredPermission.filter {
            ContextCompat.checkSelfPermission(this,it)!= PackageManager.PERMISSION_GRANTED;
        }
        if (missingPermission.isEmpty()){
            searchBluetooth();
            Toast.makeText(this,"Serching for blutooth devices", Toast.LENGTH_LONG).show();
        }else{
            permissionLauncher.launch(missingPermission.toTypedArray());
        }
    }

    @SuppressLint("MissingPermission")
    private  var permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) @androidx.annotation.RequiresPermission(
        android.Manifest.permission.BLUETOOTH_SCAN
    ) {
        permission->
        var denaypermission = permission.filter{!it.value}
        if (denaypermission.isEmpty()){
            searchBluetooth();
            Toast.makeText(this,"all permission allowed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"all permission denay", Toast.LENGTH_LONG).show();
        }

    }

     @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
     private fun searchBluetooth(){
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND);
         registerReceiver(receiver,filter);
         if (adaptar.isDiscovering){
             adaptar.cancelDiscovery();
         }
         adaptar.startDiscovery();
    }
    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    private var receiver = object:BroadcastReceiver(){
       override fun onReceive(context: Context,intent: Intent){
           var isFound = false;
            if (intent.action== BluetoothDevice.ACTION_FOUND){
                var device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE);
                var name = device?.name;
                var mac = device?.address;
                isFound = true;
                Toast.makeText(context,"Device name $name and mac $mac", Toast.LENGTH_LONG).show()
            }
           if (intent.action== BluetoothAdapter.ACTION_DISCOVERY_FINISHED && !isFound){
               Toast.makeText(context,"No Device Found ", Toast.LENGTH_LONG).show();
           }

        };
    };


}