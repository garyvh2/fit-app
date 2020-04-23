package com.gitgud.fitapp.ui.rewards;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.WaterActivity;
import com.gitgud.fitapp.adapters.MenuCardAdapter;
import com.gitgud.fitapp.components.MenuCardItem;

import java.util.ArrayList;

public class RewardsFragment extends Fragment {



    MenuCardAdapter adapter;
    ArrayList<MenuCardItem> menuItems = new ArrayList<>();

    public RewardsFragment() {
        // Required empty public constructor
        menuItems.add(new MenuCardItem(R.drawable.ic_stars, "Milestones", R.id.badgesFragment2));
        menuItems.add(new MenuCardItem(R.drawable.ic_sale, "Rewards", R.id.rewardsListFragment));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_rewards, container, false);
        adapter = new MenuCardAdapter(fragmentView.getContext(), menuItems);
        ListView listView = fragmentView.findViewById(R.id.rewards_menu);
        listView.setAdapter(adapter);
        return fragmentView;

    }


}
