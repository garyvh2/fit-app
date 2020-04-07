package com.gitgud.fitapp.ui.nutritional;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.WaterActivity;
import com.gitgud.fitapp.activities.AuthorizedActivity;
import com.gitgud.fitapp.adapters.MenuCardAdapter;
import com.gitgud.fitapp.components.MenuCardItem;

import java.util.ArrayList;


public class NutritionalFragment extends Fragment {


    MenuCardAdapter adapter;
    ArrayList<MenuCardItem> menuItems = new ArrayList<>();

    public NutritionalFragment() {
       menuItems.add(new MenuCardItem(R.drawable.ic_bottle, "Water Consumption", WaterActivity.class));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_nutritional, container, false);
        adapter = new MenuCardAdapter(fragmentView.getContext(), menuItems);
        ListView listView = fragmentView.findViewById(R.id.nutritional_menu);
        listView.setAdapter(adapter);
        return fragmentView;
    }



}
