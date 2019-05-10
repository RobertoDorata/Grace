package com.example.grace.ui.main;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.grace.MainActivity;
import com.example.grace.R;

public class FunMode extends Fragment {
    MainActivity mainActivity = new MainActivity();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fun_mode,
                container, false);

        ImageButton joybutton = view.findViewById(R.id.joy_button);
        joybutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                Toast.makeText(getActivity(), "joyButton!",
                        Toast.LENGTH_LONG).show();
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("joyButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_joyButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }
            }
        });

        ImageButton rageButton = view.findViewById(R.id.rage_button);
        rageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "angerButton!",
                        Toast.LENGTH_LONG).show();
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("rageButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_rageButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }

            }
        });

        ImageButton sadnessButton = view.findViewById(R.id.sadness_button);
        sadnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sadnessButton!",
                        Toast.LENGTH_LONG).show();
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("sadnessButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_sadButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }
            }
        });

        ImageButton fearButton = view.findViewById(R.id.fear_button);
        fearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "fearButton!",
                        Toast.LENGTH_LONG).show();
                try {
                    ((OnEmotionsButtonsListener) mainActivity).onEmotionButtonPressed("fearButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_fearButton.onClick", "OnEmotionsButtonsListener is not implemented in MainActivity");
                }
            }
        });

        return view;
    }

    public interface OnEmotionsButtonsListener {
        void onEmotionButtonPressed(String id);
    }

}

//TODO: aggiungere imageView sopra ogni singola emozione, che si illuminino di verde/rosso a seconda se tale emozione è quella giusta o sbagliata
//TODO: aggiungere eventualmente un label sotto ogni emozione che specifichi che tipo di emozione è (es gioia)
//TODO: i bottoni della app devono restituire un feedback quando vengono premuti
