package pl.gittobefit.WorkoutDisplay.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.adapters.TrainingListAdapter;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.user.User;

public class ListOfTrainings extends Fragment
{
    private RecyclerView trainingList;
    private LinearLayout loading;
    TrainingListAdapter trainingListAdapter;
    InitiationTrainingDisplayLayoutViewModel model;
    public ListOfTrainings() {
    }


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
        trainingList = getView().findViewById(R.id.list_of_trainings);
        loading = getView().findViewById(R.id.list_traing_loading);

        model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class); trainingList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        trainingListAdapter = new TrainingListAdapter(model.getTrainingWithForms(), this);
        trainingList.setAdapter(trainingListAdapter);
        trainingList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
       User.getInstance().getSynchroniseTraining$().observe(getViewLifecycleOwner(), new Observer<User.SynchroniseTraining>()
        {
            @Override
            public void onChanged(User.SynchroniseTraining synchroniseTraining)
            {
                if(User.SynchroniseTraining.No_Synchronise == synchroniseTraining ||  synchroniseTraining == User.SynchroniseTraining.Synchronise_Success )
                {
                    model.setTrainingWithForms(TrainingRepository.getInstance(getContext()).loadTrainings());
                    trainingListAdapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    trainingList.setVisibility(View.VISIBLE);
                }else if(synchroniseTraining == User.SynchroniseTraining.Synchronise_error)
                {
                    IShowSnackbar activity = (IShowSnackbar)getActivity();
                    activity.showSnackbar(getString(R.string.noSynchronize));
                    model.setTrainingWithForms(TrainingRepository.getInstance(getContext()).loadTrainings());
                    trainingListAdapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    trainingList.setVisibility(View.VISIBLE);
                }else
                {
                    loading.setVisibility(View.VISIBLE);
                    trainingList.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onResume()
    {
        super.onResume();

    }
}
