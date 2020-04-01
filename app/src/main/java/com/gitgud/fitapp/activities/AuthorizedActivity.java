package com.gitgud.fitapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.ui.dashboard.DashboardFragment;
import com.gitgud.fitapp.ui.exercises.ExercisesFragment;
import com.gitgud.fitapp.ui.nutritional.NutritionalFragment;
import com.gitgud.fitapp.ui.rewards.RewardsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AuthorizedActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);

        setUpNavigation();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void setUpNavigation(){
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment =       (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.dashboardFragment,
                        R.id.profileFragment, R.id.nutritionalFragment,
                        R.id.exercisesFragment, R.id.rewardsFragment).build();
        NavigationUI.setupActionBarWithNavController(this,  navHostFragment.getNavController(), appBarConfiguration);
    }

}
