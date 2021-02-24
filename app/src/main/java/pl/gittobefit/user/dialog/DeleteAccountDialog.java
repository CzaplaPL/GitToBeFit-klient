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


public class DeleteAccountDialog extends AppCompatDialogFragment
{
    public interface DeleteAccountDialogInterface {
        void onAccountDelete(Boolean success,String message );
    }
    private EditText userPassword;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_delete_account_dialog, null);

        builder.setView(view)
                .setTitle("Usuń konto")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Usuń", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String newPassword = userPassword.getText().toString();
                        DeleteAccountDialogInterface activity = (DeleteAccountDialogInterface) getActivity();
                        ConnectionToServer.getInstance().userServices.deleteAccount(newPassword, getParentFragment(), activity);
                    }
                });

        userPassword = view.findViewById(R.id.userPassword);

        return builder.create();
    }
}
