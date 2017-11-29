package com.example.andrej.seabattle.bluetooth_services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothConnectionCallback;

/**
 * Created by Andrej on 23.11.2017.
 */

public class SampleConnectionCallback implements IBluetoothConnectionCallback {
    private static final String TAG = "easyBt";

    @Override
    public void onConnected(IBluetoothClient bluetoothClient)
    {
        // Connection successful.
        Log.d(TAG, "Connected to: " + bluetoothClient.getNodeId());
        //Toast.makeText(this.context, "Connected to: " + bluetoothClient.getNodeId(), Toast.LENGTH_LONG).show();

        // We can start sending data now.
        bluetoothClient.sendData("ClientGreeting", "Hello Server!!".getBytes());

    }

    @Override
    public void onConnectionSuspended(IBluetoothClient bluetoothClient, int reason)
    {
        // Connection lost.
        if(reason == REASON_CONNECTION_CLOSED)
        {
            Log.d(TAG, "Connection to :" +bluetoothClient.getNodeId() + " ended.");
            //Toast.makeText(this.context, "Connection to :" +bluetoothClient.getNodeId() + " ended.", Toast.LENGTH_LONG).show();
        }
    }
}
