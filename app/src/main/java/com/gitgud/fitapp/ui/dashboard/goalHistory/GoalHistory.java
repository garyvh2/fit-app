package com.gitgud.fitapp.ui.dashboard.goalHistory;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.GoalHistoryAdapter;
import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.databinding.FragmentGoalHistoryBinding;
import com.gitgud.fitapp.ui.exercises.exercise.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalHistory extends Fragment {

    GoalHistoryViewModel goalHistoryViewModel;
    FragmentGoalHistoryBinding binding;
    RecyclerView goals;
    List<Goal> goalList =  new ArrayList<>();


    public GoalHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        goalHistoryViewModel = new ViewModelProvider(this).get(GoalHistoryViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_goal_history, container, false);
        binding.setLifecycleOwner(this);
        View view  = binding.getRoot();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        goals = view.findViewById(R.id.goals_recycler);
        goals.setLayoutManager(layoutManager);
        goals.setHasFixedSize(true);
        goals.setAdapter(new GoalHistoryAdapter(view.getContext(), this.goalList));
        goalHistoryViewModel.getGoalList().observe(getViewLifecycleOwner(), goalsList -> {
            if(goalsList != null) {
                this.goalList = goalsList;
                goals.setAdapter(new GoalHistoryAdapter(view.getContext(), this.goalList));
                goals.addItemDecoration(new DividerItemDecoration(goals.getContext(), DividerItemDecoration.VERTICAL));
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
