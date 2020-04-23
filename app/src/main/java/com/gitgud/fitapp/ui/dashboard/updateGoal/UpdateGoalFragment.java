package com.gitgud.fitapp.ui.dashboard.updateGoal;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.databinding.FragmentUpdateGoalBinding;
import com.gitgud.fitapp.entities.user.UpdateUserGoalMutation;


public class UpdateGoalFragment extends Fragment {

    UpdateGoalViewModel updateGoalViewModel;
    FragmentUpdateGoalBinding binding;

    String userId;
    Goal goal;
    View view;


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

        view = binding.getRoot();

        updateGoalViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            userId = user.getDb_id();
        });

        updateGoalViewModel.getCurrentGoal().observe(getViewLifecycleOwner(), currentGoal -> {
            if(currentGoal != null) {
                this.goal = currentGoal;
                updateGoalViewModel.setProgress(currentGoal.getProgress());
            }
        });
        Button incrementBtn =  view.findViewById(R.id.incrementBtn);
        incrementBtn.setOnClickListener(this::incrementCounter);
        Button decrementBtn = view.findViewById(R.id.decrementBtn);
        decrementBtn.setOnClickListener(this::decrementCounter);
        Button updateGoal =  view.findViewById(R.id.btn_update);
        updateGoal.setOnClickListener(this::updateGoal);

        return view;
    }

    public  void updateGoal(View v) {
        goal.setProgress(updateGoalViewModel.getProgress().getValue());
        updateGoalViewModel.update(userId, goal).subscribe(
                this::onSuccess,
                this::onFailure
        );
        updateGoalViewModel.updateGoalCounter();
        Navigation.findNavController(v).popBackStack();
    }

    public  void onSuccess(UpdateUserGoalMutation.Data updatedGoal) {
        updateGoalViewModel.updateGoalCounter();
        Toast.makeText(view.getContext(), "Goal Updated!", Toast.LENGTH_SHORT).show();

        Navigation.findNavController(view).popBackStack();
    }
    private void onFailure(Throwable throwable) {
        Toast.makeText(view.getContext(), "Couldn't update your goal", Toast.LENGTH_SHORT).show();
    }

    public  void incrementCounter (View v) {
        AsyncTask.execute(() -> {
            if(goal != null && updateGoalViewModel.getProgress().getValue() < goal.getGoal()) {
                updateGoalViewModel.setGoalsCounter(updateGoalViewModel.getGoalsCounter().getValue() + 1);
                updateGoalViewModel.setProgress(updateGoalViewModel.getProgress().getValue() + 1);
            }

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
