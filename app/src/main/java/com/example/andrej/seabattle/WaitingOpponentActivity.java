package com.example.andrej.seabattle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrej.seabattle.bluetooth_services.SampleConnectionCallback;
import com.example.andrej.seabattle.bluetooth_services.SampleDataCallback;
import com.example.andrej.seabattle.bluetooth_services.SampleDataSentCallback;
import com.newtronlabs.easybluetooth.BluetoothClient;
import com.newtronlabs.easybluetooth.BluetoothServer;
import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothServer;

public class WaitingOpponentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String NAME = "SeaBattle";
    private static final String APP_UUID = "53168060-cfc5-11e7-8f1a-0800200c9a66";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_ENABLE_DISCOVERABLE = 2;

    private BluetoothAdapter mBtAdapter;
    BluetoothClient mBluetoothClient;
    BluetoothServer mBluetoothServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_opponent);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void createServer(){
        IBluetoothServer btServer = new BluetoothServer.Builder(this.getApplicationContext(),
                NAME, ParcelUuid.fromString(APP_UUID)).build();
        if(btServer == null){
            Toast.makeText(getApplicationContext(), "Can't create server", Toast.LENGTH_SHORT).show();
        }
        else {
            IBluetoothClient btClient = btServer.accept();
            btClient.setDataCallback(new SampleDataCallback());
            btClient.setConnectionCallback(new SampleConnectionCallback());
            btClient.setDataSentCallback(new SampleDataSentCallback());
            btClient.sendData("ServerGreeting", "Hello Client".getBytes());
            //We don't want to accept any other clients.
            btServer.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        setSearchStatus("", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else if(mBtAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
            startActivityForResult(intent, REQUEST_ENABLE_DISCOVERABLE);
        }
        else {
            //doDiscovery();
            createServer();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_back)){
            super.onBackPressed();
            overridePendingTransition(R.transition.trans_bottom_in, R.transition.trans_bottom_out);
        }
        if(view == findViewById(R.id.button_startGame)){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT: {
                if(resultCode == Activity.RESULT_OK){
                    createServer();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Bluetooth must be enabled!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_ENABLE_DISCOVERABLE:{
                if(resultCode == Activity.RESULT_OK){
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
                    startActivityForResult(intent, REQUEST_ENABLE_DISCOVERABLE);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Device must be discoverable!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void setSearchStatus(String status, boolean spinnerStatus){
        TextView textViewStatus = (TextView)findViewById(R.id.textView_searchStatus);
        ProgressBar progressBarSearch = (ProgressBar)findViewById(R.id.progressBar_searching);
        textViewStatus.setText(status);
        int visibility = spinnerStatus ? View.VISIBLE : View.INVISIBLE;
        progressBarSearch.setVisibility(visibility);
    }
}
