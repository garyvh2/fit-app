package com.gitgud.fitapp.ui.modules.water;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.databinding.ActivityWaterConsumeBinding;

import java.text.MessageFormat;

import me.itangqi.waveloadingview.WaveLoadingView;

public class WaterConsumeActivity extends AppCompatActivity {

    @NonNull
    private ActivityWaterConsumeBinding binding;
    @NonNull
    private WaterConsumeViewModel viewModel;

    AutoCompleteTextView waterQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_consume);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_water_consume);

        viewModel = new ViewModelProvider(this).get(WaterConsumeViewModel.class);
        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        viewModel.findActivityRecordByTime().observe(this, waterRecord -> {
            if (waterRecord != null) {
                WaveLoadingView waveLoadingView = findViewById(R.id.waveLoadingView);
                waveLoadingView.setTopTitle(MessageFormat.format("Goal: {0}", waterRecord.getGoal()));
                waveLoadingView.setCenterTitle(MessageFormat.format("{0}ml", waterRecord.getQuantity()));
                waveLoadingView.setProgressValue((100 * waterRecord.getQuantity()) / waterRecord.getGoal());
            }
        });

        String[] waterConsumeOptions = new String [] {"150", "250", "500", "750", "1000", "2000"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        waterConsumeOptions);
        waterQuantity = findViewById(R.id.filled_exposed_dropdown);
        waterQuantity.setText("150");
        waterQuantity.setAdapter(adapter);

        Button addWater = findViewById(R.id.updateWater);
        addWater.setOnClickListener((View view) -> {
            WaterRecord waterRecord = viewModel.findActivityRecordByTime().getValue();
            if (waterRecord != null) {
                viewModel.updateTodayWater(waterRecord, waterRecord.getQuantity() + Integer.parseInt(waterQuantity.getText().toString()));
            }
        });
    }
}
