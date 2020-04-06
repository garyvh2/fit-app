package com.gitgud.fitapp.ui.dashboard;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gitgud.fitapp.R;
import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {


    public GoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_goals, container, false);
        Button nextGoal = view.findViewById(R.id.set_Goal);
        nextGoal.setOnClickListener(this::nextGoalClick);
        return  view;
    }

    public void nextGoalClick (View v) {
        NavDirections action = GoalsFragmentDirections.actionGoalsFragmentToUpdateGoalFragment();
        Navigation.findNavController(v).navigate(action);
    }


}
