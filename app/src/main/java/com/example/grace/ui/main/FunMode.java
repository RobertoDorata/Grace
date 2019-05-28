package com.example.grace.ui.main;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.grace.MainActivity;
import com.example.grace.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.UUID;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FunMode extends Fragment {
    private static final UUID HEART_RATE_SERVICE_UUID = UUID.fromString("6217FF49-AC7B-547E-EECF-016A06970BA9");
    private static final UUID HEART_RATE_CHARACTERISTIC_UUID = UUID.fromString("6217FF4A-B07D-5DEB-261E-2586752D942E");
    private BluetoothAdapter bluetoothAdapter;
    private boolean mScanning = false; //usato nel thread 
    private Handler handler = new Handler();

    private BluetoothGatt bluetoothGatt;

    private static final long SCAN_PERIOD = 5000;

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("device trovato: ", " " + result.getDevice().getName());
                    if (result.getDevice().getName() != null) {
                        if (result.getDevice().getName().equals("Polar HR Sensor")) {
                            bluetoothGatt = result.getDevice().connectGatt(getContext(), true, gattCallback);
                            Log.d("connesso a ", result.getDevice().getName());
                        }
                    }
                }
            });
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    private final BluetoothGattCallback gattCallback =
            new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                                    int newState) {
                    Log.d("HEY1","");
                    if (newState == STATE_CONNECTED){
                        gatt.discoverServices();
                    }
                }

                @Override
                // New services discovered
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                }

                @Override
                // Result of a characteristic read operation
                public void onCharacteristicRead(BluetoothGatt gatt,
                                                 BluetoothGattCharacteristic characteristic,
                                                 int status) {
                    gatt.readCharacteristic(characteristic);
                }

                @Override
                public void onCharacteristicWrite (BluetoothGatt gatt,
                                                   BluetoothGattCharacteristic characteristic,
                                                   int status) {

                    //characteristic.setValue(0x01,BluetoothGattCharacteristic.FORMAT_SINT8,0);
                    gatt.writeCharacteristic(characteristic);
                }

                @Override
                public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status){
                }

                @Override
                public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                    Log.d("BLE", "Received characteristics changed event : "+characteristic.getUuid());

                }
            };

    MainActivity mainActivity = new MainActivity();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 10);
        }
        scanLeDevice(true);
        /*BluetoothGattService bluetoothGattService = bluetoothGatt.getService(HEART_RATE_SERVICE_UUID);
        BluetoothGattCharacteristic bluetoothGattCharacteristic112 = bluetoothGattService.getCharacteristic(HEART_RATE_CHARACTERISTIC_UUID);
        bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic112, true);
        BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic112.getDescriptor(HEART_RATE_SERVICE_UUID);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View funModeView = inflater.inflate(R.layout.fun_mode,
                container, false);
        MaterialButton joybutton = funModeView.findViewById(R.id.joy_button);
        MaterialButton rageButton = funModeView.findViewById(R.id.rage_button);
        MaterialButton sadnessButton = funModeView.findViewById(R.id.sadness_button);
        MaterialButton fearButton = funModeView.findViewById(R.id.fear_button);
        TextInputEditText textView = funModeView.findViewById(R.id.textView);
        textView.setInputType(InputType.TYPE_NULL); //previene che la tastiera venga mostrata appena avviata la app, impedendo all'utente di modificare la textView, che sarà read only

        joybutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "joyButton!",
                        Toast.LENGTH_LONG).show();
                BluetoothGattService bluetoothGattService = bluetoothGatt.getService(HEART_RATE_SERVICE_UUID);
                if(bluetoothGattService != null) {
                    Log.d("FINO A QUI OK", "");
                    BluetoothGattCharacteristic bluetoothGattCharacteristic112 = bluetoothGattService.getCharacteristic(HEART_RATE_CHARACTERISTIC_UUID);
                    byte[] data_to_write = new byte[]{0};
                    bluetoothGatt.connect();
                    bluetoothGattCharacteristic112.setValue(data_to_write);
                    Boolean scritta = bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic112);
                    bluetoothGatt.disconnect();
                    Log.d("SCRITTAAAA",scritta.toString());
                }
                //Boolean letta = bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic112);
                //Log.d("LETTAA", letta.toString());
                Log.d("joyButton pressed", "in FunMode, joyButton has been pressed");
                textView.setText("joy");
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("joyButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_joyButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }
            }
        });

        rageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "angerButton!",
                        Toast.LENGTH_LONG).show();
                BluetoothGattService bluetoothGattService = bluetoothGatt.getService(HEART_RATE_SERVICE_UUID);
                BluetoothGattCharacteristic bluetoothGattCharacteristic112 = bluetoothGattService.getCharacteristic(HEART_RATE_CHARACTERISTIC_UUID);
                byte[] data_to_write = new byte[]{1};
                bluetoothGatt.connect();
                bluetoothGattCharacteristic112.setValue(data_to_write);
                Boolean scritta = bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic112);
                bluetoothGatt.disconnect();
                Log.d("SCRITTAAAA",scritta.toString());
                Log.d("rageButton pressed", "in FunMode, rageButton has been pressed");
                textView.setText("rage");
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("rageButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_rageButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }
            }
        });

        sadnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sadnessButton!",
                        Toast.LENGTH_LONG).show();
                BluetoothGattService bluetoothGattService = bluetoothGatt.getService(HEART_RATE_SERVICE_UUID);
                BluetoothGattCharacteristic bluetoothGattCharacteristic112 = bluetoothGattService.getCharacteristic(HEART_RATE_CHARACTERISTIC_UUID);
                byte[] data_to_write = new byte[]{2};
                //bluetoothGattStatic.bluetoothGatt.connect();
                bluetoothGatt.connect();
                bluetoothGattCharacteristic112.setValue(data_to_write);
                Boolean scritta = bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic112);
                //bluetoothGattStatic.bluetoothGatt.disconnect();
                bluetoothGatt.disconnect();
                Log.d("SCRITTAAAA",scritta.toString());
                textView.setText("sadness");
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("sadnessButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_sadButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }
            }
        });

        fearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "fearButton!",
                        Toast.LENGTH_LONG).show();
                BluetoothGattService bluetoothGattService = bluetoothGatt.getService(HEART_RATE_SERVICE_UUID);
                BluetoothGattCharacteristic bluetoothGattCharacteristic112 = bluetoothGattService.getCharacteristic(HEART_RATE_CHARACTERISTIC_UUID);
                byte[] data_to_write = new byte[]{3};
                //bluetoothGattStatic.bluetoothGatt.connect();
                bluetoothGatt.connect();
                bluetoothGattCharacteristic112.setValue(data_to_write);
                Boolean scritta = bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic112);
                //bluetoothGattStatic.bluetoothGatt.disconnect();
                bluetoothGatt.disconnect();
                Log.d("SCRITTAAAA",scritta.toString());
                //bluetoothGattStatic.bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic112, true);
                bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic112, true);
                textView.setText("fear");
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("fearButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_fearButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }
            }
        });

        return funModeView;

    }

    public interface OnEmotionsButtonsListener {
        void onEmotionButtonPressed(String id);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("funMode visibile", "fun mode è appena diventata visibile");
        }
    }

    private void scanLeDevice(final boolean enable) {
        final BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothLeScanner.stopScan(scanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothLeScanner.startScan(scanCallback);
        } else {
            mScanning = false;
            bluetoothLeScanner.stopScan(scanCallback);
        }
    }
}

//TODO: aggiungere imageView sopra ogni singola emozione, che si illuminino di verde/rosso a seconda se tale emozione è quella giusta o sbagliata
//TODO: aggiungere eventualmente un label sotto ogni emozione che specifichi che tipo di emozione è (es gioia)
//TODO: i bottoni della app devono restituire un feedback quando vengono premuti
