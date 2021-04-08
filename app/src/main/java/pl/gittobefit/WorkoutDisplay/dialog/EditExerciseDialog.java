package pl.gittobefit.WorkoutDisplay.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class EditExerciseDialog extends AppCompatDialogFragment implements NumberPicker.OnValueChangeListener
{
    private View myView;
    private NumberPicker seriesNumberPicker;
    private NumberPicker countNumberPicker;
    private int position;
    private String scheduleType;
    private ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList;
    private int trainingID;
    private String exerciseName;
    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS;

    public EditExerciseDialog(View view, String scheduleType, int position, ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList,
                              int trainingID, String exerciseName, ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS)
    {
        this.myView = view;
        this.scheduleType = scheduleType;
        this.position = position;
        this.exercisesExecutionArrayList = exercisesExecutionArrayList;
        this.trainingID = trainingID;
        this.exerciseName = exerciseName;
        this.exerciseExecutionPOJODBS = exerciseExecutionPOJODBS;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_exercise_dialog, null);

        seriesNumberPicker = view.findViewById(R.id.seriesNumberPicker);
        seriesNumberPicker.setMaxValue(10);
        seriesNumberPicker.setValue(exercisesExecutionArrayList.get(position).getSeries());
        seriesNumberPicker.setMinValue(1);
        seriesNumberPicker.setWrapSelectorWheel(false);
        seriesNumberPicker.setOnValueChangedListener(this);

        countNumberPicker = view.findViewById(R.id.countNumberPicker);

        if (exercisesExecutionArrayList.get(position).getTime() > 20) {
            countNumberPicker.setMaxValue(90);
            countNumberPicker.setMinValue(10);
            countNumberPicker.setValue(exercisesExecutionArrayList.get(position).getTime());
        } else {
            countNumberPicker.setMaxValue(50);
            countNumberPicker.setMinValue(1);
            countNumberPicker.setValue(exercisesExecutionArrayList.get(position).getCount());
        }


        countNumberPicker.setWrapSelectorWheel(false);
        countNumberPicker.setOnValueChangedListener(this);

        TextView seriesCount = view.findViewById(R.id.seriesCount);
        if (scheduleType.equals("SERIES"))
        {
            seriesCount.setText("Ilość serii");
        }
        else if (scheduleType.equals("CIRCUIT"))
        {
            seriesCount.setText("Ilość obwodów");
        }

        TextView count_time = view.findViewById(R.id.count_time);
        if (exercisesExecutionArrayList.get(position).getTime() > 20)
        {
            count_time.setText("Czas trwania");
        }
        else {
            count_time.setText("Ilość powtórzeń");
        }

        builder.setView(view)
                .setTitle(exerciseName)
                .setNegativeButton(getString(R.string.admit_changes), (dialog, which) ->
                {
                    if (scheduleType.equals("CIRCUIT"))
                    {
                        for (ExerciseExecutionPOJODB item: exercisesExecutionArrayList
                             ) {
                            item.setSeries(seriesNumberPicker.getValue());
                        }
                    }
                    else
                    {
                        exercisesExecutionArrayList.get(position).setSeries(seriesNumberPicker.getValue());
                    }

                    if (exercisesExecutionArrayList.get(position).getTime() > 20)
                    {
                        exercisesExecutionArrayList.get(position).setTime(countNumberPicker.getValue());
                    }
                    else {
                        exercisesExecutionArrayList.get(position).setCount(countNumberPicker.getValue());
                    }

                    AppDataBase.getInstance(getContext()).trainingDao().updateTrainingPlan(exerciseExecutionPOJODBS, trainingID);
                    Navigation.findNavController(myView).navigate(R.id. reload);
                })
                .setPositiveButton(getString(R.string.change_exercise), (dialog, which) ->
                {
                    Navigation.findNavController(myView).navigate(R.id. reload);
                });
        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}