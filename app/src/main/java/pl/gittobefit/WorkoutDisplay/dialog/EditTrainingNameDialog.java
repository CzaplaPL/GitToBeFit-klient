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

import java.util.List;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.UserTrainings;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.SavedTraining;

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

        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);

        builder.setView(view)
                .setTitle(getString(R.string.edit_training_name))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) ->
                {
                })
                .setPositiveButton(getString(R.string.change), (dialog, which) ->
                {
                    String newTrainingName = newName.getText().toString();
                    Bundle args = getArguments();
                    String trainingID = args.getString("trainingID");
                    String[] tokens = trainingID.split("/");

                    model.getTrainingWithForms().get(Integer.parseInt(tokens[0])).training.setTrainingName(newTrainingName);
                    AppDataBase.getInstance(getContext()).trainingDao().updateTrainingNameInDataBase(newTrainingName, Integer.parseInt(tokens[1]));
                    Navigation.findNavController(myView).navigate(R.id. reload);

                });
        newName = view.findViewById(R.id.newTrainingName);
        return builder.create();
    }
}
