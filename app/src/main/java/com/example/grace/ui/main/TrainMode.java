package com.example.grace.ui.main;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.grace.R;

public class TrainMode extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fun_mode, container, false);
    }
}

//TODO: quando arriverà l'info del tasto premuto sul testimone (toilet, sad, tired, ecc), la app dovrà "accendere" il tasto corrispondente e
// mostrare una notifica

//TODO: aggiungere il tasto per permettere l'invio del feedback customizzato

