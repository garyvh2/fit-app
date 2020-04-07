package com.gitgud.fitapp.ui.dashboard.dashboard.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gitgud.fitapp.R;

import com.gitgud.fitapp.databinding.FragmentDashboardBinding;
import com.gitgud.fitapp.utils.UserSharedPreferences;
import com.google.android.material.card.MaterialCardView;


public class DashboardFragment extends Fragment {

    DashboardViewModel dashboardViewModel;

    private FragmentDashboardBinding binding;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dashboardViewModel = new DashboardViewModel();
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_dashboard, container, false);
        binding.setViewModel(dashboardViewModel);

        View view = binding.getRoot();
        TextView welcomeMessage = view.findViewById(R.id.welcome_title);
        welcomeMessage.setText("Welcome "+ UserSharedPreferences
                .getUserProperty(view.getContext(),"name"));



        MaterialCardView mCard = view.findViewById(R.id.goal_card);
        mCard.setOnClickListener(dashboardViewModel.getGoals()? this::goalClick : this::noGoalClick);
        return view;
    }

    public void goalClick (View v) {
        NavDirections action = DashboardFragmentDirections.actionDashboardFragmentToGoalsFragment2();
        Navigation.findNavController(v).navigate(action);
    }

    public void noGoalClick (View v) {
        NavDirections action = DashboardFragmentDirections.actionDashboardFragmentToCreateGoal();
        Navigation.findNavController(v).navigate(action);
    }



}
