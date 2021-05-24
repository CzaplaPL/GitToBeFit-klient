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
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;


public class EditBreakDialog extends AppCompatDialogFragment implements NumberPicker.OnValueChangeListener
{
    private NumberPicker seriesNumberPicker;
    private int breakTime;

    public EditBreakDialog(int breakTime)
    {
        this.breakTime = breakTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_break_time_dialog, null);

        seriesNumberPicker = view.findViewById(R.id.trainingBreakNumberPicker);
        seriesNumberPicker.setMinValue(1);
        seriesNumberPicker.setMaxValue(90);
        seriesNumberPicker.setValue(breakTime);
        seriesNumberPicker.setWrapSelectorWheel(false);
        seriesNumberPicker.setOnValueChangedListener(this);

        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(requireActivity())
                .get(InitiationTrainingDisplayLayoutViewModel.class);

        builder.setView(view)
                .setTitle(getString(R.string.edit_break_time))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) ->
                {
                })
                .setPositiveButton(getString(R.string.change), (dialog, which) ->
                {
                    Bundle args = getArguments();
                    String trainingID = args.getString("trainingID");
                    String[] tokens = trainingID.split("/");

                    model.getTrainingWithForms()
                            .get(Integer.parseInt(tokens[0]))
                            .training.setBreakTime(seriesNumberPicker.getValue());

                    model.getCurrentBreakTime().setValue(seriesNumberPicker.getValue());

                    IShowSnackbar activity = (IShowSnackbar) getActivity();
                    long idFromServer = AppDataBase.getInstance(getContext()).trainingDao().getIdFromServerById(tokens[1]);

                    AppDataBase.getInstance(getContext())
                            .trainingDao()
                            .updateBreakTime(seriesNumberPicker.getValue(), Integer.parseInt(tokens[1]));

                    if (!User.getInstance().getLoggedBy().equals(User.WayOfLogin.NO_LOGIN))
                    {
                        ConnectionToServer.getInstance()
                                .trainingServices
                                .saveTrainingAfterChanges(activity, getContext(), Integer.parseInt(tokens[1]));
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
