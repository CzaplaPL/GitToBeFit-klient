package pl.gittobefit.user.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.Validation;


/**
 * dialog z formularzem zmiany hasła
 * @author Kuba
 */
public class ChangePasswordDialog extends AppCompatDialogFragment
{

    private EditText editTextUserOldPassword;
    private EditText editTextUserNewPassword;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_change_password_dialog, null);
        builder.setView(view)
                .setTitle(getString(R.string.change_pass_title))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) ->
                {
                })
                .setPositiveButton(getString(R.string.change), (dialog, which) ->
                {
                    String oldPassword = editTextUserOldPassword.getText().toString();
                    String newPassword = editTextUserNewPassword.getText().toString();
                    IShowSnackbar activity = (IShowSnackbar) getActivity();
                    if (newPassword.matches(Validation.PASSWORD_REGEX)) {
                        ConnectionToServer.getInstance().userServices.changePassword(oldPassword, newPassword, getContext(),activity);
                    }
                    else {
                        activity.showSnackbar(getString(R.string.wrongPassword));
                    }
                });
        editTextUserOldPassword = view.findViewById(R.id.oldPassword);
        editTextUserNewPassword = view.findViewById(R.id.newPassword);
        return builder.create();
    }
}
