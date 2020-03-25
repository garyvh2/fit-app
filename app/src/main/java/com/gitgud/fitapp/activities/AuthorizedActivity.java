package com.gitgud.fitapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        Toast.makeText(DashboardActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_fitness:
                        Toast.makeText(DashboardActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_nutrition:
                        Toast.makeText(DashboardActivity.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_rewards:
                        Toast.makeText(DashboardActivity.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;


                }
                return true;
            }
        });
    }
}
