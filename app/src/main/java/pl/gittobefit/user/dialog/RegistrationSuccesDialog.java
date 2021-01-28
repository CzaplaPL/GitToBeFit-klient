package pl.gittobefit.user.dialog;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import pl.gittobefit.R;

/**
 * dialog informujący o sukcesie rejestracji
 * @author Czapla
 */
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
