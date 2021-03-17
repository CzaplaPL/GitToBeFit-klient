package pl.gittobefit.workoutforms.fragments;

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

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.adapters.TrainingListAdapter;
import pl.gittobefit.workoutforms.object.UserTrainings;

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
    public void onResume()
    {
        super.onResume();
        RecyclerView recyclerView = getView().findViewById(R.id.list_of_trainings);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(UserTrainings.getInstance().getTrainingArrayList(), this);
        recyclerView.setAdapter(trainingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}
