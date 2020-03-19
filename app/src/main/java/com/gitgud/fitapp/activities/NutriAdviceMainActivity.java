package com.gitgud.fitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gitgud.fitapp.R;

public class NutriAdviceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutri_advice_main);
    }

    public void goToDetail(View view) {
        Intent intent = new Intent(this, NutriAdviceDetailActivity.class);
        intent.putExtra("title", ((Button) view).getText());
        startActivity(intent);
    }

    public void goBack(View view) {
        finish();
    }
}
