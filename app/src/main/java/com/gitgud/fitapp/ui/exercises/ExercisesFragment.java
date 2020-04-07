package com.gitgud.fitapp.ui.exercises;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.StepActivity;
import com.gitgud.fitapp.WaterActivity;
import com.gitgud.fitapp.adapters.MenuCardAdapter;
import com.gitgud.fitapp.components.MenuCardItem;

import java.util.ArrayList;


public class ExercisesFragment extends Fragment {


    MenuCardAdapter adapter;
    ArrayList<MenuCardItem> menuItems = new ArrayList<>();

    public  ExercisesFragment(){
        menuItems.add(new MenuCardItem(R.drawable.ic_walk, "Steps Counter", StepActivity.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_exercises, container, false);
        adapter = new MenuCardAdapter(fragmentView.getContext(), menuItems);
        ListView listView = fragmentView.findViewById(R.id.exercises_menu);
        listView.setAdapter(adapter);
        return fragmentView;
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        Button btnHistory = fragmentView.findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(fragmentView).navigate(R.id.badgesFragment);
            }
        });
        return fragmentView;
    }
}
