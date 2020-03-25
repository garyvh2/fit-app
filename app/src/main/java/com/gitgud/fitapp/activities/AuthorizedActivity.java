package com.gitgud.fitapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.fragments.DashboardFragment;
import com.gitgud.fitapp.fragments.ExercisesFragment;
import com.gitgud.fitapp.fragments.NutritionalFragment;
import com.gitgud.fitapp.fragments.RewardsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AuthorizedActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);
        //loading the default fragment
        loadFragment(new DashboardFragment());
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.navigation_fitness:
                fragment = new ExercisesFragment();
                break;
            case R.id.navigation_nutrition:
                fragment = new NutritionalFragment();
                break;
            case R.id.navigation_rewards:
                fragment = new RewardsFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
