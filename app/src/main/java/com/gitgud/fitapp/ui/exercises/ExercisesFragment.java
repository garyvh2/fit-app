package com.gitgud.fitapp.ui.exercises;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gitgud.fitapp.R;


public class ExercisesFragment extends Fragment {

    public  ExercisesFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_exercises, container, false);

        return fragmentView;
    }



}
