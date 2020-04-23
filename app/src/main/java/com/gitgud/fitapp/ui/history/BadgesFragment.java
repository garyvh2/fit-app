package com.gitgud.fitapp.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.BadgeCaloriesTotalBinding;
import com.gitgud.fitapp.databinding.BadgeRowCaloriesBinding;
import com.gitgud.fitapp.databinding.BadgeRowStepsBinding;
import com.gitgud.fitapp.ui.modules.history.HistoryModel;


public class BadgesFragment extends Fragment {

    @NonNull
    private HistoryModel historyModel;
    @NonNull
    BadgeCaloriesTotalBinding bindingTotalCalories;
    BadgeRowStepsBinding bindingStepsRow;
    BadgeRowCaloriesBinding bindingCaloriesRow;

    public BadgesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_badges, container, false);
        LinearLayout lo = (LinearLayout) view.findViewById(R.id.badgesLayout);

        //  BINDING START
        bindingTotalCalories = DataBindingUtil.inflate(inflater, R.layout.badge_calories_total, null, false);
        bindingStepsRow = DataBindingUtil.inflate(inflater, R.layout.badge_row_steps, null, false);
        bindingCaloriesRow = DataBindingUtil.inflate(inflater, R.layout.badge_row_calories, null, false);

        historyModel = new ViewModelProvider(this).get(HistoryModel.class);

        bindingTotalCalories.setHistory(historyModel);
        bindingStepsRow.setHistory(historyModel);
        bindingCaloriesRow.setHistory(historyModel);
        //  BINDING END

        View badgeTC = bindingTotalCalories.getRoot();
        lo.addView(badgeTC);
        View badgeRowS = bindingStepsRow.getRoot();
        lo.addView(badgeRowS);
        View badgeRowC = bindingCaloriesRow.getRoot();
        lo.addView(badgeRowC);

        return view;
    }


}
