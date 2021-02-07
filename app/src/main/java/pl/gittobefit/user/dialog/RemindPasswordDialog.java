package pl.gittobefit.user.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.Validation;

/**
 *  formularz przypominania hasła
 *  * @author Czapla
 */
public class RemindPasswordDialog extends AppCompatDialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Context context = this.getContext();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_remind_password_dialog, null);
        TextInputLayout email = view.findViewById(R.id.remindPassEmail);
        builder.setView(view)
                .setTitle(getResources().getString(R.string.remindPasswordTitle))
                .setPositiveButton(getResources().getString(R.string.remindPassword), (dialog, which) ->
                {
                    String mail=email.getEditText().getText().toString();
                    if(!mail.matches(Validation.emailValidation ))
                    {
                        Toast.makeText(context,context.getResources().getString(R.string.incorrectData),Toast.LENGTH_SHORT).show();
                        email.setErrorEnabled(true);
                        email.setError(getResources().getString(R.string.incorrectData));

                    }else
                    {
                       ConnectionToServer.getInstance().userServices.remindPassword(mail,context);
                    }
                });
        return builder.create();
    }


}
