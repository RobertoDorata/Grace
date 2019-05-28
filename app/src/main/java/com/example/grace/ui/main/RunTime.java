package com.example.grace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.grace.R;

import static java.lang.Integer.valueOf;

public class RunTime extends AppCompatActivity {
    private Intent intent = new Intent();
    private String modifiedData;

    TimePicker.OnTimeChangedListener onTimeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            Integer oraScelta = valueOf(hourOfDay);
            Integer minutoScelto = valueOf(minute);
            modifiedData = oraScelta.toString() + "_" + minutoScelto.toString();
            Log.d("ora scelta: ", oraScelta.toString());
            Log.d("minuto scelto: ", minutoScelto.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_until_default_feedback);
        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(onTimeChangedListener);
        Button sendButton = findViewById(R.id.sendbutton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salva il tempo che è stato salvato nel timePicker, il quale sarà da inviare al testimone
                intent.putExtra("runned time", modifiedData);
                setResult(RESULT_OK, intent);
                finish(); //close this activity and return to the previous one
            }
        });
    }
}
