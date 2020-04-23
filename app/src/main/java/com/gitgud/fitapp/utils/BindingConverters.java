package com.gitgud.fitapp.utils;

import androidx.databinding.InverseMethod;

public class BindingConverters {
    @InverseMethod("stringToFloat")
    public static String floatToString(float value) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static float stringToFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return 0.0f;
        }
    }
}
