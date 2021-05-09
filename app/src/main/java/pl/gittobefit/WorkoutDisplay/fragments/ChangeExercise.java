package pl.gittobefit.WorkoutDisplay.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.gittobefit.R;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.databinding.FragmentChangeExerciseBinding;
import pl.gittobefit.databinding.FragmentTrainingStartBinding;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.running_training.fragment.TrainingStartArgs;
import pl.gittobefit.running_training.viewmodel.ChangeExerciseViewModel;
import pl.gittobefit.running_training.viewmodel.TrainingViewModel;

public class ChangeExercise extends Fragment {

    private ChangeExerciseViewModel model;
    private FragmentChangeExerciseBinding binding;

    public ChangeExercise() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null)
        {
            activity.getSupportActionBar().hide();
        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeExerciseBinding.inflate(inflater, container, false);
        Bundle args = getArguments();
        int exerciseID = args.getInt("exerciseID");
        model = new ViewModelProvider(this).get(ChangeExerciseViewModel.class);
        model.init(exerciseID, getContext());
        generateMainView();
        return binding.getRoot();
    }

    private void generateMainView(){
        getVideo();
        binding.titleExerciseChange.setText(String.format("%s", model.getExercise().getName()));
        binding.descriptionStartPosition.setText(model.getExercise().getDescriptionOfStartPosition());
        binding.descriptionOfMoveText.setText(model.getExercise().getDescriptionOfCorrectExecution());
    }

    private void getVideo() {
        Uri uri = Uri.parse(ConnectionToServer.PREFIX_VIDEO_URL + model.getExercise().getVideoUrl());
        binding.videoView.setVideoURI(uri);
        binding.videoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
        binding.videoView.setOnPreparedListener(mp ->
        {
            binding.readVideo.setVisibility(View.INVISIBLE);
            binding.videoView.start();
            mp.setLooping(true);
        });
    }
}