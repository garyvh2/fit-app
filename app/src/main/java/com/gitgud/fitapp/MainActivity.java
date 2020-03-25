package com.gitgud.fitapp;

import android.content.Intent;
import android.os.Bundle;

import com.gitgud.fitapp.ui.modules.pokemon.PokemonActivity;
import com.gitgud.fitapp.ui.modules.steps.StepsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private Button bmiBtn, waterConsumeBtn, stepsBtn, bindingBtn;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bmiBtn = findViewById(R.id.bmi);
        bmiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BMIActivity.class);
                startActivity(intent);
            }
        });

        waterConsumeBtn = findViewById(R.id.waterConsume);
        waterConsumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WaterActivity.class);
                startActivity(intent);
            }
        });


        stepsBtn = findViewById(R.id.steps);
        stepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StepsActivity.class);
                startActivity(intent);
            }
        });

        bindingBtn = findViewById(R.id.binding);
        bindingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PokemonActivity.class);
                startActivity(intent);
            }
        });

    }

}
