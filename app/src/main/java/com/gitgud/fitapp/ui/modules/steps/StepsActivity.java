package com.gitgud.fitapp.ui.modules.steps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.ActitityAdapter;
import com.gitgud.fitapp.databinding.ActivityStepsBinding;
import com.gitgud.fitapp.provider.database.AppDatabase;
import com.wajahatkarim3.roomexplorer.RoomExplorer;

import java.text.MessageFormat;

import me.itangqi.waveloadingview.WaveLoadingView;

public class StepsActivity extends AppCompatActivity {
    @NonNull
    private ActivityStepsBinding binding;
    @NonNull
    private StepsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_steps);
        viewModel = new ViewModelProvider(this).get(StepsViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        listenToChanges();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            RoomExplorer.show(this, AppDatabase.class, "app_database");
        }
        return super.onKeyDown(keyCode, event);
    }

    public void listenToChanges() {
        viewModel.getTodayStepsRecord().observe(this, stepsRecord -> {
            if (stepsRecord != null) {
                WaveLoadingView waveLoadingView = findViewById(R.id.waveLoadingView);
                waveLoadingView.setTopTitle(MessageFormat.format("Goal: {0}", stepsRecord.getGoal()));
                waveLoadingView.setCenterTitle(String.valueOf(stepsRecord.getSteps()));
                waveLoadingView.setProgressValue((100 * stepsRecord.getSteps()) / stepsRecord.getGoal());
            }
        });

        viewModel.getTodayActivityRecords().observe(this, activityRecords -> {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerView = findViewById(R.id.recycler_activities);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new ActitityAdapter(this, activityRecords));
        });
    }
}
