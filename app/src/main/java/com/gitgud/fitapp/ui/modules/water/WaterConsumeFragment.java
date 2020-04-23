package com.gitgud.fitapp.ui.modules.water;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.databinding.ActivityWaterConsumeBinding;
import com.gitgud.fitapp.databinding.FragmentWaterConsumeBinding;

import java.text.MessageFormat;

import me.itangqi.waveloadingview.WaveLoadingView;

public class WaterConsumeFragment extends Fragment {

    @NonNull
    private FragmentWaterConsumeBinding binding;
    @NonNull
    private WaterConsumeViewModel viewModel;

    AutoCompleteTextView waterQuantity;

    private View fragment;

    public WaterConsumeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.fragment_water_consume, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_water_consume, container, false);

        viewModel = new ViewModelProvider(this).get(WaterConsumeViewModel.class);
        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        viewModel.findActivityRecordByTime().observe(getViewLifecycleOwner(), waterRecord -> {
            if (waterRecord != null) {
                WaveLoadingView waveLoadingView = fragment.findViewById(R.id.waveLoadingView);
                waveLoadingView.setTopTitle(MessageFormat.format("Goal: {0}", waterRecord.getGoal()));
                waveLoadingView.setCenterTitle(MessageFormat.format("{0}ml", waterRecord.getQuantity()));
                waveLoadingView.setProgressValue((100 * waterRecord.getQuantity()) / waterRecord.getGoal());
            }
        });

        String[] waterConsumeOptions = new String [] {"150", "250", "500", "750", "1000", "2000"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.dropdown_menu_popup_item,
                        waterConsumeOptions);
        waterQuantity = fragment.findViewById(R.id.filled_exposed_dropdown);
        waterQuantity.setText("150");
        waterQuantity.setAdapter(adapter);

        Button addWater = fragment.findViewById(R.id.updateWater);
        addWater.setOnClickListener((View view) -> {
            WaterRecord waterRecord = viewModel.findActivityRecordByTime().getValue();
            if (waterRecord != null) {
                viewModel.updateTodayWater(waterRecord, waterRecord.getQuantity() + Integer.parseInt(waterQuantity.getText().toString()));
            }
        });

        EditText goalInput = fragment.findViewById(R.id.water_goal_input);
        Button updateWaterGoal = fragment.findViewById(R.id.updateWaterGoal);
        updateWaterGoal.setOnClickListener((View view) -> {
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

        return fragment;
    }
}
