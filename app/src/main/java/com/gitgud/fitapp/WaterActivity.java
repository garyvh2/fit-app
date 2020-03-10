package com.gitgud.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitgud.fitapp.databinding.ActivityWaterBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;

public class WaterActivity extends AppCompatActivity {
    private int accumulated = 0;
    private final int total = 2500;
    private final int amount = 500;

    private ActivityWaterBinding binding;

    private LinearLayout water;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_water);
        binding.setViewModel(this);

        water = findViewById(R.id.water);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            water.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        }

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                accumulated += amount;
                Snackbar.make(view, MessageFormat.format("+ {0}ml", amount), Snackbar.LENGTH_SHORT).show();
                binding.invalidateAll();
            }
        });
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }


    public int getLevel() {
        if (accumulated < 1500) {
            return Color.RED;
        } else if (accumulated < 2500) {
            return Color.YELLOW;
        }
        return Color.GREEN;
    }

    public int getWaterFill () {
        float factor = getApplicationContext().getResources().getDisplayMetrics().density;
        int height = Math.round(factor * ((accumulated <= 2500 ? accumulated : 2500) * 220) / total);
        return height > 0 ? height : Math.round(1 * factor);
    }

    public String getTotalAccumulated () {
        return MessageFormat.format("{0}ml / {1}ml", accumulated, total);
    }
}
