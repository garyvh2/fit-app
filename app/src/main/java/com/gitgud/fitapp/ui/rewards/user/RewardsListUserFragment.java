package com.gitgud.fitapp.ui.rewards.user;

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
import com.gitgud.fitapp.adapters.MyRewardsAdapter;
import com.gitgud.fitapp.adapters.RewardsAdapter;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.databinding.RewardsListFragmentBinding;
import com.gitgud.fitapp.databinding.RewardsListUserFragmentBinding;
import com.gitgud.fitapp.ui.rewards.list.RewardsListViewModel;

import java.util.ArrayList;

public class RewardsListUserFragment extends Fragment {

    private View fragment;
    private ProgressBar loading;
    private MyRewardsAdapter rewardsAdapter;
    private RewardsListUserViewModel viewModel;
    private RewardsListUserFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rewards_list_user_fragment, container, false);
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this).get(RewardsListUserViewModel.class);
        binding.setViewModel(viewModel);

        fragment = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = fragment.findViewById(R.id.rewards);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        rewardsAdapter = new MyRewardsAdapter(getContext(), new ArrayList<>());

        recyclerView.setAdapter(rewardsAdapter);

        loading = fragment.findViewById(R.id.loading);

        this.setLoading(true);
        AsyncTask.execute(() -> {
            User user = viewModel.getCurrentUser();
            if (user != null) {
                viewModel.getRewardsByUser(user.getEmail())
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
            }
        });

        return fragment;
    }

    private void setLoading (boolean isLoading) {
        loading.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }
}
