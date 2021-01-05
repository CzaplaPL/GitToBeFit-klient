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
public class ChangePasswordDialog extends AppCompatDialogFragment
{
    private EditText editTextUserOldPassword;
    private EditText editTextUserNewPassword;
    private ChangePasswordDialog.DialogListener dialogListener;
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
                        String oldP = editTextUserOldPassword.getText().toString();
                        String newP = editTextUserNewPassword.getText().toString();
                        dialogListener.applyTexts2(oldP, newP);
                    }
                });

        editTextUserOldPassword = view.findViewById(R.id.oldPassword);
        editTextUserNewPassword = view.findViewById(R.id.newPassword);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (ChangePasswordDialog.DialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "musi implementowac dialog listner");
        }
    }

    public interface DialogListener
    {
        void applyTexts2(String oldPassword, String newPassword);
    }
}
