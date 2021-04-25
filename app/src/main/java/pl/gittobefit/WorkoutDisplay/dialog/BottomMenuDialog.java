package pl.gittobefit.WorkoutDisplay.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS;

    public BottomMenuDialog(
            ArrayList<Exercise> exerciseArrayList,
            Fragment fragment,
            String scheduleType,
            int position,
            ArrayList<ExerciseExecutionPOJODB> exercisesExecutionArrayList,
            int trainingID,
            ArrayList<ArrayList<ExerciseExecutionPOJODB>> exerciseExecutionPOJODBS
    )
    {
        this.exerciseArrayList = exerciseArrayList;
        this.fragment = fragment;
        this.scheduleType = scheduleType;
        this.position = position;
        this.exercisesExecutionArrayList = exercisesExecutionArrayList;
        this.trainingID = trainingID;
        this.exerciseExecutionPOJODBS = exerciseExecutionPOJODBS;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_bottom_menu_dialog, container, false);
        Button goToEditButton = v.findViewById(R.id.goToEditButton);
        Button goToChangeButton = v.findViewById(R.id.goToChangeButton);
        Button goToInfoButton = v.findViewById(R.id.goToInfoButton);

        TextView title = v.findViewById(R.id.menu_title);
        title.setText(exerciseArrayList.get(position).getName());

        goToChangeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("exerciseID", exerciseArrayList.get(position).getId());
                Navigation.findNavController(fragment.getView()).navigate(R.id.training_to_change_exercise, args);
            dismiss();
            }
        });

        goToEditButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                EditExerciseDialog editExerciseDialog = new EditExerciseDialog(
                        fragment.getView(),
                        scheduleType,
                        position ,
                        exercisesExecutionArrayList,
                        trainingID,
                        exerciseArrayList.get(position).getName(),
                        exerciseExecutionPOJODBS
                );
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
