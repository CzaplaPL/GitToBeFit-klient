package pl.gittobefit.WorkoutDisplay.adapters;

import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Locale;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.dialog.BottomMenuDialog;
import pl.gittobefit.WorkoutDisplay.dialog.DeleteTrainingDialog;
import pl.gittobefit.WorkoutDisplay.dialog.EditExerciseDialog;
import pl.gittobefit.WorkoutDisplay.exceptions.TrainingNotFoundException;
import pl.gittobefit.WorkoutDisplay.fragments.DisplayReceivedTraining;
import pl.gittobefit.WorkoutDisplay.fragments.DisplayReceivedTrainingDirections;
import pl.gittobefit.WorkoutDisplay.fragments.ExerciseDetails;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.network.ConnectionToServer;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>
{

    private ArrayList<Exercise> exerciseArrayList;
    private ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList;
    private Fragment fragment;
    private String scheduleType;
    private int trainingID;
    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS;
    private int circuitsCount;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView exerciseName;
        private TextView exerciseInfo;

        public ViewHolder (View view)
        {
            super(view);

            exerciseName = (TextView) view.findViewById(R.id.exercise_name);
            exerciseInfo = (TextView) view.findViewById(R.id.exercise_additional_info);
        }
    }

    public ExerciseListAdapter(
            ArrayList<Exercise> exerciseArrayList,
            ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList,
            String scheduleType,
            int trainingID,
            Fragment fragment,
            ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS
    )
    {
        this.exerciseArrayList = exerciseArrayList;
        this.fragment = fragment;
        this.exercisesExecutionArrayList = exercisesExecutionArrayList;
        this.scheduleType = scheduleType;
        this.trainingID = trainingID;
        this.exerciseExecutionPOJODBS = exerciseExecutionPOJODBS;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_exercise_listview_item, parent, false);

        return new ExerciseListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.exerciseName.setText(exerciseArrayList.get(position).getName());
        String text = "";
        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(fragment.requireActivity())
                .get(InitiationTrainingDisplayLayoutViewModel.class);
        try {
            circuitsCount = model.getTrainingByID(trainingID).training.getCircuitsCount();
        } catch (TrainingNotFoundException e) {
            e.printStackTrace();
        }
        String properFormSeries = "";
        switch (exercisesExecutionArrayList.get(position).getSeries())
        {
            case 1: properFormSeries = "seria"; break;
            case 2:
            case 4:
            case 3:
                properFormSeries = "serie"; break;
            default:  properFormSeries = "serii"; break;
        }

        String properFormCircuit = "";
        switch (exercisesExecutionArrayList.get(position).getSeries())
        {
            case 1: properFormCircuit = "obwód"; break;
            case 2:
            case 4:
            case 3:
                properFormCircuit = "obwody"; break;
            default:  properFormCircuit = "obwodów"; break;
        }
        String properFormRep = "";
        switch (exercisesExecutionArrayList.get(position).getCount())
        {
            case 1: properFormRep = "powtórzenie"; break;
            case 2:
            case 4:
            case 3:
                properFormRep = "powtórzenia"; break;
            default:  properFormRep = "powtórzeń"; break;
        }

        if (exercisesExecutionArrayList.get(position).getTime() != 0)
        {
            if (scheduleType.equals("CIRCUIT"))
            {
                text = String.format(Locale.getDefault(),"%d %s, %d sekund",
                        circuitsCount,
                        properFormCircuit,
                        exercisesExecutionArrayList.get(position).getTime());
            }
            else
            {
                text = String.format(Locale.getDefault(),"%d %s, %d sekund",
                        exercisesExecutionArrayList.get(position).getSeries(),
                        properFormSeries,
                        exercisesExecutionArrayList.get(position).getTime());
            }
        }
        else
        {
            if (scheduleType.equals("CIRCUIT"))
            {
                text = String.format(Locale.getDefault(),
                        "%d %s, %d %s",
                        circuitsCount,
                        properFormCircuit,
                        exercisesExecutionArrayList.get(position).getCount(), properFormRep);
            }
            else
            {
                text = String.format(Locale.getDefault(),
                        "%d %s, %d %s",
                        exercisesExecutionArrayList.get(position).getSeries(),
                        properFormSeries,
                        exercisesExecutionArrayList.get(position).getCount(), properFormRep);
            }
        }

        holder.exerciseInfo.setText(text);

            holder.itemView.setOnClickListener(v -> {
                if(ConnectionToServer.isNetwork(fragment.getContext()))
                {
                BottomMenuDialog bottomSheetDialog = new BottomMenuDialog(
                        circuitsCount,
                        exerciseArrayList,
                        fragment,
                        scheduleType,
                        position ,
                        exercisesExecutionArrayList,
                        trainingID,
                        exerciseExecutionPOJODBS
                );
                bottomSheetDialog.show(fragment.getParentFragmentManager(), "bottomMenu");
                }else
                {
                    IShowSnackbar activity = (IShowSnackbar) fragment.getActivity();
                    activity.showSnackbar("Zawartość dostępna w trybie online");
                }
            });


    }


    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

}
