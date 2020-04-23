package com.gitgud.fitapp.ui.modules.steps;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.ActitityAdapter;
import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.databinding.FragmentStepsBinding;
import com.gitgud.fitapp.utils.DateUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;

import me.itangqi.waveloadingview.WaveLoadingView;

public class StepsFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();
    private TextInputEditText calendarInput;
    TextInputEditText goalInput;

    private FragmentStepsBinding binding;
    private StepsViewModel viewModel;
    private View fragment;
    private Context context;
    private boolean INITIAL = true;

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


        goalInput = fragment.findViewById(R.id.goal_input);
        goalInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int goal = Integer.parseInt(goalInput.getText().toString());
                    if (goal > 1) {
                        viewModel.updateSteps(goal);
                    }
                } catch (Exception e) {
                    Log.e("StepsFragment", e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        calendarInput = fragment.findViewById(R.id.date_input);
        calendarInput.setOnClickListener(this::dateInputClick);
        setDateValue();

        return fragment;
    }

    private void setDateValue() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        calendarInput.setText(sdf.format(calendar.getTime()));
        viewModel.setTodayStepsRecord(calendar.getTime());
        viewModel.setTodayActivityRecords(calendar.getTime());

        if(DateUtils.isSameDay(calendar.getTime(), new Date())) {
            goalInput.setEnabled(true);
        } else {
            goalInput.setEnabled(false);
        }

        binding.invalidateAll();
        listenToChanges();
    }

    private void dateInputClick(View view) {
        DatePickerDialog.OnDateSetListener date = (dateView, year, monthOfYear, dayOfMonth) ->{
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDateValue();
        };

        DatePickerDialog dialog = new DatePickerDialog(
                view.getContext(),
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
    }

    private void listenToChanges() {
        viewModel.getTodayStepsRecord().observe(getViewLifecycleOwner(), stepsRecord -> {
            if (stepsRecord != null) {
                WaveLoadingView waveLoadingView = fragment.findViewById(R.id.waveLoadingView);
                waveLoadingView.setTopTitle(MessageFormat.format("Goal: {0}", stepsRecord.getGoal()));
                waveLoadingView.setCenterTitle(String.valueOf(stepsRecord.getSteps()));
                waveLoadingView.setProgressValue((100 * stepsRecord.getSteps()) / stepsRecord.getGoal());

                Double totalDistance = new BigDecimal(0.0013208 * stepsRecord.getSteps()).setScale(3, RoundingMode.HALF_UP).doubleValue();
                Double totalCalories = new BigDecimal(0.063 * stepsRecord.getSteps()).setScale(3, RoundingMode.HALF_UP).doubleValue();

                TextView calories = fragment.findViewById(R.id.calories);
                calories.setText(String.valueOf(totalCalories) + " kcal");

                TextView distance = fragment.findViewById(R.id.distance);
                distance.setText(String.valueOf(totalDistance) + " km");

                if(INITIAL) {
                    goalInput.setText(String.valueOf(stepsRecord.getGoal()));
                    INITIAL = false;
                }
            }
        });

        viewModel.getTodayActivityRecords().observe(getViewLifecycleOwner(), activityRecords -> {
            if (activityRecords != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                RecyclerView recyclerView = fragment.findViewById(R.id.recycler_activities);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new ActitityAdapter(context, activityRecords));
            }
        });
    }
}
