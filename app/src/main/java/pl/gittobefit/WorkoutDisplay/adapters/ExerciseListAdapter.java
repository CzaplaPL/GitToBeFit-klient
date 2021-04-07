package pl.gittobefit.WorkoutDisplay.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.dialog.DeleteTrainingDialog;
import pl.gittobefit.WorkoutDisplay.dialog.EditExerciseDialog;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>
{

    private ArrayList<Exercise> exerciseArrayList;
    private ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList;
    private Fragment fragment;
    private String scheduleType;
    private int trainingID;

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

    public ExerciseListAdapter(ArrayList<Exercise> exerciseArrayList, ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList, String scheduleType, int trainingID, Fragment fragment) {
        this.exerciseArrayList = exerciseArrayList;
        this.fragment = fragment;
        this.exercisesExecutionArrayList = exercisesExecutionArrayList;
        this.scheduleType = scheduleType;
        this.trainingID = trainingID;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_exercise_listview_item, parent, false);

        return new ExerciseListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.exerciseName.setText(exerciseArrayList.get(position).getName());
        String text = "";

        if (exercisesExecutionArrayList.get(position).getTime() > 20)
        {
            text = exercisesExecutionArrayList.get(position).getSeries() + " serie, " + exercisesExecutionArrayList.get(position).getTime() + " sekund";
        }
        else
        {
            text = exercisesExecutionArrayList.get(position).getSeries() + " serie, " + exercisesExecutionArrayList.get(position).getCount() + " powtórzeń";
        }

        holder.exerciseInfo.setText(text);


        holder.itemView.setOnClickListener(v -> {
            EditExerciseDialog editExerciseDialog = new EditExerciseDialog(fragment.getView(), scheduleType, exercisesExecutionArrayList.get(position), trainingID, exerciseArrayList.get(position).getName());
            editExerciseDialog.show(fragment.getFragmentManager(), "dialog");
        });
    }


    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

}
