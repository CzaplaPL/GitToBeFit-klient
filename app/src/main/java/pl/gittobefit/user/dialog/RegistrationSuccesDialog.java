package pl.gittobefit.user.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;

public class RegistrationSuccesDialog extends AppCompatDialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        MaterialAlertDialogBuilder builder = new   MaterialAlertDialogBuilder(getContext())
                .setTitle(getResources().getString(R.string.registrsucces))
                .setMessage(getResources().getString(R.string.registrsuccesinfo))
                .setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {  });
        return builder.create();
    }


}
