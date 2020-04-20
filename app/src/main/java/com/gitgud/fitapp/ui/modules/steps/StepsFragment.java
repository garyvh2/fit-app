package com.gitgud.fitapp.ui.modules.steps;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.ActitityAdapter;
import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.databinding.FragmentStepsBinding;

import org.w3c.dom.Text;

import java.text.MessageFormat;

import me.itangqi.waveloadingview.WaveLoadingView;

public class StepsFragment extends Fragment {
    private FragmentStepsBinding binding;
    private StepsViewModel viewModel;
    private View fragment;
    private Context context;

    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_steps, container, false);
        context = getActivity();
        viewModel = new ViewModelProvider(this).get(StepsViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        listenToChanges();
        return fragment;
    }

    private void listenToChanges() {
        viewModel.getTodayStepsRecord().observe(getViewLifecycleOwner(), stepsRecord -> {
            if (stepsRecord != null) {
                WaveLoadingView waveLoadingView = fragment.findViewById(R.id.waveLoadingView);
                waveLoadingView.setTopTitle(MessageFormat.format("Goal: {0}", stepsRecord.getGoal()));
                waveLoadingView.setCenterTitle(String.valueOf(stepsRecord.getSteps()));
                waveLoadingView.setProgressValue((100 * stepsRecord.getSteps()) / stepsRecord.getGoal());
            }
        });

        viewModel.getTodayActivityRecords().observe(getViewLifecycleOwner(), activityRecords -> {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerView = fragment.findViewById(R.id.recycler_activities);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new ActitityAdapter(context, activityRecords));
        });

        EditText goalInput = fragment.findViewById(R.id.goal_input);
        Button addWater = fragment.findViewById(R.id.updateStepsGoal);
        addWater.setOnClickListener((View view) -> {
            AsyncTask.execute(() -> {
                try {
                    int goal = Integer.parseInt(goalInput.getText().toString());
                    if (goal > 1) {
                        viewModel.updateSteps(goal);
                    }
                } catch (Exception e) {
                    Log.e("StepsFragment", e.getMessage());
                }
            });
        });
    }
}
