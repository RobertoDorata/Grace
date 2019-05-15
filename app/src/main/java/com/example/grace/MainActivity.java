package com.example.grace;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.grace.ui.main.FunMode;
import com.example.grace.ui.main.SectionsPagerAdapter;
import com.example.grace.ui.main.TrainMode;
import com.google.android.material.tabs.TabLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements FunMode.OnEmotionsButtonsListener, TrainMode.OnTrainButtonsListener {
    private BluetoothSocket btSocket;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }

        Log.d("paired: ", btAdapter.getBondedDevices().toString());
        Log.d("ooooook", "fino a qui ok");

        /*for (BluetoothDevice btDevice : btAdapter.getBondedDevices()) {
            Log.d("device: ", btDevice.getName() + ": indirizzo: " + btDevice.getAddress());
        }*/
        bluetooth();
    }

    public void bluetooth() {
        Log.d("ok", "fino a qui ok");
    }

    @Override
    public void onEmotionButtonPressed(String id) {
         switch (id) {
            case "joyButton":
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
}
