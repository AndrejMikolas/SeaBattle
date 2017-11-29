package com.example.andrej.seabattle.bluetooth_services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothConnectionFailedListener;

/**
 * Created by Andrej on 23.11.2017.
 */

public class SampleConnectionFailedListener implements IBluetoothConnectionFailedListener {
    public static final String TAG = "easyBt";

    @Override
    public void onConnectionFailed(IBluetoothClient bluetoothClient, int i)
    {
        // Connection attempt failed.
        Log.d(TAG, "Connection Failed!");
//        Toast.makeText(this.context, "Connection Failed!", Toast.LENGTH_LONG).show();
    }
}
