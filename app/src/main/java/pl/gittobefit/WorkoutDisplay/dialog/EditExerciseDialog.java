package pl.gittobefit.WorkoutDisplay.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.UserTrainings;
import pl.gittobefit.database.AppDataBase;

public class EditExerciseDialog extends AppCompatDialogFragment implements NumberPicker.OnValueChangeListener
{
    private View myView;
    private NumberPicker seriesNumberPicker;
    private NumberPicker countNumberPicker;
    public EditExerciseDialog(View view)
    {
        this.myView = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_exercise_dialog, null);

        seriesNumberPicker = view.findViewById(R.id.seriesNumberPicker);
        seriesNumberPicker.setMaxValue(10);
        seriesNumberPicker.setValue(3);
        seriesNumberPicker.setMinValue(1);
        seriesNumberPicker.setWrapSelectorWheel(false);
        seriesNumberPicker.setOnValueChangedListener(this);

        countNumberPicker = view.findViewById(R.id.countNumberPicker);
        countNumberPicker.setMaxValue(50);
        countNumberPicker.setValue(8);
        countNumberPicker.setMinValue(1);
        countNumberPicker.setWrapSelectorWheel(false);
        countNumberPicker.setOnValueChangedListener(this);
        builder.setView(view)
                .setNegativeButton(getString(R.string.admit_changes), (dialog, which) ->
                {
                    System.out.println(seriesNumberPicker.getValue() + " " + countNumberPicker.getValue());
                })
                .setPositiveButton(getString(R.string.change_exercise), (dialog, which) ->
                {
                    Bundle args = getArguments();
                    String trainingID = args.getString("trainingID");
                    String[] tokens = trainingID.split("/");

                    UserTrainings.getInstance().getTrainingArrayList().remove(Integer.parseInt(tokens[0]));

                    AppDataBase.getInstance(getContext()).training().deleteTrainingInDataBase(Integer.parseInt(tokens[1]));
                    AppDataBase.getInstance(getContext()).workoutForm().deleteFormInDataBase(Integer.parseInt(tokens[1]));
                    Navigation.findNavController(myView).navigate(R.id. reload);
                });
        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
