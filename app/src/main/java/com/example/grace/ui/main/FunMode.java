package com.example.grace.ui.main;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.grace.MainActivity;
import com.example.grace.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FunMode extends Fragment {
    MainActivity mainActivity = new MainActivity();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View funModeView = inflater.inflate(R.layout.fun_mode,
                container, false);
        ImageButton joybutton = funModeView.findViewById(R.id.joy_button);
        ImageButton rageButton = funModeView.findViewById(R.id.rage_button);
        ImageButton sadnessButton = funModeView.findViewById(R.id.sadness_button);
        ImageButton fearButton = funModeView.findViewById(R.id.fear_button);
        TextInputEditText textView = funModeView.findViewById(R.id.textView);
        textView.setInputType(InputType.TYPE_NULL); //previene che la tastiera venga mostrata appena avviata la app, impedendo all'utente di modificare la textView, che sarà read only

        joybutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "joyButton!",
                        Toast.LENGTH_LONG).show();
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

}

//TODO: aggiungere imageView sopra ogni singola emozione, che si illuminino di verde/rosso a seconda se tale emozione è quella giusta o sbagliata
//TODO: aggiungere eventualmente un label sotto ogni emozione che specifichi che tipo di emozione è (es gioia)
//TODO: i bottoni della app devono restituire un feedback quando vengono premuti
