package com.gitgud.fitapp.ui.profile;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gitgud.fitapp.R;
import com.github.lzyzsd.circleprogress.ArcProgress;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }
    Resources.Theme theme;

    TextView lblBMI;
    TextView lblBMIResult;

    ArcProgress progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        theme = fragmentView.getContext().getTheme();
        progressBar = fragmentView.findViewById(R.id.imc_progress);
        this.setIMCColors();
        return fragmentView;
    }

    public  void  setIMCColors() {
        Float imc = Float.parseFloat(progressBar.getProgress()+progressBar.getSuffixText().trim());


        progressBar.setFinishedStrokeColor(getBMIColor(imc));
        progressBar.setTextColor(getBMIColor(imc));
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
