package com.gitgud.fitapp.ui.dashboard.dashboard.view;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gitgud.fitapp.R;

import com.gitgud.fitapp.databinding.FragmentDashboardBinding;
import com.gitgud.fitapp.ui.dashboard.updateGoal.UpdateGoalViewModel;
import com.gitgud.fitapp.ui.unauthorized.login.LoginViewModel;
import com.gitgud.fitapp.utils.UserSharedPreferences;
import com.google.android.material.card.MaterialCardView;


public class DashboardFragment extends Fragment {

    DashboardViewModel dashboardViewModel;

    private FragmentDashboardBinding binding;
    private  Boolean isGoal;
    TextView welcomeMessage;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_dashboard, container, false);
        binding.setViewModel(dashboardViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        welcomeMessage = view.findViewById(R.id.welcome_title);

        dashboardViewModel.getLoggedUser().observe(getViewLifecycleOwner(), loggedUser -> {
            welcomeMessage.setText("Welcome " +  loggedUser.getName());
        });

        dashboardViewModel.getHaveGoals().observe(getViewLifecycleOwner(), haveGoals -> {
            isGoal = haveGoals;
        });


        MaterialCardView mCard = view.findViewById(R.id.goal_card);
        mCard.setOnClickListener(this::goalClick);
        return view;
    }

    public void goalClick (View v) {
            if(isGoal) {
                NavDirections action = DashboardFragmentDirections.actionDashboardFragmentToGoalsFragment2();
                Navigation.findNavController(v).navigate(action);
            } else {
                NavDirections action = DashboardFragmentDirections.actionDashboardFragmentToCreateGoal();
                Navigation.findNavController(v).navigate(action);
            }
    }


}
