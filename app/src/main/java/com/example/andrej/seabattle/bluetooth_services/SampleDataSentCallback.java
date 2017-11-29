package com.example.andrej.seabattle.bluetooth_services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothDataSentCallback;
import com.newtronlabs.easybluetooth.IBluetoothMessageEvent;

/**
 * Created by Andrej on 23.11.2017.
 */

public class SampleDataSentCallback implements IBluetoothDataSentCallback {
    private static final String TAG = "easyBt";

    @Override
    public void onDataSent(IBluetoothClient bluetoothClient, IBluetoothMessageEvent messageEvent){
        Log.d(TAG, "Data Sent: " + messageEvent.getTag() + " Data: " + new String(messageEvent.getData()));
        //Toast.makeText(this.context, "Data sent: " + messageEvent.getTag() + " Data: " + messageEvent.getData(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataSendFailed(IBluetoothClient bluetoothClient, @SendFailureReason int failureReason)
    {
        if(failureReason == REASON_DATA_FORMAT_INVALID)
        {
            Log.d(TAG, "Failed to send data. Invalid Format");
            //Toast.makeText(this.context, "Failed to send data. Invalid Format", Toast.LENGTH_LONG).show();
        }
        else if(failureReason == REASON_REMOTE_CONNECTION_CLOSED)
        {
            Log.d(TAG, "Failed to send data. Connection Lost.");
        }
    }
}

