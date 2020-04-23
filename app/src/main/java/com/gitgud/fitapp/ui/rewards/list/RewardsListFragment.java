package com.gitgud.fitapp.ui.rewards.list;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.NutritionalProductsAdapter;
import com.gitgud.fitapp.adapters.RewardsAdapter;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.databinding.RewardsListFragmentBinding;
import com.gitgud.fitapp.entities.product.GetProductsByUserQuery;
import com.gitgud.fitapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RewardsListFragment extends Fragment {

    private View fragment;
    private ProgressBar loading;
    private RewardsAdapter rewardsAdapter;
    private RewardsListViewModel viewModel;
    private RewardsListFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rewards_list_fragment, container, false);
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this).get(RewardsListViewModel.class);
        binding.setViewModel(viewModel);

        fragment = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = fragment.findViewById(R.id.rewards);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        rewardsAdapter = new RewardsAdapter(getContext(), new ArrayList<>(), (view, reward) -> {
            view.setCuponEnable(false);
            AsyncTask.execute(() -> {
                User user = viewModel.getCurrentUser();
                if (user != null) {
                    viewModel.insertUserReward(user.getEmail(), reward.id())
                            .subscribe(
                                    rewards -> {
                                        if (rewards.isPresent()) {
                                            Toast.makeText(getContext(), "Cupon adquired", Toast.LENGTH_SHORT).show();
                                        }
                                        view.setCuponEnable(true);
                                    },
                                    throwable -> {
                                        view.setCuponEnable(true);
                                        Toast.makeText(getContext(), "An error ocurred", Toast.LENGTH_SHORT).show();
                                        Log.e("NutritionalScannerFragment", throwable.toString());
                                    }
                            );
                }
            });
            return null;
        });

        recyclerView.setAdapter(rewardsAdapter);

        loading = fragment.findViewById(R.id.loading);

        this.setLoading(true);
        AsyncTask.execute(() -> {
            viewModel.getRewards()
                    .subscribe(
                            rewards -> {
                                if (rewards.isPresent()) {
                                    rewardsAdapter.setRewards(rewards.get());
                                    rewardsAdapter.notifyDataSetChanged();
                                    loading.setVisibility(View.INVISIBLE);
                                    this.setLoading(false);
                                }
                            },
                            throwable -> {
                                Log.e("NutritionalScannerFragment", throwable.toString());
                            }
                    );
        });

        return fragment;
    }

    private void setLoading (boolean isLoading) {
        loading.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }
}
