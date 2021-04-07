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

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class EditExerciseDialog extends AppCompatDialogFragment implements NumberPicker.OnValueChangeListener
{
    private View myView;
    private NumberPicker seriesNumberPicker;
    private NumberPicker countNumberPicker;
    private String scheduleType;
    private ExerciseExecutionPOJODB exerciseExecutionPOJODB;
    private int trainingID;
    private String exerciseName;

    public EditExerciseDialog(View view, String scheduleType, ExerciseExecutionPOJODB exerciseExecutionPOJODB, int trainingID, String exerciseName)
    {
        this.myView = view;
        this.scheduleType = scheduleType;
        this.exerciseExecutionPOJODB = exerciseExecutionPOJODB;
        this.trainingID = trainingID;
        this.exerciseName = exerciseName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_exercise_dialog, null);

        seriesNumberPicker = view.findViewById(R.id.seriesNumberPicker);
        seriesNumberPicker.setMaxValue(10);
        seriesNumberPicker.setValue(exerciseExecutionPOJODB.getSeries());
        seriesNumberPicker.setMinValue(1);
        seriesNumberPicker.setWrapSelectorWheel(false);
        seriesNumberPicker.setOnValueChangedListener(this);

        countNumberPicker = view.findViewById(R.id.countNumberPicker);
        countNumberPicker.setMaxValue(50);

        if (exerciseExecutionPOJODB.getTime() > 20) {
            countNumberPicker.setValue(exerciseExecutionPOJODB.getTime());
        } else {
            countNumberPicker.setValue(exerciseExecutionPOJODB.getCount());
        }

        countNumberPicker.setMinValue(1);
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
        builder.setView(view)
                .setTitle(exerciseName)
                .setNegativeButton(getString(R.string.admit_changes), (dialog, which) ->
                {
                    exerciseExecutionPOJODB.setSeries(seriesNumberPicker.getValue());

                    if (exerciseExecutionPOJODB.getTime() > 20)
                    {
                        exerciseExecutionPOJODB.setTime(countNumberPicker.getValue());
                    }
                    else {
                        exerciseExecutionPOJODB.setCount(countNumberPicker.getValue());
                    }

                    System.out.println(seriesNumberPicker.getValue() + " " + countNumberPicker.getValue());
                    Navigation.findNavController(myView).navigate(R.id. reload);
                })
                .setPositiveButton(getString(R.string.change_exercise), (dialog, which) ->
                {
                    //AppDataBase.getInstance(getContext()).trainingDao().deleteTrainingInDataBase(Integer.parseInt(tokens[1]));
                    Navigation.findNavController(myView).navigate(R.id. reload);
                });
        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
