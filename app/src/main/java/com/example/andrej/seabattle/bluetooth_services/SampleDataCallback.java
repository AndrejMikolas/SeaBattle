package com.example.andrej.seabattle.bluetooth_services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.newtronlabs.easybluetooth.IBluetoothDataReceivedCallback;
import com.newtronlabs.easybluetooth.IBluetoothMessageEvent;

/**
 * Created by Andrej on 23.11.2017.
 */

public class SampleDataCallback implements IBluetoothDataReceivedCallback {
    private static final String TAG = "easyBt";

    @Override
    public void onDataReceived(IBluetoothMessageEvent messageEvent)
    {
        // Data was received.
        //Toast.makeText(this.context, "Received: " + messageEvent.getTag() + " Data: " + messageEvent.getData(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "Received: " + messageEvent.getTag() + " Data: " + new String(messageEvent.getData()));
    }
}
