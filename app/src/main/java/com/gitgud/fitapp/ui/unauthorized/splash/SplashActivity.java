package com.gitgud.fitapp.ui.unauthorized.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.activities.AuthorizedActivity;
import com.gitgud.fitapp.ui.unauthorized.login.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    SplashViewModel splashViewModel;
    Context splashContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        splashContext = this;
        setContentView(R.layout.activity_splash);
        splashViewModel =  new ViewModelProvider(this).get(SplashViewModel.class);
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
}
