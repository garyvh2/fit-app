package com.gitgud.fitapp.ui.exercises.routine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.ActitityAdapter;
import com.gitgud.fitapp.adapters.ExerciseAdapter;
import com.gitgud.fitapp.data.model.Exercise;

import java.util.ArrayList;
import java.util.List;


public class RoutineFragment extends Fragment {

    List<Exercise> exerciseList =  new ArrayList<>();

    public RoutineFragment() {
        Exercise exercise = new Exercise(1, "Jumping Jack", "umping Jack", "", "","",1f, 0 );

        exerciseList.add(exercise);
        exerciseList.add(exercise);exerciseList.add(exercise);
        exerciseList.add(exercise);
        exerciseList.add(exercise);
        exerciseList.add(exercise);
        exerciseList.add(exercise);
        exerciseList.add(exercise);
        exerciseList.add(exercise);exerciseList.add(exercise);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_routine, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_exercises);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ExerciseAdapter(view.getContext(), exerciseList));
        return view;
    }
}
