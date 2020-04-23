package com.gitgud.fitapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.provider.database.AppDatabase;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wajahatkarim3.roomexplorer.RoomExplorer;

import java.util.ArrayList;

public class AuthorizedActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    ArrayList<Integer> noToolbarFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);
        noToolbarFragments.add(R.id.dashboardFragment);
        noToolbarFragments.add(R.id.profileFragment);
        noToolbarFragments.add(R.id.nutritionalFragment);
        noToolbarFragments.add(R.id.exercisesFragment);
        noToolbarFragments.add(R.id.rewardsFragment);
        setUpNavigation();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            RoomExplorer.show(this, AppDatabase.class, "app_database");
        }
        return super.onKeyDown(keyCode, event);
    }


    public void setUpNavigation(){
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment =       (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,
                navController);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.dashboardFragment,
                        R.id.profileFragment, R.id.nutritionalFragment,
                        R.id.exercisesFragment, R.id.rewardsFragment).build();
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if(noToolbarFragments.contains(destination.getId())) {
                    toolbar.setVisibility(View.GONE);

                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
        NavigationUI.setupActionBarWithNavController(this,  navController, appBarConfiguration);
    }

}
