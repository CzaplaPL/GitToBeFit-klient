package pl.gittobefit.WorkoutDisplay.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.repository.TrainingRepository;

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


        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);


        if (model.getTrainingWithForms().size() == 0)
        {
            model.setTrainingWithForms(TrainingRepository.getInstance(getContext()).getAllTrainingsForUser(""));
        }

        RecyclerView recyclerView = getView().findViewById(R.id.list_of_trainings);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(model.getTrainingWithForms(), this);
        recyclerView.setAdapter(trainingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
