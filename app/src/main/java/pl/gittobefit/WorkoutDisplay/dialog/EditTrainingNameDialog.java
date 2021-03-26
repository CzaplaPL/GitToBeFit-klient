package pl.gittobefit.WorkoutDisplay.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.UserTrainings;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;

public class EditTrainingNameDialog extends AppCompatDialogFragment
{
    private TextView newName;
    private int index;
    private View myView;

    public EditTrainingNameDialog(View view) {
        this.myView = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_training_name_dialog, null);
        builder.setView(view)
                .setTitle(getString(R.string.edit_training_name))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) ->
                {
                })
                .setPositiveButton(getString(R.string.change), (dialog, which) ->
                {
                    String newTrainingName = newName.getText().toString();
                    Bundle args = getArguments();
                    int sc1 = args.getInt("sc1");

                    UserTrainings.getInstance().getTraining(sc1).setTrainingName(newTrainingName);
                    AppDataBase.getInstance(getContext()).training().updateTrainingNameInDataBase(newTrainingName,sc1+1);
                    Navigation.findNavController(myView).navigate(R.id. reload);

                });
        newName = view.findViewById(R.id.newTrainingName);
        return builder.create();
    }
}
