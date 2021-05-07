package pl.gittobefit.WorkoutDisplay.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
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
    private int exerciseId;
    private int trainingId;

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
        exerciseId = args.getInt("exerciseID");
        trainingId = args.getInt("trainingID");

        TrainingWithForm trainingWithForm =  TrainingRepository.getInstance(getContext()).getTraining(trainingId);
        ConnectionToServer.getInstance().trainingServices.changeExercise(exerciseId, trainingWithForm.form, this);

        model = new ViewModelProvider(this).get(ChangeExerciseViewModel.class);
        model.init(exerciseId, getContext());

        generateMainView();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnChangeExercise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });

        binding.nextChangeExercise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (model.getIndexExercise() == model.getListExercises().size() - 1)
                {
                    model.getIndexChange().setValue(0);
                    model.setIndexExercise(0);
                }
                else
                {
                    model.getIndexChange().setValue((int) (model.getIndexExercise() + 1));
                    model.setIndexExercise((int) (model.getIndexExercise() + 1));
                }

            }
        });

        binding.backChangeExercise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (model.getIndexExercise() == 0)
                {
                    model.getIndexChange().setValue(model.getListExercises().size() - 1);
                    model.setIndexExercise(model.getListExercises().size() - 1);
                }
                else
                {
                    model.getIndexChange().setValue((int) (model.getIndexExercise() - 1));
                    model.setIndexExercise((int) (model.getIndexExercise() - 1));
                }

            }
        });

        final Observer<Integer> indexChangeObserver = new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer) {
                generateMainView();
            }
        };

        model.getIndexChange().observe(getViewLifecycleOwner(), indexChangeObserver);
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