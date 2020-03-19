package com.gitgud.fitapp.adapters;

import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;

public class TextInputLayoutAdapter implements ViewDataAdapter<TextInputLayout, String> {

    @Override
    public String getData(final TextInputLayout til) {
        return getText(til);
    }

    private String getText(TextInputLayout til) {
        return til.getEditText().getText().toString();
    }
}