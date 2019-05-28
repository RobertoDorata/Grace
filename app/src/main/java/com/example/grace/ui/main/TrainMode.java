package com.example.grace.ui.main;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.example.grace.MainActivity;
import com.example.grace.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;

@RequiresApi(api = Build.VERSION_CODES.M)
public class TrainMode extends Fragment {
    MainActivity mainActivity = new MainActivity();
    Context mContext;
    private static final UUID HEART_RATE_SERVICE_UUID = UUID.fromString("4FAFC201-1FB5-459E-8FCC-C5C9C331914B");
    private static final UUID HEART_RATE_CHARACTERISTIC_UUID = UUID.fromString("BEB5483E-36E1-4688-B7F5-EA07361B26A8");

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //NOTIFICATION

        mContext = this.getContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");
        Intent ii = new Intent(mContext.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("aaaaaa");
        bigText.setBigContentTitle("Title");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        View view = inflater.inflate(R.layout.train_mode,
                container, false);

        MaterialButton firstButton = view.findViewById(R.id.first_button);
        firstButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                firstButton.setBackgroundColor(Color.parseColor("#C5C3C6"));
                mNotificationManager.notify(0, mBuilder.build());
                try {
                    ((TrainMode.OnTrainButtonsListener) mainActivity).onTrainButtonPressed("firstButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_firstButton.onClick", "OnTrainButtonsListener is not implemented in MainActivity");
                }
            }
        });

        MaterialButton secondButton = view.findViewById(R.id.second_button);
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondButton.setBackgroundColor(Color.parseColor("#C5C3C6"));
                Toast.makeText(getActivity(), "Target Achieved!",
                        Toast.LENGTH_LONG).show();
                try {
                    ((TrainMode.OnTrainButtonsListener) mainActivity).onTrainButtonPressed("secondButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_2Button.onClick", "OnTrainButtonsListener is not implemented in MainActivity");
                }
            }
        });

        MaterialButton thirdButton = view.findViewById(R.id.third_button);
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Sad!",
                        Toast.LENGTH_LONG).show();
                thirdButton.setBackgroundColor(Color.parseColor("#C5C3C6"));
                try {
                    ((TrainMode.OnTrainButtonsListener) mainActivity).onTrainButtonPressed("thirdButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_thirdButton.onClick", "OnTrainButtonsListener is not implemented in MainActivity");
                }
            }
        });

        MaterialButton fourthButton = view.findViewById(R.id.fourth_button);
        fourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Tired!",
                        Toast.LENGTH_LONG).show();
                fourthButton.setBackgroundColor(Color.parseColor("#C5C3C6"));
                try {
                    ((TrainMode.OnTrainButtonsListener) mainActivity).onTrainButtonPressed("fourthButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_4Button.onClick", "OnTrainButtonsListener is not implemented in MainActivity");
                }
            }

        });

        FloatingActionButton customFeedbackButton = view.findViewById(R.id.CFButton);
        customFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "customFeedbackButton!",
                        Toast.LENGTH_LONG).show();
                Log.d("CFButton pressed", "in TrainMode, CFButton has been pressed");
                Intent myIntent = new Intent(getActivity(), TTS.class);
                startActivity(myIntent);
            }
        });

        FloatingActionButton maxTimeDefaultFeedbackButton = view.findViewById(R.id.ClockButton);
        maxTimeDefaultFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ClockButton!",
                        Toast.LENGTH_LONG).show();
                Log.d("ClockButton pressed", "in TrainMode, ClockButton has been pressed");
                Intent myIntent = new Intent(getActivity(), TimeUntilDefaultFeedback.class);
                startActivityForResult(myIntent, 1);
            }
        });

        FloatingActionButton runTimeButton = view.findViewById(R.id.RunTimeButton);
        runTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "RunTimeButton!",
                        Toast.LENGTH_LONG).show();
                Log.d("runTimeButtonPressed", "in TrainMode, runTimeButton has been pressed");
                Intent myIntent = new Intent(getActivity(), RunTime.class);
                startActivityForResult(myIntent, 2);
            }
        });

        /*boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firstButton.performClick();
            }
        }, 1000);*/

        return view;
    }

    public interface OnTrainButtonsListener {
        void onTrainButtonPressed(String id);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("trainMode visibile", "train mode è appena diventata visibile");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        if(requestCode == 1)
            Log.d("ORARIO RICEVUTO", dataIntent.getStringExtra("modified date"));
        else if(requestCode == 2)
            Log.d("TEMPO DI CORSA", " " + dataIntent.getStringExtra("runned time"));
    }
}

//TODO: quando arriverà l'info del tasto premuto sul testimone (toilet, sad, tired, ecc), la app dovrà "accendere" il tasto corrispondente e
// mostrare una notifica

