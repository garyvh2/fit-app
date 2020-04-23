package com.gitgud.fitapp.ui.dashboard.goal;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.model.enums.GoalType;
import com.gitgud.fitapp.databinding.FragmentGoalsBinding;
import com.gitgud.fitapp.ui.dashboard.dashboard.view.DashboardViewModel;
import com.gitgud.fitapp.ui.dashboard.goal.GoalsFragmentDirections;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {

    GoalViewModel goalViewModel;
    FragmentGoalsBinding binding;
    ArcProgress progress;
    TextView goalDate;
    String pattern = "MMMM, dd";
    ConstraintLayout btnHistory;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    public GoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        goalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_goals, container, false);
        binding.setViewModel(goalViewModel);
        binding.setLifecycleOwner(this);
        View view  = binding.getRoot();
        Button nextGoal = view.findViewById(R.id.set_Goal);
        progress = view.findViewById(R.id.calories_progress);
        goalDate = view.findViewById(R.id.goal_date);
        nextGoal.setOnClickListener(this::nextGoalClick);
        btnHistory = view.findViewById(R.id.goal_history);

        goalViewModel.getCurrentGoal().observe(getViewLifecycleOwner(), currentGoal -> {
            if(currentGoal != null) {

                progress.setProgress(currentGoal.getProgress());
                progress.setMax(currentGoal.getGoal());
                progress.setSuffixText("/"+ currentGoal.getGoal());
                progress.setBottomText(getUnit(currentGoal.getGoalType()));
                goalDate.setText("Goal Ends: "+ getDateString(currentGoal.getDate()));
            }
        });
        btnHistory.setOnClickListener(this::onHistoryClick);
        return  view;
    }

    private String getUnit (String goalType) {
        if(goalType.equals(GoalType.RUNNING.getUrl()) ) return "Km";
        if(goalType.equals(GoalType.TIME.getUrl())) return  "min";
        return "Kg";
    }

    public void nextGoalClick (View v) {
        NavDirections action = GoalsFragmentDirections.actionGoalsFragmentToUpdateGoalFragment();
        Navigation.findNavController(v).navigate(action);
    }

    public String getDateString(String date){
        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate lDate = LocalDate.parse(date, oldFormatter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd");
        return lDate.format(formatter);
    }

    public  void onHistoryClick (View v) {
        NavDirections action = GoalsFragmentDirections.actionGoalsFragmentToGoalHistory();
        Navigation.findNavController(v).navigate(action);
    }


}
