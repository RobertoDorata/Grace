package com.example.grace.ui.main;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.grace.MainActivity;
import com.example.grace.R;

public class TrainMode extends Fragment {
    MainActivity mainActivity = new MainActivity();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_mode,
                container, false);

        Button firstButton = view.findViewById(R.id.first_button);
        firstButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                firstButton.setBackgroundColor(Color.parseColor("#C5C3C6"));
                Toast.makeText(getActivity(), "Toilet!",
                        Toast.LENGTH_LONG).show();
                try {
                    ((TrainMode.OnTrainButtonsListener) mainActivity).onTrainButtonPressed("firstButton");
                }
                catch (ClassCastException cce) {
                    Log.d("cce_firstButton.onClick", "OnTrainButtonsListener is not implemented in MainActivity");
                }
            }
        });

        Button secondButton = view.findViewById(R.id.second_button);
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

        Button thirdButton = view.findViewById(R.id.third_button);
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

        Button fourthButton = view.findViewById(R.id.fourth_button);
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

        return view;
    }

    public interface OnTrainButtonsListener {
        void onTrainButtonPressed(String id);
    }
}

//TODO: quando arriverà l'info del tasto premuto sul testimone (toilet, sad, tired, ecc), la app dovrà "accendere" il tasto corrispondente e
// mostrare una notifica

//TODO: aggiungere il tasto per permettere l'invio del feedback customizzato

