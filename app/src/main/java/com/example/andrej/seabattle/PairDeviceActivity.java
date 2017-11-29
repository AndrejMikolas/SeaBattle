package com.example.andrej.seabattle;

import android.app.Activity;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrej.seabattle.bluetooth_services.SampleConnectionCallback;
import com.example.andrej.seabattle.bluetooth_services.SampleConnectionFailedListener;
import com.example.andrej.seabattle.bluetooth_services.SampleDataCallback;
import com.example.andrej.seabattle.bluetooth_services.SampleDataSentCallback;
import com.newtronlabs.easybluetooth.BluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PairDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String NAME = "SeaBattle";
    private static final String APP_UUID = "53168060-cfc5-11e7-8f1a-0800200c9a66";
    private static final int REQUEST_ENABLE_BT = 1;
    /*
        ArrayAdapter<String> listAdapter;
        Button searchButton;
        ListView listView;
        BluetoothAdapter btAdapter;
        Set<BluetoothDevice> devicesArray;
        ArrayList<String> searchedDevices;
        IntentFilter filter;
        BroadcastReceiver receiver;
    */
    // Return Intent extra

    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothAdapter mBtAdapter;
    //private ArrayAdapter mPairedDevicesArrayAdapter;
    private ArrayAdapter mBtDevicesAdapter;
    private ArrayList<BluetoothDevice> mBtDevices;

    //private SmoothBluetooth mSmoothBluetooth;
    private List<Integer> mBuffer = new ArrayList<>();
    private List<String> mResponseBuffer = new ArrayList<>();
    private ArrayAdapter<String> mResponseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_device);
        setResult(Activity.RESULT_CANCELED);

        /*
        mSmoothBluetooth = new SmoothBluetooth(this);
        mSmoothBluetooth.setListener(mListener);

        mBtDevices = new ArrayList<>();
        mBtDevicesAdapter = new ArrayAdapter(this, R.layout.device_name);
        ListView btDevicesListView = (ListView) findViewById(R.id.listView_nearbyDevices);
        btDevicesListView.setAdapter(mBtDevicesAdapter);
        btDevicesListView.setOnItemClickListener(mDeviceClickListener);
        */


        mBtDevices = new ArrayList<>();
        mBtDevicesAdapter = new ArrayAdapter(this, R.layout.device_name);
        ListView btDevicesListView = (ListView) findViewById(R.id.listView_nearbyDevices);
        btDevicesListView.setAdapter(mBtDevicesAdapter);
        btDevicesListView.setOnItemClickListener(mDeviceClickListener);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        //Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        setSearchStatus("", false);
        // If there are paired devices, add each one to the ArrayAdapter

/*
        if (pairedDevices.size() > 0) {
            //findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                //mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBtDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            //mPairedDevicesArrayAdapter.add(noDevices);
            mBtDevicesAdapter.add(noDevices);
        }
        */
    }

  /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmoothBluetooth.stop();
    }

    private SmoothBluetooth.Listener mListener = new SmoothBluetooth.Listener() {
        @Override
        public void onBluetoothNotSupported() {
            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBluetoothNotEnabled() {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        @Override
        public void onConnecting(Device device) {
            setSearchStatus("Connecting to " + device.getName(), true);
        }

        @Override
        public void onConnected(Device device) {
            setSearchStatus("Connected to " + device.getName(), false);
        }

        @Override
        public void onDisconnected() {

        }

        @Override
        public void onConnectionFailed(Device device) {
            Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
            setSearchStatus("", false);
        }

        @Override
        public void onDiscoveryStarted() {
            setSearchStatus("Searching", true);
        }

        @Override
        public void onDiscoveryFinished() {
            setSearchStatus("Searching complete", false);
        }

        @Override
        public void onNoDevicesFound() {
            setSearchStatus("None found", false);
        }

        @Override
        public void onDevicesFound(final List<Device> deviceList, final SmoothBluetooth.ConnectionCallback connectionCallback) {
            final DialogFragment dialog = new DialogFragment().Builder(PairDeviceActivity.this)
                    .title("Devices")
                    .adapter(new DevicesAdapter(MainActivity.this, deviceList))
                    .build();

            ListView listView = dialog.getListView();
            if (listView != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        connectionCallback.connectTo(deviceList.get(position));
                        dialog.dismiss();
                    }

                });
            }
        }

        @Override
        public void onDataReceived(int data) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Bluetooth is required", Toast.LENGTH_LONG).show();
            finish();
        }
    }
*/


    public void refresh(){
        if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }
        else {
            doDiscovery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK){
                //String result=data.getStringExtra("result");
                doDiscovery();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth must be enabled!", Toast.LENGTH_SHORT).show();
            }
        }
    }//onActivityResult

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
        setSearchStatus("", false);
    }

    public void doDiscovery() {
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        mBtDevicesAdapter.clear();
        mBtDevices.clear();
        setSearchStatus("Searching", true);
        mBtAdapter.startDiscovery();

    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            mBtAdapter.cancelDiscovery();
            BluetoothDevice device =  mBtDevices.get(arg2);//      getItem(arg2);
            Toast.makeText(getApplicationContext(), device.getAddress(), Toast.LENGTH_SHORT).show();
            // Get the device MAC address, which is the last 17 chars in the View
            // Find the server Bluetooth device.
            BluetoothDevice serverDev = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
            IBluetoothClient client = new BluetoothClient.Builder(getApplicationContext(), serverDev, ParcelUuid.fromString(APP_UUID))
                    // We want to be notified when connection completes.
                    .setConnectionCallback(new SampleConnectionCallback())
                    // Let's also get notified if it fails
                    .setConnectionFailedListener(new SampleConnectionFailedListener())
                    // Receive data from the server
                    .setDataCallback(new SampleDataCallback())
                    // Be notified when the data is sent to the server or fails to send.
                    .setDataSentCallback(new SampleDataSentCallback())
                    .build();

// Connect to server
            client.connect();

            client.setConnectionCallback(new SampleConnectionCallback(){
                @Override
                public void onConnected(IBluetoothClient bluetoothClient) {
                    super.onConnected(bluetoothClient);
                }

                @Override
                public void onConnectionSuspended(IBluetoothClient bluetoothClient, int reason) {
                    super.onConnectionSuspended(bluetoothClient, reason);
                }
            } );
            //SampleDataCallback dataCallback = new SampleDataCallback(getApplicationContext());
            //dataCallback.onDataReceived();
            //String info = ((TextView) v).getText().toString();
            //String address = info.substring(info.length() - 17);
            // Create the result Intent and include the MAC address


            //Intent intent = new Intent();
            //intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            // Set result and finish this Activity
            //setResult(Activity.RESULT_OK, intent);
            //finish();

        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBtDevices.add(device);
                mBtDevicesAdapter.add(device.getName());
                Log.i("XXXXXXNewdevice", device.getName());
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setSearchStatus("Search finished", false);
                if (mBtDevicesAdapter.getCount() == 0) {
                    setSearchStatus("None found", false);
                }
            }
        }
    };

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_back)){
            //super.onBackPressed();
            //overridePendingTransition(R.transition.trans_bottom_in, R.transition.trans_bottom_out);
            try{
                BluetoothDevice serverDev = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("34:4D:F7:E2:98:8C");
                IBluetoothClient client = new BluetoothClient.Builder(getApplicationContext(), serverDev, ParcelUuid.fromString(APP_UUID))
                        // We want to be notified when connection completes.
                        .setConnectionCallback(new SampleConnectionCallback())
                        // Let's also get notified if it fails
                        .setConnectionFailedListener(new SampleConnectionFailedListener())
                        // Receive data from the server
                        .setDataCallback(new SampleDataCallback())
                        // Be notified when the data is sent to the server or fails to send.
                        .setDataSentCallback(new SampleDataSentCallback())
                        .build();

// Connect to server
                client.connect();
            } catch (Exception e){}

        }
        if(view == findViewById(R.id.button_searchNearby)){
            refresh();
        }
    }

    private void setSearchStatus(String status, boolean spinnerStatus){
        TextView textViewStatus = (TextView)findViewById(R.id.textView_searchStatus);
        ProgressBar progressBarSearch = (ProgressBar)findViewById(R.id.progressBar_searching);
        textViewStatus.setText(status);
        int visibility = spinnerStatus ? View.VISIBLE : View.INVISIBLE;
        progressBarSearch.setVisibility(visibility);
    }





    /*

    private void startDiscovery() {
        btAdapter.cancelDiscovery();
        btAdapter.startDiscovery();
    }

    private void getPairedDevices() {
        devicesArray = btAdapter.getBondedDevices();
        if(devicesArray.size() > 0){
            for(BluetoothDevice device:devicesArray){
                searchedDevices.add(device.getName());
            }
        }
    }

    private void init(){
        searchButton = (Button)findViewById(R.id.button_searchNearby);
        listView = (ListView)findViewById(R.id.listView_nearbyDevices);
        listView.setOnItemClickListener(this);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, 0);
        listView.setAdapter(listAdapter);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        searchedDevices = new ArrayList<String>();
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    String s = "";
                    for(int a = 0; a < searchedDevices.size(); a++){
                        if(device.getName().equals(searchedDevices.get(a))){
                            s ="(PAIRED)";
                            //listAdapter.insert(s, i);
                            Log.i("debugging", "insert");
                            break;
                        }
                    }
                    listAdapter.add(device.getName()+" "+s+" "+"\n"+device.getAddress());
                    Log.i("debugging", "insert");
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){

                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){

                }
                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                    if(btAdapter.getState() == btAdapter.STATE_OFF){
                        Intent btRequestIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(btRequestIntent, 1);
                    }
                }

            }
        };
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Bluetooth is required", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        
    }
    */
}
