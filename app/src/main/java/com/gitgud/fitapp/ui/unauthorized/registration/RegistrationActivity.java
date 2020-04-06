package com.gitgud.fitapp.ui.unauthorized.registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import com.gitgud.fitapp.ui.unauthorized.login.LoginActivity;
import com.gitgud.fitapp.adapters.TextInputLayoutAdapter;
import com.gitgud.fitapp.databinding.ActivityRegistrationBinding;
import com.gitgud.fitapp.entities.user.AddUserMutation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;
import java.util.Locale;

import io.reactivex.annotations.NonNull;

public class RegistrationActivity extends AppCompatActivity implements Validator.ValidationListener{
    final Calendar myCalendar = Calendar.getInstance();

    @NonNull
    private RegistrationViewModel registrationViewModel;

    private ActivityRegistrationBinding binding;

    @NotEmpty
    TextInputLayout name;
    @NotEmpty
    TextInputLayout lastName;
    @NotEmpty
    @Email
    TextInputLayout email;
    @NotEmpty
    TextInputLayout birthday;
    EditText birthdayText;
    @Password
    TextInputLayout password;
    @ConfirmPassword
    TextInputLayout confirmPassword;

    private Validator validator;


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdayText.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        registrationViewModel = RegistrationModule.createViewModel();
        binding.setViewModel(registrationViewModel);
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

    private  void initView() {
        myCalendar.set(Calendar.YEAR, 1980);
        myCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        myCalendar.set(Calendar.DAY_OF_MONTH, 1);
        birthdayText = findViewById(R.id.birthdayText);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        birthday = findViewById(R.id.birthday);
    }


    public  void onClickSubmit (View view) {
        validator.validate();
    }
    @Override
    public void onValidationSucceeded() {
        registrationViewModel.addUserObservable(getInputText(name), getInputText(lastName),
                getInputText(email), getInputText(password), getInputText(birthday)).subscribe(
                this::onRegisteredUser,
                throwable -> Log.e("[Registration]", throwable.toString())
        );


    }

    public  void onRegisteredUser(AddUserMutation.Data user) {
        registrationViewModel.setLoading(false);
        Toast.makeText(this,  user.addUser().name() + " Registered!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

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
    public void onClick(View view) {
        // TODO Auto-generated method stub
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
