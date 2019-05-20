package com.example.grace.ui.main;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.grace.R;

import org.w3c.dom.Text;

public class TTS extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        context = this;
        Intent intent = getIntent();
        Button cfbutton= findViewById(R.id.CFButton);
        TextView ctext= findViewById(R.id.CText);
        EditText cfedit= findViewById((R.id.editText));
        Button inviaButton = findViewById(R.id.sendbutton);

        inviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("submit","inviato del test");

            }
        });
    }


}
