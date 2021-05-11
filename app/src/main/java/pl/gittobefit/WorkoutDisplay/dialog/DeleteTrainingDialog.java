package pl.gittobefit.WorkoutDisplay.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;

public class DeleteTrainingDialog extends AppCompatDialogFragment
{
    private View myView;
    private static final int  indexReset = -1;

    public DeleteTrainingDialog(View view) {
        this.myView = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_delete_training, null);

        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);

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
                    long idFromServer = AppDataBase.getInstance(getContext()).trainingDao().getIdFromServerById(tokens[1]);
                    model.getTrainingWithForms().remove(Integer.parseInt(tokens[0]));
                    AppDataBase.getInstance(getContext()).trainingDao().deleteTrainingInDataBase(Integer.parseInt(tokens[1]));
                    AppDataBase.getInstance(getContext()).workoutFormDao().deleteFormInDataBase(Integer.parseInt(tokens[1]));
                    IShowSnackbar activity = (IShowSnackbar) getActivity();
                    if (!User.getInstance().getLoggedBy().equals(User.WayOfLogin.NO_LOGIN))
                    {
                        ConnectionToServer.getInstance().trainingServices.deleteTrainingFromServer(activity, idFromServer, getContext());
                    }
                    Navigation.findNavController(myView).navigate(R.id. training_to_delete_action);
                    model.setLastIndex(indexReset);
                    activity.showSnackbar(getActivity().getString(R.string.deleteComplete));
                });
        return builder.create();
    }
}
