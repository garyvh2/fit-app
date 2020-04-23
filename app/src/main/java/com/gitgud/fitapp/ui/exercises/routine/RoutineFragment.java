package com.gitgud.fitapp.ui.exercises.routine;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.ActitityAdapter;
import com.gitgud.fitapp.adapters.ExerciseAdapter;
import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.data.model.Routine;
import com.gitgud.fitapp.databinding.FragmentRoutineBinding;
import com.gitgud.fitapp.ui.profile.profile.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;


public class RoutineFragment extends Fragment {

    List<Exercise> exerciseList =  new ArrayList<>();
    RoutineViewModel viewModel;
    FragmentRoutineBinding binding;
    TextView routineName;
    Routine routine;

    public RoutineFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(RoutineViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_routine, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        routineName =  view.findViewById(R.id.routine_name);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_exercises);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ExerciseAdapter(view.getContext(), exerciseList));
        viewModel.getRoutine(getArguments().getLong("id")).observe(getViewLifecycleOwner(), routineAndExercise -> {
            if(routineAndExercise != null) {
                this.routine  = routineAndExercise.routine;
                routineName.setText(routine.getName());
                recyclerView.setAdapter(new ExerciseAdapter(view.getContext(), routineAndExercise.exerciseList));
            }
        });
        Button btnStart =  view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this::startExercise);

        return view;
    }

    private  void startExercise(View v) {
        NavDirections action = RoutineFragmentDirections.actionRoutineFragmentToExerciseFragment(this.routine.getId());
        if (getArguments().getBoolean("onDashboard", false) != false ) {
            Bundle bundle =  new Bundle();
            bundle.putLong("routineId", this.routine.getId());
            Navigation.findNavController(v).navigate(R.id.exerciseFragment2, bundle);
        } else {
            Navigation.findNavController(v).navigate(action);
        }

    }
}
