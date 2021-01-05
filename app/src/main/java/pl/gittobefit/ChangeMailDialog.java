package pl.gittobefit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


/**
 * @author Kuba
 */

public class ChangeMailDialog extends AppCompatDialogFragment
{

    private EditText editTextUserNewEmail;
    private EditText editTextUserPassword;
    private DialogListener dialogListener;
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
                        dialogListener.applyTexts(email, password);
                    }
                });

        editTextUserNewEmail = view.findViewById(R.id.edit_new_email);
        editTextUserPassword = view.findViewById(R.id.passwordToChangeEmail);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "musi implementowac dialog listner");
        }
    }

    public interface DialogListener
    {
        void applyTexts(String newEmail, String password);
    }
}
