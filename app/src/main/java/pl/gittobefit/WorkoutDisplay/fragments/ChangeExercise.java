package pl.gittobefit.WorkoutDisplay.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.databinding.FragmentChangeExerciseBinding;
import pl.gittobefit.databinding.FragmentTrainingStartBinding;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.running_training.fragment.TrainingStartArgs;
import pl.gittobefit.running_training.viewmodel.ChangeExerciseViewModel;
import pl.gittobefit.running_training.viewmodel.TrainingViewModel;
import pl.gittobefit.user.User;

public class ChangeExercise extends Fragment {

    private ChangeExerciseViewModel model;
    private FragmentChangeExerciseBinding binding;
    private int exerciseId;
    private int trainingId;
    private int position;
    private int controlSum;
    private TrainingWithForm trainingWithForm;


    public ChangeExercise()
    {
    }
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
        position = args.getInt("position");
        controlSum = args.getInt("sum");

        trainingWithForm =  TrainingRepository.getInstance(getContext()).getTraining(trainingId);
        ConnectionToServer.getInstance().trainingServices.changeExercise(exerciseId, trainingWithForm.form, this);

        model = new ViewModelProvider(this).get(ChangeExerciseViewModel.class);

        model.init(exerciseId, getContext());

        generateMainView(0);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        int trainingDay = getTrainingDayFromSum();
        System.out.println(trainingDay);
        binding.btnChangeExercise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                trainingWithForm.training
                        .getPlanList()
                        .get(trainingDay)
                        .get(position)
                        .setExerciseId(
                                model.getListExercises().get((int) model.getIndexExercise()).getId()
                        );

                Exercise exercise = model.getTrainingRepository().
                        getExercise(model.getListExercises().get((int) model.getIndexExercise()).getId());

                if (exercise == null)
                {
                    AppDataBase.getInstance(getContext())
                            .exerciseDao()
                            .addExercise(model.getListExercises().get((int) model.getIndexExercise()));
                }

                AppDataBase.getInstance(getContext())
                        .trainingDao()
                        .updateTrainingPlan( trainingWithForm.training.getPlanList(), trainingWithForm.training.getCircuitsCount(), trainingId);

                Handler handler = new Handler();
                IShowSnackbar activity = (IShowSnackbar) getActivity();
                activity.showSnackbar("Trwa wymiana ...");
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Bundle args = new Bundle();
                        args.putInt("exerciseChanged", 1);
                        if (!User.getInstance().getLoggedBy().equals(User.WayOfLogin.NO_LOGIN))
                        {
                            ConnectionToServer.getInstance().trainingServices.saveTrainingAfterChanges(activity, getContext(), trainingId);
                        }
                        else
                        {
                            activity.showSnackbar(getContext().getResources().getString(R.string.editionComplete));
                        }
                        Navigation.findNavController(view).navigate(R.id.change_exercise_to_list_of_trainings, args);
                    }
                }, 1000);
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
                generateMainView(integer);
            }
        };

        model.getIndexChange().observe(getViewLifecycleOwner(), indexChangeObserver);
    }

    private int getTrainingDayFromSum() {
        trainingWithForm.training.getPlanList().get(0);
        int i;
        for (i = 0; i < trainingWithForm.training.getPlanList().size(); i++) {
            int sum = 0;
            for (int j = 0; j < trainingWithForm.training.getPlanList().get(i).size(); j++)
            {
                sum += trainingWithForm.training.getPlanList().get(i).get(j).getExerciseId();
            }
            if (sum == controlSum) break;
        }

        return i;
    }

    private void generateMainView(int index){
        getVideo(index);
        binding.titleExerciseChange.setText(String.format("%s", model.getListExercises().get(index).getName()));
        binding.descriptionStartPosition.setText(model.getListExercises().get(index).getDescriptionOfStartPosition());
        binding.descriptionOfMoveText.setText(model.getListExercises().get(index).getDescriptionOfCorrectExecution());
    }

    private void getVideo(int index) {
        if(model.getExercise().getVideoUrl() != null)
        {
            Uri uri = Uri.parse(ConnectionToServer.PREFIX_VIDEO_URL + model.getListExercises().get(index).getVideoUrl());
            binding.videoView.setVideoURI(uri);
            binding.videoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
            binding.videoView.setOnPreparedListener(mp ->
            {
                binding.readVideo.setVisibility(View.INVISIBLE);
                binding.videoView.start();
                mp.setLooping(true);
            });
        }else
        {
            binding.readVideo.setVisibility(View.INVISIBLE);
            binding.videoView.setVisibility(View.INVISIBLE);
            binding.imagePlaceholder.setVisibility(View.VISIBLE);
            Glide.with(this).load(ConnectionToServer.PREFIX_PHOTO_URL + model.getExercise().getPhotoUrl()).into(binding.imagePlaceholder);
        }
    }
}