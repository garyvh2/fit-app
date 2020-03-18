package com.gitgud.fitapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.ActivityBindingBinding;
import com.gitgud.fitapp.models.BindingViewModel;

public class BindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);

        //  BINDING START
        ActivityBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_binding);
        BindingViewModel bindingEjemploModel = new BindingViewModel();
        binding.setViewModel(bindingEjemploModel);
        //  BINDING END
    }
}
