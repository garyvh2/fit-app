package com.gitgud.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    final float inToCm = (float) 2.54;
    final float kgToLb = (float) 2.205;

    boolean metricSystem = true;

    SeekBar sliderHeight;
    SeekBar sliderWeight;

    TextView lblHeight;
    TextView lblWeight;
    TextView lblBMI;
    TextView lblBMIResult;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i);
        init();
    }

    private void init() {
        sliderHeight = findViewById(R.id.sliderHeight);
        sliderWeight = findViewById(R.id.sliderWeight);

        lblHeight = findViewById(R.id.lblHeight);
        lblWeight = findViewById(R.id.lblWeight);
        lblBMI = findViewById(R.id.lblBMI);
        lblBMIResult = findViewById(R.id.lblBMIResult);

        progressBar = findViewById(R.id.circleProgress);

        initHeightSlider();
        initWeightSlider();
        initLabels();
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
        sliderHeight = findViewById(R.id.sliderHeight);
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
        sliderWeight = findViewById(R.id.sliderWeight);
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
        if (bmi > 40) return getResources().getColor(R.color.red, getTheme());
        if (bmi > 35) return getResources().getColor(R.color.red, getTheme());
        if (bmi > 30) return getResources().getColor(R.color.orange, getTheme());
        if (bmi > 25) return getResources().getColor(R.color.colorAccent, getTheme());
        if (bmi > 18.5) return getResources().getColor(R.color.aquaGreen, getTheme());
        if (bmi > 17) return getResources().getColor(R.color.colorAccent, getTheme());
        return getResources().getColor(R.color.orange, getTheme());
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
