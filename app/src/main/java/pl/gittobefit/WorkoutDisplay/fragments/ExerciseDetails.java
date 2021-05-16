package pl.gittobefit.WorkoutDisplay.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import pl.gittobefit.R;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.repository.TrainingRepository;

public class ExerciseDetails extends Fragment
{
    public ExerciseDetails() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null)
        {
            activity.getSupportActionBar().hide();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_details, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        int exerciseID = args.getInt("exerciseID");


        Exercise exercise = TrainingRepository.getInstance(getContext()).getExercise(exerciseID);

        TextView displayExerciseName = getView().findViewById(R.id.exercise_details_name);
        displayExerciseName.setText(exercise.getName());

        TextView descriptionOfStartPosition = getView().findViewById(R.id.descriptionOfStartPosition);
        TextView descriptionOfCorrectExecution = getView().findViewById(R.id.descriptionOfCorrectExecution);
        TextView hints = getView().findViewById(R.id.hintsForExercise);
        LinearLayout onLoading = getView().findViewById(R.id.movieLoading);

        descriptionOfStartPosition.setText(exercise.getDescriptionOfStartPosition());
        descriptionOfCorrectExecution.setText(exercise.getDescriptionOfCorrectExecution());
        hints.setText(exercise.getHints());

        String PREFIX_VIDEO_URL = "https://static.fabrykasily.pl/atlas/";
        Uri uri = Uri.parse(PREFIX_VIDEO_URL + exercise.getVideoUrl());
        VideoView videoView = getView().findViewById(R.id.exerciseDisplay);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
                videoView.setAlpha(1);
                onLoading.setVisibility(View.GONE);
                mp.setLooping(true);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null)
        {
            activity.getSupportActionBar().show();
        }
    }
}
