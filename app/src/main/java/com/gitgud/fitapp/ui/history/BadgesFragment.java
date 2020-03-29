package com.gitgud.fitapp.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.BadgeRowBinding;
import com.gitgud.fitapp.ui.modules.history.HistoryModel;
import com.gitgud.fitapp.ui.modules.pokemon.PokemonViewModel;


public class BadgesFragment extends Fragment {

    @NonNull
    private HistoryModel historyModel;
    @NonNull
    BadgeRowBinding binding;

    public BadgesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_badges, container, false);
        LinearLayout lo = (LinearLayout) view.findViewById(R.id.badgesLayout);

        View totalCalories = inflater.inflate(R.layout.total_calories, null);
        lo.addView(totalCalories);


//        for (int i = 0; i < 3; i++) {
//
//            View badgeRow = inflater.inflate(R.layout.badge_row, null);
//            lo.addView(badgeRow);
//        }
//
//        for (int i = 0; i < 3; i++) {
//
//            View badgeRow = inflater.inflate(R.layout.badge_row, null);
//            lo.addView(badgeRow);
//        }


        //  BINDING START
//        binding = DataBindingUtil.setContentView(getActivity(), R.layout.badge_row);
        binding = DataBindingUtil.inflate(inflater, R.layout.badge_row, null, false);
        historyModel = new HistoryModel(3500);
        binding.setHistory(historyModel);
        //  BINDING END
        View badgeRow = binding.getRoot();
        lo.addView(badgeRow);


//        MaterialCardView mCard = view.findViewById(R.id.goal_card);
//        mCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.goalsFragment);
//            }
//        });
        return view;
    }


}
