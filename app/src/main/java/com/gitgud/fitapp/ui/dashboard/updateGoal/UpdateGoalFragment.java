package com.gitgud.fitapp.ui.dashboard.updateGoal;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        updateGoalViewModel = new ViewModelProvider(this).get(UpdateGoalViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_update_goal, container, false);
        binding.setViewModel(updateGoalViewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        Button incrementBtn =  view.findViewById(R.id.incrementBtn);
        incrementBtn.setOnClickListener(this::incrementCounter);
        Button decrementBtn = view.findViewById(R.id.decrementBtn);
        decrementBtn.setOnClickListener(this::decrementCounter);


        return view;
    }

    public  void incrementCounter (View v) {
        AsyncTask.execute(() -> {
            updateGoalViewModel.setGoalsCounter(updateGoalViewModel.getGoalsCounter().getValue() + 1);
            updateGoalViewModel.setProgress(updateGoalViewModel.getProgress().getValue() + 1);
        });
    }
    public  void decrementCounter (View v) {
        AsyncTask.execute(() -> {
            if (updateGoalViewModel.getGoalsCounter().getValue() > 0) {
                updateGoalViewModel.setGoalsCounter(updateGoalViewModel.getGoalsCounter().getValue() - 1);
                updateGoalViewModel.setProgress(updateGoalViewModel.getProgress().getValue() - 1);
            }
        });
    }

}
