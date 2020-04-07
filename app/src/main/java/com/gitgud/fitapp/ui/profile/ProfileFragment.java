package com.gitgud.fitapp.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gitgud.fitapp.BMIActivity;
import com.gitgud.fitapp.R;
import com.gitgud.fitapp.utils.UserSharedPreferences;
import com.github.lzyzsd.circleprogress.ArcProgress;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }
    Resources.Theme theme;

    TextView userName;
    TextView email;
    TextView birthday;


    ArcProgress progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        theme = fragmentView.getContext().getTheme();
        ImageView editImc = fragmentView.findViewById(R.id.edit_imc);
        editImc.setOnClickListener(this::onEditImcClick);
        ImageView editProfile =fragmentView.findViewById(R.id.edit_profile);

        userName = fragmentView.findViewById(R.id.full_name);
        userName.setText(UserSharedPreferences.getUserProperty(fragmentView.getContext(), "name")+ " " + UserSharedPreferences.getUserProperty(fragmentView.getContext(), "lastname"));
        email = fragmentView.findViewById(R.id.user_email);
        email.setText(UserSharedPreferences.getUserProperty(fragmentView.getContext(), "email"));
        birthday = fragmentView.findViewById(R.id.user_birthdate);
        birthday.setText(UserSharedPreferences.getUserProperty(fragmentView.getContext(), "birthday"));

        progressBar = fragmentView.findViewById(R.id.imc_progress);
        this.setIMCColors();
        return fragmentView;
    }

    public  void  setIMCColors() {
        Float imc = Float.parseFloat(progressBar.getProgress()+progressBar.getSuffixText().trim());


        progressBar.setFinishedStrokeColor(getBMIColor(imc));
        progressBar.setTextColor(getBMIColor(imc));
    }

    public void onEditImcClick (View v) {
        Intent intent = new Intent(v.getContext(), BMIActivity.class);
        startActivity(intent);
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
