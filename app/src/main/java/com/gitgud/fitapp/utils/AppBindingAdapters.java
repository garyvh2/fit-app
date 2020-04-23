package com.gitgud.fitapp.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

public class AppBindingAdapters {
    @BindingAdapter("android:text")
    public static void setText(TextView view, Float oldValue, Float newValue) {
        if (newValue == null) {
            view.setText(String.valueOf(0.0f));
            return;
        }

        if (oldValue == null || Float.compare(oldValue, newValue) != 0) {
            view.setText(String.valueOf(newValue));
        }
    }

    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    public static Float getTextString(TextView view) {
        try {
            return Float.valueOf(view.getText().toString());
        } catch (Exception e) {
            return 0.0f;
        }
    }
}
