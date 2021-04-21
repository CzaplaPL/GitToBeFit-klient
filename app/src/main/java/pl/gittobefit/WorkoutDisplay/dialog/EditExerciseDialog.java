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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;

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
    private int circuitsCount;

    public EditExerciseDialog(
            int circuitsCount,
            View view,
            String scheduleType,
            int position,
            ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList,
            int trainingID,
            String exerciseName,
            ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS
    )
    {
        this.circuitsCount = circuitsCount;
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
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_exercise_dialog, null);

        seriesNumberPicker = view.findViewById(R.id.seriesNumberPicker);
        seriesNumberPicker.setMaxValue(10);
        TextView seriesCount = view.findViewById(R.id.seriesCount);

        if (scheduleType.equals("CIRCUIT"))
        {
            seriesNumberPicker.setValue(circuitsCount);
            seriesCount.setText("Ilość obwodów");
        }
        else
        {
            seriesNumberPicker.setValue(exercisesExecutionArrayList.get(position).getSeries());
            seriesCount.setText("Ilość serii");
        }
        seriesNumberPicker.setMinValue(1);
        seriesNumberPicker.setWrapSelectorWheel(false);
        seriesNumberPicker.setOnValueChangedListener(this);

        countNumberPicker = view.findViewById(R.id.countNumberPicker);

        TextView count_time = view.findViewById(R.id.count_time);
        if (exercisesExecutionArrayList.get(position).getTime() != 0) {
            countNumberPicker.setMaxValue(90);
            countNumberPicker.setMinValue(10);
            countNumberPicker.setValue(exercisesExecutionArrayList.get(position).getTime());
            count_time.setText("Czas trwania");
        } else {
            countNumberPicker.setMaxValue(50);
            countNumberPicker.setMinValue(1);
            countNumberPicker.setValue(exercisesExecutionArrayList.get(position).getCount());
            count_time.setText("Ilość powtórzeń");
        }

        countNumberPicker.setWrapSelectorWheel(false);
        countNumberPicker.setOnValueChangedListener(this);



        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);

        builder.setView(view)
                .setTitle(exerciseName)
                .setNegativeButton(getString(R.string.cancel), (dialog, which) ->
                {
                })
                .setPositiveButton(getString(R.string.admit_changes), (dialog, which) ->
                {
                    if (scheduleType.equals("CIRCUIT"))
                    {
                        if (seriesNumberPicker.getValue() != model.getTrainingWithForms().get(position).training.getCircuitsCount())
                        {
                            model.getTrainingByID(trainingID).training.setCircuitsCount(seriesNumberPicker.getValue());
                            model.getCurrentSeries().setValue(seriesNumberPicker.getValue());
                            circuitsCount = seriesNumberPicker.getValue();
                        }
                    }
                    else
                    {
                        if (seriesNumberPicker.getValue() !=  exercisesExecutionArrayList.get(position).getSeries())
                        {
                            exercisesExecutionArrayList.get(position).setSeries(seriesNumberPicker.getValue());
                            model.getCurrentSeries().setValue(seriesNumberPicker.getValue());
                        }

                    }

                    if (exercisesExecutionArrayList.get(position).getTime() != 0)
                    {
                        if (countNumberPicker.getValue() !=  exercisesExecutionArrayList.get(position).getTime())
                        {
                            exercisesExecutionArrayList.get(position).setTime(countNumberPicker.getValue());
                            model.getCurrentTime().setValue(countNumberPicker.getValue());
                        }
                    }
                    else {
                        if (countNumberPicker.getValue() !=  exercisesExecutionArrayList.get(position).getCount())
                        {
                            exercisesExecutionArrayList.get(position).setCount(countNumberPicker.getValue());
                            model.getCurrentCount().setValue(countNumberPicker.getValue());
                        }

                    }

                    AppDataBase.getInstance(getContext()).trainingDao().updateTrainingPlan(exerciseExecutionPOJODBS, circuitsCount, trainingID);
                    IShowSnackbar activity = (IShowSnackbar) getActivity();
                    if (!User.getInstance().getLoggedBy().equals(User.WayOfLogin.NO_LOGIN))
                    {
                        ConnectionToServer.getInstance().trainingServices.saveTrainingAfterChanges(activity, getContext());
                    }
                    else
                    {
                        activity.showSnackbar(getContext().getResources().getString(R.string.editionComplete));
                    }
                });
        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
