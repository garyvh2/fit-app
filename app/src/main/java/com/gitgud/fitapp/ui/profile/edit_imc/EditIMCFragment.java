package com.gitgud.fitapp.ui.profile.edit_imc;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.FragmentEditIMCBinding;
import com.gitgud.fitapp.entities.user.AddUserStatMutation;
import com.gitgud.fitapp.ui.profile.profile.ProfileViewModel;


public class EditIMCFragment extends Fragment {

    final float inToCm = (float) 2.54;
    final float kgToLb = (float) 2.205;

    EditIMCViewModel  editIMCViewModel;
    FragmentEditIMCBinding binding;

    boolean metricSystem = true;
    Resources.Theme theme;
    String dbId;
    long localId;

    SeekBar sliderHeight;
    SeekBar sliderWeight;

    TextView lblHeight;
    TextView lblWeight;
    TextView lblBMI;
    TextView lblBMIResult;

    ProgressBar progressBar;
    Button btnSave;
    View view;


    public EditIMCFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        editIMCViewModel = new ViewModelProvider(this).get(EditIMCViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_i_m_c, container, false);
        view = binding.getRoot();
        binding.setLifecycleOwner(this);
        init(view);

        // Set the adapter
    return view;
    }

    private void init(View view) {
        sliderHeight = view.findViewById(R.id.sliderHeight);
        sliderWeight = view.findViewById(R.id.sliderWeight);

        lblHeight = view.findViewById(R.id.lblHeight);
        lblWeight = view.findViewById(R.id.lblWeight);
        lblBMI = view.findViewById(R.id.lblBMI);
        lblBMIResult = view.findViewById(R.id.lblBMIResult);
        sliderHeight = view.findViewById(R.id.sliderHeight);
        sliderWeight = view.findViewById(R.id.sliderWeight);
        theme = view.getContext().getTheme();

        progressBar = view.findViewById(R.id.circleProgress);
        btnSave =  view.findViewById(R.id.save);
        btnSave.setOnClickListener(this::onClickSave);

        editIMCViewModel.getCurrentStat().observe(getViewLifecycleOwner(), stat -> {
            if(stat != null) {

                sliderHeight.setProgress(stat.getHeight().intValue());
                sliderWeight.setProgress(stat.getWeight().intValue());
                updateBMITV();
            }
        });

        editIMCViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if(user != null) {
                dbId = user.getDb_id();
                localId = user.getId();
            }
        });

        initHeightSlider();
        initWeightSlider();
        initLabels();
    }


    public  void onClickSave(View view) {
        Double height = Double.parseDouble(sliderHeight.getProgress()+"" );
        Double weight = Double.parseDouble(sliderWeight.getProgress()+"" );
        Double imc = weight / Math.pow(height / 100, 2);
        editIMCViewModel.saveIMC(dbId, localId,imc,height, weight).subscribe(
                this::onSuccess,
                this::onFailure
        );
    }

    private  void onSuccess(AddUserStatMutation.Data data){
        Toast.makeText(view.getContext(), "Saved", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(view).popBackStack();
    }

    private void onFailure(Throwable throwable) {
        Toast.makeText(view.getContext(), "Couldn't update your imc", Toast.LENGTH_SHORT).show();
    }

    private void initHeightSlider() {
        updateHeightSliderRange();
        sliderHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                lblHeight.setText(getHeightString(progressChangedValue));
                updateBMITV();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void initWeightSlider() {
        updateWeightSliderRange();
        sliderWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                lblWeight.setText(getWeightString(progressChangedValue));
                updateBMITV();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void initLabels() {
        lblHeight.setText(getHeightString(sliderHeight.getProgress()));
        lblWeight.setText(getWeightString(sliderWeight.getProgress()));
        updateBMITV();
    }

    private void updateHeightSliderRange() {

        if (metricSystem) {
            sliderHeight.setMin(getResources().getInteger(R.integer.min_height));
            sliderHeight.setMax(getResources().getInteger(R.integer.max_height));
            sliderHeight.setProgress((int) getResources().getInteger(R.integer.avg_height));
        } else {
            sliderHeight.setMin(getResources().getInteger(R.integer.retarded_min_height));
            sliderHeight.setMax(getResources().getInteger(R.integer.retarded_max_height));
            sliderHeight.setProgress(getResources().getInteger(R.integer.retarded_avg_height));
        }
    }

    private void updateWeightSliderRange() {
        if (metricSystem) {
            sliderWeight.setMin(getResources().getInteger(R.integer.min_weight));
            sliderWeight.setMax(getResources().getInteger(R.integer.max_weight));
            sliderWeight.setProgress(getResources().getInteger(R.integer.avg_weight));
        } else {
            sliderWeight.setMin(getResources().getInteger(R.integer.retarded_min_weight));
            sliderWeight.setMax(getResources().getInteger(R.integer.retarded_max_weight));
            sliderWeight.setProgress(getResources().getInteger(R.integer.retarded_avg_weight));
        }
    }

    private String getHeightString(float height) {
        if (metricSystem) {
            return getString(R.string.height) + ": " + height + " " + getString(R.string.metric_height);
        } else {
            int feet = (int) height / 12;
            int in = (int) height - feet * 12;
            return getString(R.string.height) + ": " + feet + "'" + (in > 0 ? in : 0) + "\"";
        }
    }

    private String getWeightString(float weight) {
        if (metricSystem) {
            return getString(R.string.weight) + ": " + weight + " " + getString(R.string.metric_weight);
        } else {
            return getString(R.string.weight) + ": " + weight + " " + getString(R.string.retarded_weight);
        }
    }

    private void updateBMITV() {
        float height = metricSystem ? sliderHeight.getProgress() : sliderHeight.getProgress() * inToCm;
        float weight = metricSystem ? sliderWeight.getProgress() : sliderWeight.getProgress() * (1 / kgToLb);
        double bmi = weight / Math.pow(height / 100, 2);
        lblBMI.setText(String.format("%.1f", bmi));
        lblBMI.setTextColor(getBMIColor(bmi));
        lblBMIResult.setText(getBMIResult(bmi));
        lblBMIResult.setTextColor(getBMIColor(bmi));
        progressBar.setProgress(getBMIProgress(bmi));
    }

    private int getBMIColor(double bmi) {
        if (bmi > 40) return getResources().getColor(R.color.red, theme);
        if (bmi > 35) return getResources().getColor(R.color.red, theme);
        if (bmi > 30) return getResources().getColor(R.color.orange, theme);
        if (bmi > 25) return getResources().getColor(R.color.colorAccent, theme);
        if (bmi > 18.5) return getResources().getColor(R.color.aquaGreen, theme);
        if (bmi > 17) return getResources().getColor(R.color.colorAccent, theme);
        return getResources().getColor(R.color.orange, theme);
    }

    private int getBMIProgress(double bmi) {
        if (bmi > 40) bmi = 40;
        return (int) (75 * (bmi/40));
    }

    private String getBMIResult(double bmi) {
        if (bmi > 40) return getResources().getString(R.string.obese3);
        if (bmi > 35) return getResources().getString(R.string.obese2);
        if (bmi > 30) return getResources().getString(R.string.obese1);
        if (bmi > 25) return getResources().getString(R.string.overweight);
        if (bmi > 18.5) return getResources().getString(R.string.normal_weight);
        if (bmi > 17) return getResources().getString(R.string.underweight);
        return getResources().getString(R.string.super_underweight);
    }



}
