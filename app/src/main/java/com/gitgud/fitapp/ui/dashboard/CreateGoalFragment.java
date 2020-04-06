package com.gitgud.fitapp.ui.dashboard;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.TextInputLayoutAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.Locale;


public class CreateGoalFragment extends Fragment implements Validator.ValidationListener {
    final Calendar myCalendar = Calendar.getInstance();

    public CreateGoalFragment() {
        // Required empty public constructor
    }

    private Validator validator;

    View create_goal_view;

    @NotEmpty
    TextInputLayout objective;
    TextInputEditText goal_input;
    @NotEmpty
    TextInputLayout goal_date_layout;
    TextInputEditText goal_date_text;
    RadioButton rb_calories;
    RadioButton rb_weight;
    Button save_goal;

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

        goal_date_text.setText(sdf.format(myCalendar.getTime()));
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_goal, container, false);
        this.initFagment(view);

        return view;

    }

    private void initFagment(View view) {
        rb_calories = view.findViewById(R.id.rb_calories);
        rb_weight = view.findViewById(R.id.rb_weight);
        objective = view.findViewById(R.id.objective);
        goal_input = view.findViewById(R.id.goal_input);
        goal_date_text = view.findViewById(R.id.birthdayText);
        goal_date_layout = view.findViewById(R.id.birthday);
        create_goal_view = view;
        save_goal = view.findViewById(R.id.save_goal);


        rb_calories.setOnClickListener(this::onClickRadioButton);
        rb_weight.setOnClickListener(this::onClickRadioButton);
        goal_date_text.setOnClickListener(this::onClickDatePicker);
        save_goal.setOnClickListener(this::onClickSubmit);


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
    }

    public void onClickRadioButton(View v) {
        if(rb_calories.isChecked()) {
            objective.setSuffixText("KJ");
            objective.setHint("Calories");
        } else {
            objective.setHint("Weight");
            objective.setSuffixText("KG");
        }
    }
    public void onClickDatePicker(View view) {

        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis() - 1000);
        dialog.show();
    }

    public  void onClickSubmit (View view) {
        validator.validate();
    }
    @Override
    public void onValidationSucceeded() {
        Toast.makeText(create_goal_view.getContext(), "Goal Registred!", Toast.LENGTH_SHORT).show();

        Navigation.findNavController(create_goal_view).popBackStack();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(create_goal_view.getContext());
            // Display error messages
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
                ((TextInputLayout) view).setErrorEnabled(true);
            } else {
                Toast.makeText(create_goal_view.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }




}
