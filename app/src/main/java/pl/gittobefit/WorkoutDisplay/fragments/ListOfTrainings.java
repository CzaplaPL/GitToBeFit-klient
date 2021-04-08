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

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.adapters.TrainingListAdapter;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.repository.TrainingRepository;

public class ListOfTrainings extends Fragment
{
    public ListOfTrainings() {
        System.out.println(5);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_trainings, container, false);
        System.out.println(2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println(3);
        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);

        model.setTrainingWithForms(TrainingRepository.getInstance(getContext()).getAllTrainingsForUser(""));

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
        System.out.println(4);
    }
}
