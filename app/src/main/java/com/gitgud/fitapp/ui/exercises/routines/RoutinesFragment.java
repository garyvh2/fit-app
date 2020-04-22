package com.gitgud.fitapp.ui.exercises.routines;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.MenuCardAdapter;
import com.gitgud.fitapp.components.MenuCardItem;
import com.gitgud.fitapp.ui.modules.water.WaterConsumeActivity;

import java.util.ArrayList;


public class RoutinesFragment extends Fragment {

    MenuCardAdapter adapter;
    ArrayList<MenuCardItem> menuItems = new ArrayList<>();

    public RoutinesFragment() {
        menuItems.add(new MenuCardItem(R.layout.menu_routine_component ,R.drawable.arm, "Arms", R.id.routineFragment));
        menuItems.add(new MenuCardItem(R.layout.menu_routine_component ,R.drawable.leg, "Legs",  R.id.routineFragment));
        menuItems.add(new MenuCardItem(R.layout.menu_routine_component ,R.drawable.chest, "Chest",  R.id.routineFragment));
        menuItems.add(new MenuCardItem(R.layout.menu_routine_component ,R.drawable.abs, "Abs",  R.id.routineFragment));
        menuItems.add(new MenuCardItem(R.layout.menu_routine_component ,R.drawable.shoulder, "Shoulder and Back",  R.id.routineFragment));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routines, container, false);
        adapter = new MenuCardAdapter(view.getContext(), menuItems);
        ListView listView = view.findViewById(R.id.routines_menu);
        listView.setAdapter(adapter);
        return view;
    }
}
