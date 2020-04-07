package com.gitgud.fitapp.ui.unauthorized.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.activities.AuthorizedActivity;
import com.gitgud.fitapp.adapters.TextInputLayoutAdapter;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.source.UserDataSource;
import com.gitgud.fitapp.entities.user.LoginUserQuery;
import com.gitgud.fitapp.ui.modules.steps.StepsViewModel;
import com.gitgud.fitapp.ui.unauthorized.registration.RegistrationActivity;
import com.gitgud.fitapp.utils.UserSharedPreferences;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {


    @NonNull
    private LoginViewModel loginViewModel;
    @NotEmpty
    @Email
    private TextInputLayout email;
    @NotEmpty
    private TextInputLayout password;

    private Button btnLogin;

    private Button btnSignUp;

    private Validator validator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setContentView(R.layout.activity_login);
        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.registerAdapter(TextInputLayout.class, new TextInputLayoutAdapter());
        validator.setViewValidatedAction(new Validator.ViewValidatedAction() {
            @Override
            public void onAllRulesPassed(View view) {
                if (view instanceof TextInputLayout) {
                    ((TextInputLayout) view).setError("");
                    ((TextInputLayout) view).setErrorEnabled(false);

                }
            }
        });

        initView();

    }
    private void initView() {

        email =  findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.signUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUp(view);
            }
        });
    }

    private void onLogin(View view) {
        validator.validate();

    }

    private void onSignUp(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        this.loginViewModel.loginObservable(getInputText(email), getInputText(password)).subscribe(
        this::onSuccess,
                this::onFailed
        );

    }

    private void onSuccess(LoginUserQuery.Data user) {
        User newUser =  new User(user.loginUser());
        loginViewModel.saveLoggedUser(newUser);
        loginViewModel.saveGoals(new ArrayList<Goal>());
        Intent intent = new Intent(this, AuthorizedActivity.class);
        startActivity(intent);
    }
    private void onFailed(Throwable throwable) {
        Log.e("Login", throwable.toString());
        Toast.makeText(this, "We couldn't login please try again", Toast.LENGTH_SHORT).show();
    }
    private String getInputText(TextInputLayout field) {
        return field.getEditText().getText().toString();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
                ((TextInputLayout) view).setErrorEnabled(true);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
