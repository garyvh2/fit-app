package com.gitgud.fitapp.ui.exercises.routines;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.MenuCardAdapter;
import com.gitgud.fitapp.components.MenuCardItem;
import com.gitgud.fitapp.data.model.Routine;
import com.gitgud.fitapp.data.model.RoutineAndExercise;
import com.gitgud.fitapp.databinding.FragmentRoutinesBinding;
import com.gitgud.fitapp.ui.exercises.routine.RoutineViewModel;
import com.gitgud.fitapp.ui.modules.water.WaterConsumeActivity;

import java.util.ArrayList;


public class RoutinesFragment extends Fragment {

    MenuCardAdapter adapter;
    RoutineViewModel routineViewModel;
    FragmentRoutinesBinding binding;
    ArrayList<MenuCardItem> menuItems = new ArrayList<>();

    public RoutinesFragment() {
       }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_routines, container, false);
        View view = binding.getRoot();
        binding.setLifecycleOwner(this);
        routineViewModel.getRoutines().observe(getViewLifecycleOwner(), routineAndExercises -> {
            if(routineAndExercises != null) {
                for(RoutineAndExercise routine : routineAndExercises) {
                    String routineName = routine.routine.name;
                    menuItems.add(new MenuCardItem(R.layout.menu_routine_component,
                            getRoutineImage(routineName),
                            routineName,R.id.routineFragment, routine.routine.getId()));
                }
                adapter = new MenuCardAdapter(view.getContext(), menuItems);
                ListView listView = view.findViewById(R.id.routines_menu);
                listView.setAdapter(adapter);
            }

        });



        return view;
    }

    private int getRoutineImage (String name){
        if( name.contains("Arm")) return R.drawable.arm;
        if(name.contains("Leg")) return R.drawable.leg;
        if(name.contains("Chest")) return R.drawable.chest;
        if(name.contains("Abs")) return R.drawable.abs;
        return R.drawable.shoulder;

    }
}
