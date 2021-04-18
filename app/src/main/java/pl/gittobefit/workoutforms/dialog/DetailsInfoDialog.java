package pl.gittobefit.workoutforms.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import pl.gittobefit.R;

public class DetailsInfoDialog extends AppCompatDialogFragment {

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_details_info, null);
        builder.setView(view)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> { });
        return builder.create();
    }
}
