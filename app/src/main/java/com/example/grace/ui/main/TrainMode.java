package com.example.grace.ui.main;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import com.example.grace.MainActivity;
import com.example.grace.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

@RequiresApi(api = Build.VERSION_CODES.M)
public class TrainMode extends Fragment {
    MainActivity mainActivity = new MainActivity();
    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //NOTIFICATION

        mContext = this.getContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");
        Intent ii = new Intent(mContext.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("aaaaaa");
        bigText.setBigContentTitle("Title");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);








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
                mNotificationManager.notify(0, mBuilder.build());
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

        FloatingActionButton customFeedbackButton = view.findViewById(R.id.CFButton);
        customFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "customFeedbackButton!",
                        Toast.LENGTH_LONG).show();
                Log.d("CFButton pressed", "in TrainMode, CFButton has been pressed");
                Intent myIntent = new Intent(getActivity(), TTS.class);
                startActivity(myIntent);
            }
        });

        FloatingActionButton maxTimeDefaultFeedbackButton = view.findViewById(R.id.ClockButton);
        maxTimeDefaultFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "maxTimeDefaultFeedbackButton!",
                        Toast.LENGTH_LONG).show();
                Log.d("MTDFButton pressed", "in TrainMode, maxTimeDefaultFeedbackButton has been pressed");
                Intent myIntent = new Intent(getActivity(), TimeUntilDefaultFeedback.class);
                startActivityForResult(myIntent, 1);
            }
        });

        FloatingActionButton runTimeButton = view.findViewById(R.id.RunTimeButton);
        runTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "runTimeButton!",
                        Toast.LENGTH_LONG).show();
                Log.d("runTimeButtonPressed", "in TrainMode, runTimeButton has been pressed");
                Intent myIntent = new Intent(getActivity(), RunnedTime.class);
                startActivityForResult(myIntent, 2);
            }
        });



        return view;
    }

    public interface OnTrainButtonsListener {
        void onTrainButtonPressed(String id);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("trainMode visibile", "train mode è appena diventata visibile");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        if(requestCode == 1)
            Log.d("ORARIO RICEVUTO", dataIntent.getStringExtra("modified date"));
        else if(requestCode == 2)
            Log.d("TEMPO DI CORSA", " " + dataIntent.getStringExtra("runned time"));
    }
}

//TODO: quando arriverà l'info del tasto premuto sul testimone (toilet, sad, tired, ecc), la app dovrà "accendere" il tasto corrispondente e
// mostrare una notifica

