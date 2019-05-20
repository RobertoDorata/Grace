package com.example.grace.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.grace.MainActivity;
import com.example.grace.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class TTS extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        context = this;
        Intent intent = getIntent();
        Button cfbutton= findViewById(R.id.CFButton);
        TextInputLayout ctext= findViewById(R.id.CText);
        TextInputEditText cfedit= findViewById((R.id.editText));
        Button inviaButton = findViewById(R.id.sendbutton);

        inviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("submit",cfedit.getText().toString()); //salva il testo che è stato scritto nella TextEdit, il quale sarà da inviare al testimone
                finish(); //close this activity and return to the previous one
            }
        });
    }
}
