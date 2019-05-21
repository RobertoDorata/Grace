package com.example.grace.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grace.R;
import com.google.android.material.textfield.TextInputEditText;

public class TTS extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        context = this;
        TextInputEditText textInputEditText = findViewById((R.id.textInputEditText));
        Button sendButton = findViewById(R.id.sendbutton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("submit",textInputEditText.getText().toString()); //salva il testo che è stato scritto nella TextEdit, il quale sarà da inviare al testimone
                finish(); //close this activity and return to the previous one
            }
        });
    }
}
