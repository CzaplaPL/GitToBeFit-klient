package pl.gittobefit.WorkoutDisplay.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import java.util.List;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.UserTrainings;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.SavedTraining;

public class DeleteTrainingDialog extends AppCompatDialogFragment
{
    private View myView;

    public DeleteTrainingDialog(View view) {
        this.myView = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_delete_training, null);
        builder.setView(view)
                .setTitle(getString(R.string.delete_training))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) ->
                {
                })
                .setPositiveButton(getString(R.string.delete), (dialog, which) ->
                {
                    Bundle args = getArguments();
                    String trainingID = args.getString("trainingID");
                    String[] tokens = trainingID.split("/");

                    UserTrainings.getInstance().getTrainingArrayList().remove(Integer.parseInt(tokens[0]));

                    AppDataBase.getInstance(getContext()).training().deleteTrainingInDataBase(Integer.parseInt(tokens[1]));
                    AppDataBase.getInstance(getContext()).workoutForm().deleteFormInDataBase(Integer.parseInt(tokens[1]));
                    Navigation.findNavController(myView).navigate(R.id. training_to_delete_action);
                });
        return builder.create();
    }
}
