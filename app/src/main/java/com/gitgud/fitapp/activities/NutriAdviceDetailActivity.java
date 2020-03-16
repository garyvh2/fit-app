package com.gitgud.fitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gitgud.fitapp.R;

public class NutriAdviceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutri_advice_detail);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String newTitle = intent.getExtras().getString("title");
        TextView tv = findViewById(R.id.txtAdviceDetailTitle);
        tv.setText(newTitle);
    }

    public void goBack(View view) {
        finish();
    }
}
