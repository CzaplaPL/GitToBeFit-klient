package pl.gittobefit.WorkoutDisplay.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.adapters.TrainingListAdapter;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.WorkoutDisplay.objects.UserTrainings;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;

public class ListOfTrainings extends Fragment
{
    public ListOfTrainings() {}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_trainings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (UserTrainings.getInstance().getTrainingArrayList().size() == 0)
        {
            List<WorkoutForm> result = AppDataBase.getInstance(getContext()).workoutForm().getTrainingsFrom();
            List<SavedTraining> result2 = AppDataBase.getInstance(getContext()).training().getInfoForTrainingList();
            if (result2.size() != 0)
            {
                Training training;

                for (int i = 0; i < result.size(); i++) {
                    training = new Training();
                    training.setTrainingForm(new WorkoutForm(null, result.get(i).getTrainingType(), null, result.get(i).getDaysCount(), result.get(i).getScheduleType(), result.get(i).getDuration()));
                    training.setTrainingName(result2.get(i).getTrainingName());
                    training.setGenerationDate(result2.get(i).getGenerationDate());
                    training.setId(result2.get(i).getId());

                    UserTrainings.getInstance().add(training);
                }
            }

        }

        RecyclerView recyclerView = getView().findViewById(R.id.list_of_trainings);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(UserTrainings.getInstance().getTrainingArrayList(), this);
        recyclerView.setAdapter(trainingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
