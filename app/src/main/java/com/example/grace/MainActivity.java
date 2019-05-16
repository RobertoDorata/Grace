package com.example.grace;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import com.example.grace.ui.main.FunMode;
import com.example.grace.ui.main.SectionsPagerAdapter;
import com.example.grace.ui.main.TrainMode;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements FunMode.OnEmotionsButtonsListener, TrainMode.OnTrainButtonsListener {
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private String deviceaddress=("70:52:F9:F2:01:5C");
    public final static String ACTION_DATA_AVAILABLE = "de.example.BluetoothLETest.ACTION_DATA_AVAILABLE";
    public static final UUID TX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public static String EXTRA_DATA="de.example.BluetoothLETest.EXTRA_DATA";
    public BluetoothSocket bluetoothSocket;
    public UUID MY_UUID = UUID.fromString("DACE8C01-7249-43EA-B582-53CD01D7E891"); //UUID DELLA ESP32
    public BluetoothDevice myBluetoothdevice = null;
    private DataOutputStream dOut;

    {
        try {
            dOut = new DataOutputStream(bluetoothSocket.getOutputStream());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    private DataInputStream dataInputStream;

    {
        try {
            dataInputStream = new DataInputStream(bluetoothSocket.getInputStream());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //GATT
        final BluetoothManager mbluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //get the BluetoothAdapter
        mBluetoothAdapter = mbluetoothManager.getAdapter();

        connect(mbluetoothManager);

        //SOCKET
        for(BluetoothDevice bluetoothDevice : mBluetoothAdapter.getBondedDevices()) {
            if(bluetoothDevice.getName().equals("ESP32"))
                myBluetoothdevice = bluetoothDevice;
        }
        connectToDevice(myBluetoothdevice);


        //DATABASE FIREBASE
        myRef.setValue("Hello, World!");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("value", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });
        String readValue = null;
        try {
            readValue = dataInputStream.readUTF();
            Log.d("read value: ",readValue);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            receiveData(bluetoothSocket);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean connectToDevice(BluetoothDevice device){
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            mBluetoothAdapter.cancelDiscovery();
        } catch (IOException | NullPointerException e){Log.d("Error", "error creating the socket");}

        try{
            bluetoothSocket.connect();
            return true;
        } catch (IOException | NullPointerException e){
            Log.d("Error", "error connecting to the device");
            try{
                bluetoothSocket.close();
                return true;
            } catch (IOException | NullPointerException exception){Log.d("Error", "error closing the socket");}
        }
        return false;
    }

    public void connect(BluetoothManager myBluetoothManager){
        for(BluetoothDevice bluetoothDevice : mBluetoothAdapter.getBondedDevices()) {
            Log.d("devices ",bluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
        }
        //connect to the given device address
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceaddress);
        Log.d("device to connect: ",device.getName());
        mBluetoothGatt=device.connectGatt(this, true, mGattCallback);
        Boolean connesso = mBluetoothGatt.connect();
        Integer stato = Integer.valueOf(myBluetoothManager.getConnectionState(device, BluetoothProfile.GATT));
        Log.d("stato prima: ", stato.toString());
        Log.d("è connesso? ",connesso.toString());
        mBluetoothGatt.disconnect();
        stato = myBluetoothManager.getConnectionState(device, BluetoothProfile.GATT);
        Log.d("stato dopo: ", stato.toString());
        //now we get callbacks on mGattCallback
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String joyButtonValue = dataSnapshot.getValue(String.class);
                Log.d("database value: ",joyButtonValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        //mDatabase.addValueEventListener(postListener);
    }

    //get callbacks when something changes
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState== BluetoothProfile.STATE_CONNECTED){
                //device connected
                gatt.discoverServices();
                Log.d("CONNECTED ", "connected to blank");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status==BluetoothGatt.GATT_SUCCESS){
                //all Services have been discovered
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            //we are still connected to the service
            if (status==BluetoothGatt.GATT_SUCCESS){
                //send the characteristic to broadcastupdate
                broadcastupdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            //send the characteristic to broadcastupdate
            broadcastupdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    //Get the 'real' data out of characteristic
    private void broadcastupdate(final String action,final BluetoothGattCharacteristic characteristic){
        final Intent intent= new Intent(action);
        //only  when it is the right characteristic?/service?
        if (TX_CHAR_UUID.equals(characteristic.getUuid())){
            //get the 'real' data from the stream
            intent.putExtra(EXTRA_DATA, characteristic.getValue());
            //send the extracted data via LocalBroadcastManager
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        }

    }

    @Override
    public void onEmotionButtonPressed(String id) {
         switch (id) {
            case "joyButton":
                try {
                    dOut.writeByte(1);
                    Log.d("writeByte: ","writeByte = 1");
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    dOut.writeUTF("joyButton pressed");
                    Log.d("writeUTF: ","writeUTF = joyButton pressed");
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    dOut.flush();
                    Log.d("flush: ","stream flushed");
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
                mDatabase.setValue("JoyButton");
                Log.d("joyButton pressed", "in FunMode, joyButton has been pressed");
                break;

             case "rageButton":
                 Log.d("rageButton pressed", "in FunMode, rageButton has been pressed");
                 break;

             case "fearButton":
                 Log.d("fearButton pressed", "in FunMode, fearButton has been pressed");
                 break;

             case "sadnessButton":
                 Log.d("sadnessButton pressed", "in FunMode, sadnessButton has been pressed");
                 break;
         }
    }

    @Override
    public void onTrainButtonPressed(String id) {
        switch (id) {
            case "firstButton":
                Log.d("firstButton pressed", "in TrainMode, firstButton has been pressed");
                break;

            case "secondButton":
                Log.d("secondButton pressed", "in TrainMode, secondButton has been pressed");
                break;

            case "thirdButton":
                Log.d("thirdButton pressed", "in TrainMode, thirdButton has been pressed");
                break;

            case "fourthButton":
                Log.d("fourthButton pressed", "in TrainMode, fourthButton has been pressed");
                break;
        }
    }

    public void receiveData(BluetoothSocket socket) throws IOException{
        InputStream socketInputStream = socket.getInputStream();
        byte[] buffer = new byte[256];
        int bytes;

        // Keep looping to listen for received messages
        while (true) {
            try {
                bytes = socketInputStream.read(buffer);            //read bytes from input buffer
                String readMessage = new String(buffer, 0, bytes);
                // Send the obtained bytes to the UI Activity via handler
                Log.i("logging", readMessage + "");
            } catch (IOException e) {
                break;
            }
        }

    }
}

//TODO: AGGIUNGERE POSSIBILITA' DI MOSTRARE TUTTI I DISPOSITIVI BLUETOOTH DISPONIBILI, NON SOLO QUELLI IN CUI E' GIA' STATO FATTO IL PAIRING
