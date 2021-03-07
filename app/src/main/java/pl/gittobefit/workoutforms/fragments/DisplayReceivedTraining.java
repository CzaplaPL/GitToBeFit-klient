package pl.gittobefit.workoutforms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.adapters.ExerciseListAdapter;
import pl.gittobefit.workoutforms.object.UserTrainings;

public class DisplayReceivedTraining extends Fragment
{
    public DisplayReceivedTraining() {}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received_training, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView trainingType = getView().findViewById(R.id.trainingType);
        TextView trainingForm = getView().findViewById(R.id.trainingForm);
        TextView trainingDuration = getView().findViewById(R.id.trainingDuration);

        TextView day1 = getView().findViewById(R.id.day1);
        TextView day2 = getView().findViewById(R.id.day2);
        TextView day3 = getView().findViewById(R.id.day3);
        TextView day4 = getView().findViewById(R.id.day4);
        TextView day5 = getView().findViewById(R.id.day5);
        TextView day6 = getView().findViewById(R.id.day6);
        TextView day7 = getView().findViewById(R.id.day7);

        ArrayList<TextView> textViewArrayList = new ArrayList<>();
        textViewArrayList.add(day1);
        textViewArrayList.add(day2);
        textViewArrayList.add(day3);
        textViewArrayList.add(day4);
        textViewArrayList.add(day5);
        textViewArrayList.add(day6);
        textViewArrayList.add(day7);

        LinearLayout linearLayout1 = getView().findViewById(R.id.layout1);
        LinearLayout linearLayout2 = getView().findViewById(R.id.layout2);
        LinearLayout linearLayout3 = getView().findViewById(R.id.layout3);
        LinearLayout linearLayout4 = getView().findViewById(R.id.layout4);
        LinearLayout linearLayout5 = getView().findViewById(R.id.layout5);
        LinearLayout linearLayout6 = getView().findViewById(R.id.layout6);

        ArrayList<LinearLayout> linearLayoutArrayList = new ArrayList<>();
        linearLayoutArrayList.add(linearLayout1);
        linearLayoutArrayList.add(linearLayout2);
        linearLayoutArrayList.add(linearLayout3);
        linearLayoutArrayList.add(linearLayout4);
        linearLayoutArrayList.add(linearLayout5);
        linearLayoutArrayList.add(linearLayout6);

        RecyclerView exercisesList = getView().findViewById(R.id.exercisesList);
        RecyclerView exercisesList2 = getView().findViewById(R.id.exercisesList2);
        RecyclerView exercisesList3 = getView().findViewById(R.id.exercisesList3);
        RecyclerView exercisesList4 = getView().findViewById(R.id.exercisesList4);
        RecyclerView exercisesList5 = getView().findViewById(R.id.exercisesList5);
        RecyclerView exercisesList6 = getView().findViewById(R.id.exercisesList6);
        RecyclerView exercisesList7 = getView().findViewById(R.id.exercisesList7);

        ArrayList<RecyclerView> recyclerViewArrayList = new ArrayList<>();
        recyclerViewArrayList.add(exercisesList);
        recyclerViewArrayList.add(exercisesList2);
        recyclerViewArrayList.add(exercisesList3);
        recyclerViewArrayList.add(exercisesList4);
        recyclerViewArrayList.add(exercisesList5);
        recyclerViewArrayList.add(exercisesList6);
        recyclerViewArrayList.add(exercisesList7);
        ArrayList<ExerciseListAdapter> exerciseListAdapters = new ArrayList<>();


        if (UserTrainings.getInstance().getTrainingArrayList().size() != 0 )
        {
            String trainingTypeDisplay = "Rodzaj treningu: " + UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType();
            String scheduleTypeDisplay = "Forma treningu: " + UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getScheduleType();
            String durationDisplay = "Czas treningu: " + UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getDuration();
            trainingType.setText(trainingTypeDisplay);
            trainingForm.setText(scheduleTypeDisplay);
            trainingDuration.setText(durationDisplay);

            if (UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType().equals("FITNESS") ||
                    UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType().equals("CARDIO"))
            {
                for (int i = 0; i < textViewArrayList.size(); i++)
                {
                    textViewArrayList.get(i).setVisibility(View.GONE);
                }
                for (int i = 0; i < linearLayoutArrayList.size(); i++)
                {
                    linearLayoutArrayList.get(i).setVisibility(View.GONE);
                }
            }
            else if (UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType().equals("FBW"))
            {
                int x = UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getDaysCount();

                for (int i = x; i < textViewArrayList.size(); i++)
                {
                    textViewArrayList.get(i).setVisibility(View.GONE);
                }
                for (int i = x-1; i < linearLayoutArrayList.size(); i++)
                {
                    linearLayoutArrayList.get(i).setVisibility(View.GONE);
                }
            }

            for (int i = 0; i < UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getPlanList().size(); i++)
            {
                for(int j = 0; j < UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingPlan(i).getExercisesExecutions().size(); j++ )
                {
                    String setDay = "Dzien " + (i + 1);
                    textViewArrayList.get(i).setText(setDay);
                    exerciseListAdapters.add(new ExerciseListAdapter(UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingPlan(i).getExercisesExecutions()));
                    recyclerViewArrayList.get(i).addItemDecoration(new DividerItemDecoration(getContext(),
                            DividerItemDecoration.VERTICAL));
                    recyclerViewArrayList.get(i).setAdapter(exerciseListAdapters.get(i));
                    recyclerViewArrayList.get(i).setNestedScrollingEnabled(false);
                    recyclerViewArrayList.get(i).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                }
            }
        }

    }
}
