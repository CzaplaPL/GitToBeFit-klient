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

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;


/**
 * @author Kuba
 */
public class ChangePasswordDialog extends AppCompatDialogFragment
{
    private EditText editTextUserOldPassword;
    private EditText editTextUserNewPassword;
    String passValidation = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!`'@#$%^&()\\\\{}\\[\\]:;<>,.?/~_+\\-=|])(?=\\S+$).{8,}";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_change_password_dialog, null);

        builder.setView(view)
                .setTitle("Zmień hasło")
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
                        String oldPassword = editTextUserOldPassword.getText().toString();
                        String newPassword = editTextUserNewPassword.getText().toString();
                        if (newPassword.matches(passValidation)) {
                            ConnectionToServer.getInstance().userServices.changePassword(oldPassword, newPassword, getContext());
                        }
                        else {
                            Toast.makeText(getContext(), "Hasło musi składać sie z:\n- 8 znaków\n- jednej wielkiej litery\n- jendej małej litery\n- znaku specjalnego", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        editTextUserOldPassword = view.findViewById(R.id.oldPassword);
        editTextUserNewPassword = view.findViewById(R.id.newPassword);
        return builder.create();
    }


}
