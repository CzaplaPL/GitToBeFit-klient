package pl.gittobefit.user.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.Validation;


/**
 * @author Kuba
 */

public class ChangeMailDialog extends AppCompatDialogFragment
{
    public interface ChangeMailDialogInterface {
        void onChangeMail(Boolean sukces,String message );
    }
    private EditText editTextUserNewEmail;
    private EditText editTextUserPassword;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_email, null);
        editTextUserNewEmail = view.findViewById(R.id.edit_new_email);
        editTextUserPassword = view.findViewById(R.id.passwordToChangeEmail);
        builder.setView(view)
                .setTitle("Zmień email")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Zmień", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = editTextUserNewEmail.getText().toString();
                        String password = editTextUserPassword.getText().toString();
                        ChangeMailDialogInterface activity = (ChangeMailDialogInterface) getActivity();
                        if (email.matches(Validation.EMAIL_REGEX)) {
                            activity.onChangeMail(false ,getString(R.string.changingMail));
                            ConnectionToServer.getInstance().userServices.changeEmail(email, password, getContext(),activity);
                        } else {

                           activity.onChangeMail(false ,getString(R.string.wrongEmail));
                        }
                    }
                });
        return builder.create();
    }




}
