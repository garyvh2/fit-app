package com.gitgud.fitapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.gitgud.fitapp.R;
import com.gitgud.fitapp.ui.unauthorized.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(this, AuthorizedActivity.class);
        startActivity(intent);
    }
}
