package com.gitgud.fitapp.ui.nutritional.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.activities.AuthorizedActivity;
import com.gitgud.fitapp.databinding.FragmentNutritionalDashboardBinding;
import com.gitgud.fitapp.ui.dashboard.dashboard.view.DashboardFragmentDirections;

public class NutritionalDashboardFragment extends Fragment {

    private View fragment;
    private FragmentNutritionalDashboardBinding binding;
    private NutritionalDashboardViewModel viewModel;

    public NutritionalDashboardFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.fragment_nutritional_dashboard, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nutritional_dashboard, container, false);

        viewModel = new ViewModelProvider(this).get(NutritionalDashboardViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Button button = fragment.findViewById(R.id.openScanner);
        button.setOnClickListener(this::openScanner);

        return fragment;
    }

    private void openScanner(View view) {
        NavDirections action = new ActionOnlyNavDirections(R.id.action_nutritionalDashboard_to_nutritionalScanner);
        Navigation.findNavController(view).navigate(action);
    }
}
