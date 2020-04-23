package com.gitgud.fitapp.ui.nutritional.dashboard;

import android.os.AsyncTask;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.activities.AuthorizedActivity;
import com.gitgud.fitapp.adapters.NutritionalFactsAdapter;
import com.gitgud.fitapp.adapters.NutritionalProductsAdapter;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.databinding.FragmentNutritionalDashboardBinding;
import com.gitgud.fitapp.type.ProductInputType;
import com.gitgud.fitapp.ui.dashboard.dashboard.view.DashboardFragmentDirections;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NutritionalDashboardFragment extends Fragment {

    private ArcProgress progress;
    private View fragment;
    private ProgressBar loading;
    private FragmentNutritionalDashboardBinding binding;
    private NutritionalProductsAdapter nutritionalProductsAdapter;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = fragment.findViewById(R.id.nutritionalProducts);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        nutritionalProductsAdapter = new NutritionalProductsAdapter(getContext(), new ArrayList<>());

        recyclerView.setAdapter(nutritionalProductsAdapter);

        loading = fragment.findViewById(R.id.loading);
        progress = fragment.findViewById(R.id.progress);

        loading.setVisibility(View.VISIBLE);
        AsyncTask.execute(() -> {
            User user = viewModel.getCurrentUser();
            if (user != null) {
                viewModel.getProductsByUser(user.getEmail())
                        .subscribe(
                                nutritionalProducts -> {
                                    if (nutritionalProducts.isPresent()) {
                                        nutritionalProductsAdapter.setNutritionalProducts(nutritionalProducts.get());
                                        nutritionalProductsAdapter.notifyDataSetChanged();

                                        loading.setVisibility(View.INVISIBLE);

                                        int progressItems = (int)nutritionalProducts.get().stream().mapToDouble(userFood -> userFood.product().calories()).sum();
                                        setProgress(progressItems);
                                    }
                                },
                                throwable -> {
                                    Log.e("NutritionalScannerFragment", throwable.toString());
                                }
                        );
            }
        });

        Button button = fragment.findViewById(R.id.openScanner);
        button.setOnClickListener(this::openScanner);

        return fragment;
    }

    private void setProgress(int totalProgress) {
        progress.setProgress(totalProgress);
    }

    private void openScanner(View view) {
        NavDirections action = new ActionOnlyNavDirections(R.id.action_nutritionalDashboard_to_nutritionalScanner);
        Navigation.findNavController(view).navigate(action);
    }
}
