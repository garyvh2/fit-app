package com.gitgud.fitapp.ui.dashboard.updateGoal;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.FragmentUpdateGoalBinding;


public class UpdateGoalFragment extends Fragment {

    UpdateGoalViewModel updateGoalViewModel;
    FragmentUpdateGoalBinding binding;


    public UpdateGoalFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateGoalViewModel = new UpdateGoalViewModel();
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_update_goal, container, false);
        binding.setViewModel(updateGoalViewModel);
        View view = binding.getRoot();
        Button incrementBtn =  view.findViewById(R.id.incrementBtn);
        incrementBtn.setOnClickListener(this::incrementCounter);
        Button decrementBtn = view.findViewById(R.id.decrementBtn);
        decrementBtn.setOnClickListener(this::decrementCounter);


        return view;
    }

    public  void incrementCounter (View v) {
        Integer counter = updateGoalViewModel.getGoalsCounter();
        Integer progress = updateGoalViewModel.getProgress();
        updateGoalViewModel.setGoalsCounter(++counter);
        updateGoalViewModel.setProgress(++progress);
    }
    public  void decrementCounter (View v) {
        Integer counter = updateGoalViewModel.getGoalsCounter();
        Integer progress = updateGoalViewModel.getProgress();
        if(counter > 0) {
            updateGoalViewModel.setGoalsCounter(--counter);
            updateGoalViewModel.setProgress(--progress);
        }

    }

}
