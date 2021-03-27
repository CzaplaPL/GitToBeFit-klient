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
                    int sc1 = args.getInt("sc1");
                    if (sc1 == -999)
                    {
                        sc1 = UserTrainings.getInstance().getTrainingArrayList().size()-1;
                    }

                    UserTrainings.getInstance().getTrainingArrayList().remove(sc1);

                    List<SavedTraining> result2 = AppDataBase.getInstance(getContext()).training().getInfoForTrainingList();

                    AppDataBase.getInstance(getContext()).training().deleteTrainingInDataBase(result2.get(sc1).getId());
                    AppDataBase.getInstance(getContext()).workoutForm().deleteFormInDataBase(result2.get(sc1).getId());
                    Navigation.findNavController(myView).navigate(R.id. training_to_delete_action);
                });
        return builder.create();
    }
}
