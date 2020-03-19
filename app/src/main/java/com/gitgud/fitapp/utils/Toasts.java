package com.gitgud.fitapp.utils;

import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class Toasts {
    static public void show(View view, String text)
    {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
