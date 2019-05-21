package com.example.grace.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.example.grace.R;

public class TimeUntilDefaultFeedback extends AppCompatActivity {

    TimePicker.OnTimeChangedListener onTimeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            Integer oraScelta = Integer.valueOf(hourOfDay);
            Integer minutoScelto = Integer.valueOf(minute);
            Log.d("ora scelta: ", oraScelta.toString());
            Log.d("minuto scelto: ",minutoScelto.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_until_default_feedback);
        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(onTimeChangedListener);
    }
}
