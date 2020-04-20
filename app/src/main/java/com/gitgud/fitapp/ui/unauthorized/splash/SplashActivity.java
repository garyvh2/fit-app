package com.gitgud.fitapp.ui.unauthorized.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.activities.AuthorizedActivity;
import com.gitgud.fitapp.services.StepsService;
import com.gitgud.fitapp.ui.unauthorized.login.LoginActivity;
import com.gitgud.fitapp.utils.Permissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    private final int ACTIVITY_RECOGNITION_GRANTED = 1;
    SplashViewModel splashViewModel;
    Context splashContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        splashContext = this;
        setContentView(R.layout.activity_splash);
        splashViewModel =  new ViewModelProvider(this).get(SplashViewModel.class);

        try {
            if (Permissions.arePermissionsGranted(this, Arrays.asList(Manifest.permission.ACTIVITY_RECOGNITION))) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        ACTIVITY_RECOGNITION_GRANTED
                );
            } else {
                startService();
                redirectUser();
            }
        } catch (SecurityException exception) {
            Log.e("[Error]", exception.getMessage());
        }


    }

    public void redirectUser() {
        splashViewModel.getLoggedUser().observe(this, loggedUser -> {
            if(loggedUser == null) {
                Intent intent = new Intent(splashContext, LoginActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                Intent intent = new Intent(splashContext, AuthorizedActivity.class);
                startActivity(intent);
                this.finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case ACTIVITY_RECOGNITION_GRANTED: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startService();
                        redirectUser();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        this.finish();
                    }
                    return;
                }
            }
        } catch (SecurityException exception) {
            Log.e("[Error]", exception.getMessage());
        }

    }

    public void startService() {
        Intent stepsServiceIntent = new Intent(this, StepsService.class);
        startService(stepsServiceIntent);
    }
}
