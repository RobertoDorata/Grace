package com.example.grace;
import android.Manifest;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements FunMode.OnEmotionsButtonsListener, TrainMode.OnTrainButtonsListener {

    ArrayList mScanFilter = new ArrayList<ScanFilter>();
    ScanSettings mScanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).setReportDelay(0)
                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).build();

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private BluetoothDevice bluetoothDevice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Context mContext = getBaseContext();
        BluetoothAdapter mAdapter = ((BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        BluetoothLeScanner mBluetoothLeScanner = mAdapter.getBluetoothLeScanner();

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
                Log.d("error", "Failed to read value.", error.toException());
            }
        });

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);

        BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                if(newState == BluetoothProfile.STATE_CONNECTED)
                    Log.d("connesso", "connesso");
            }
        };

        ScanCallback mBLEScan = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Log.d("FOUND: ", "" + result.getDevice().getName());
                if(result.getDevice().getName() != null) {
                    if(result.getDevice().getName().equals("Roberto’s MacBook Air")) {
                        bluetoothDevice = result.getDevice();
                        //Log.d("OK STOP", "Blank");
                        bluetoothDevice.connectGatt(getApplicationContext(), true, bluetoothGattCallback);
                    }
                }
            }
        };

        //mBluetoothLeScanner.startScan(mScanFilter, mScanSettings, mBLEScan);
    }



    @Override
    public void onEmotionButtonPressed(String id) {
         switch (id) {
            case "joyButton":
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

//TODO: AGGIUNGERE POSSIBILITA' DI MOSTRARE TUTTI I DISPOSITIVI BLUETOOTH DISPONIBILI, NON SOLO QUELLI IN CUI E' GIA' STATO FATTO IL PAIRING
