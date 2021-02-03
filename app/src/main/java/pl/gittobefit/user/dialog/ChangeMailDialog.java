package pl.gittobefit.user.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;


/**
 * @author Kuba
 */

public class ChangeMailDialog extends AppCompatDialogFragment
{
    String emailValidation = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!`'@#$%^&()\\\\{}\\[\\]:;<>,.?/~_+\\-=|])(?=\\S+$).{8,}";
    private EditText editTextUserNewEmail;
    private EditText editTextUserPassword;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_email, null);

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
                        if (email.matches(emailValidation)) {
                            ConnectionToServer.getInstance().userServices.changeEmail(email, password, getContext());
                        } else {
                            Toast.makeText(getContext(), "Zły format emaila :/", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        editTextUserNewEmail = view.findViewById(R.id.edit_new_email);
        editTextUserPassword = view.findViewById(R.id.passwordToChangeEmail);
        return builder.create();
    }




}
