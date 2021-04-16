package pl.gittobefit.WorkoutDisplay.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class BottomMenuDialog extends BottomSheetDialogFragment
{
    private Fragment fragment;
    private ArrayList<Exercise> exerciseArrayList;
    private int position;
    private String scheduleType;
    private ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList;
    private int trainingID;
    private String exerciseName;
    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS;

    public BottomMenuDialog(ArrayList<Exercise> exerciseArrayList,
                            Fragment fragment,
                            String scheduleType,
                            int position,
                            ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList,
                            int trainingID,
                            String exerciseName,
                            ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS)
    {
        this.exerciseArrayList = exerciseArrayList;
        this.fragment = fragment;
        this.scheduleType = scheduleType;
        this.position = position;
        this.exercisesExecutionArrayList = exercisesExecutionArrayList;
        this.trainingID = trainingID;
        this.exerciseName = exerciseName;
        this.exerciseExecutionPOJODBS = exerciseExecutionPOJODBS;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_bottom_menu_dialog, container, false);
        Button goToEditButton = v.findViewById(R.id.goToEditButton);
        Button goToChangeButton = v.findViewById(R.id.goToChangeButton);
        Button goToInfoButton = v.findViewById(R.id.goToInfoButton);

        goToChangeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            dismiss();
            }
        });

        goToEditButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                EditExerciseDialog editExerciseDialog = new EditExerciseDialog(fragment.getView(),
                    scheduleType,
                    position ,
                    exercisesExecutionArrayList,
                    trainingID,
                    exerciseArrayList.get(position).getName(),
                    exerciseExecutionPOJODBS);
            editExerciseDialog.show(fragment.getParentFragmentManager(), "dialog");
                dismiss();
            }
        });

        goToInfoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("exerciseID", exerciseArrayList.get(position).getId());
                Navigation.findNavController(fragment.getView()).navigate(R.id.training_to_exercise_details, args);
                dismiss();
            }
        });
        return v;
    }
}
