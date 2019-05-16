package com.example.grace;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

public class TTS extends AppCompatActivity {
    TextView ctext= findViewById(R.id.CText);
    //Button cfbutton= findViewById((R.id.CFButton));
    EditText cfedit= findViewById((R.id.editText));
    Button inviaButton = findViewById(R.id.sendbutton);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        ctext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });
    }

    private void buttonClick(){
        inviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctext.setText(cfedit.getText().toString());
            }
        });
    }
}
