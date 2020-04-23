package com.gitgud.fitapp.ui.exercises.exercise;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.data.model.Exercise;
import com.gitgud.fitapp.databinding.FragmentExerciseBinding;
import com.gitgud.fitapp.ui.exercises.routine.RoutineViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.annotations.NonNull;


public class ExerciseFragment extends Fragment {


    ExerciseViewModel exerciseViewModel;
    FragmentExerciseBinding binding;
    List<Exercise> exerciseList;
    TextView amountExercise;
    TextView title;
    TextView description;
    Button btnNext;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer mYouTubePlayer;

    public ExerciseFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_exercise, container, false);
        View view = binding.getRoot();
        binding.setViewModel(exerciseViewModel);
        binding.setLifecycleOwner(this);
        this.setExerciseObserver();
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        exerciseViewModel.getRoutine(getArguments().getLong("routineId"))
                .observe(getViewLifecycleOwner(), routineAndExercise -> {
                    if(routineAndExercise != null) {
                        exerciseList = routineAndExercise.exerciseList;
                        Exercise exercise = exerciseList.get(exerciseViewModel.getIndex());
                        exerciseViewModel.setCurrentExercise(exercise);
                        exerciseViewModel.increaseIndex();
                    }
                });
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this::onNextClick);
        title =  view.findViewById(R.id.exercise_title);
        description = view.findViewById(R.id.exercise_description);
        amountExercise =  view.findViewById(R.id.exercise_repetition);

        // Inflate the layout for this fragment
        return view;
    }

    public  void setExerciseObserver () {
        exerciseViewModel.getCurrentExercise().observe(getViewLifecycleOwner(), exercise -> {
            if(exercise != null) {
                if(exercise.getTutorial() != "") {
                    String videoId = exercise.getTutorial().split("/")[3];

                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            mYouTubePlayer = youTubePlayer;
                            youTubePlayer.cueVideo(videoId, 0);
                        }
                    });
                    if(mYouTubePlayer != null) {
                        mYouTubePlayer.cueVideo(videoId, 0);
                    }
                }

                amountExercise.setText(exerciseViewModel.getAmountExercise());
                description.setText(exercise.getDescription());
                title.setText(exercise.getName());

            }

        });
    }


    public  void onNextClick(View v) {
        int currentIndex = exerciseViewModel.getIndex();
        if(currentIndex <= exerciseList.size() - 1) {
            Exercise exercise = exerciseList.get(exerciseViewModel.getIndex());
            exerciseViewModel.setCurrentExercise(exercise);
            exerciseViewModel.increaseIndex();
            if(exerciseViewModel.getIndex() == exerciseList.size()) {
                btnNext.setText("Finish");
                btnNext.setOnClickListener(this::onFinish);
            }
        }

    }

    public  void onFinish(View v) {
        Navigation.findNavController(v).popBackStack();
    }
}
