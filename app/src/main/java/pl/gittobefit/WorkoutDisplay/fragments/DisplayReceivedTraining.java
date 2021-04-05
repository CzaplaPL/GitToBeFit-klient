package pl.gittobefit.WorkoutDisplay.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.adapters.ExerciseListAdapter;
import pl.gittobefit.WorkoutDisplay.dialog.DeleteTrainingDialog;
import pl.gittobefit.WorkoutDisplay.dialog.EditTrainingNameDialog;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.WorkoutDisplay.objects.UserTrainings;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;

public class DisplayReceivedTraining extends Fragment
{
    public DisplayReceivedTraining() {
    }

    private int index;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);
        model.getPosition().observe(getViewLifecycleOwner(), integer -> index = integer);
    }


    @Override
    public void onResume() {

        super.onResume();
        List<SavedTraining> result2 = AppDataBase.getInstance(getContext()).training().getInfoForTrainingList();

        if (index != UserTrainings.getInstance().getTrainingArrayList().size() && UserTrainings.getInstance().getTraining(index).getPlanList().size() == 0) {
            SavedTraining savedTraining = AppDataBase.getInstance(getContext()).training().getOneTraining(result2.get(index).getId());
            TrainingPlan trainingPlan;
            Training training = new Training();
            Exercise exercise;
            ArrayList<ExerciseExecution> exerciseExecutionArrayList;
            ArrayList<TrainingPlan> trainingPlanArrayList;
            trainingPlanArrayList = new ArrayList<>();
            for (int k = 0; k < savedTraining.getPlanList().size(); k++) {
                exerciseExecutionArrayList = new ArrayList<>();
                for (int j = 0; j < savedTraining.getPlanList().get(k).size(); j++) {
                    ExerciseExecution exerciseExecution = new ExerciseExecution();
                    exercise = new Exercise();
                    exercise.setName(AppDataBase.getInstance(getContext()).exercise().getExerciseList(savedTraining.getPlanList().get(k).get(j).getExerciseId()).getName());
                    exerciseExecution.setExercise(exercise);
                    exerciseExecution.setCount(savedTraining.getPlanList().get(k).get(j).getCount());
                    exerciseExecution.setSeries(savedTraining.getPlanList().get(k).get(j).getSeries());
                    exerciseExecution.setTime(savedTraining.getPlanList().get(k).get(j).getTime());
                    exerciseExecutionArrayList.add(exerciseExecution);
                }
                trainingPlan = new TrainingPlan();
                trainingPlan.setExercisesExecutions(exerciseExecutionArrayList);

                trainingPlanArrayList.add(trainingPlan);
                training.setPlanList(trainingPlanArrayList);
                UserTrainings.getInstance().getTraining(index).setPlanList(trainingPlanArrayList);
            }
        }


        TextView trainingType = getView().findViewById(R.id.trainingType);
        TextView trainingForm = getView().findViewById(R.id.trainingForm);
        TextView trainingDuration = getView().findViewById(R.id.trainingDuration);
        TextView trainingName = getView().findViewById(R.id.trainingName);


        RelativeLayout relativeLayout1 = getView().findViewById(R.id.layout0);
        RelativeLayout relativeLayout2 = getView().findViewById(R.id.layout1);
        RelativeLayout relativeLayout3 = getView().findViewById(R.id.layout2);
        RelativeLayout relativeLayout4 = getView().findViewById(R.id.layout3);
        RelativeLayout relativeLayout5 = getView().findViewById(R.id.layout4);


        ArrayList<RelativeLayout> linearLayoutArrayList = new ArrayList<>();
        linearLayoutArrayList.add(relativeLayout1);
        linearLayoutArrayList.add(relativeLayout2);
        linearLayoutArrayList.add(relativeLayout3);
        linearLayoutArrayList.add(relativeLayout4);
        linearLayoutArrayList.add(relativeLayout5);


        RecyclerView exercisesList = getView().findViewById(R.id.exercisesList);
        RecyclerView exercisesList2 = getView().findViewById(R.id.exercisesList2);
        RecyclerView exercisesList3 = getView().findViewById(R.id.exercisesList3);
        RecyclerView exercisesList4 = getView().findViewById(R.id.exercisesList4);
        RecyclerView exercisesList5 = getView().findViewById(R.id.exercisesList5);


        ArrayList<RecyclerView> recyclerViewArrayList = new ArrayList<>();
        recyclerViewArrayList.add(exercisesList);
        recyclerViewArrayList.add(exercisesList2);
        recyclerViewArrayList.add(exercisesList3);
        recyclerViewArrayList.add(exercisesList4);
        recyclerViewArrayList.add(exercisesList5);

        ArrayList<ExerciseListAdapter> exerciseListAdapters = new ArrayList<>();


        Button day1Button = getView().findViewById(R.id.day1_button);
        Button day2Button = getView().findViewById(R.id.day2_button);
        Button day3Button = getView().findViewById(R.id.day3_button);
        Button day4Button = getView().findViewById(R.id.day4_button);
        Button day5Button = getView().findViewById(R.id.day5_button);

        ArrayList<Button> buttonArrayList = new ArrayList<>();
        buttonArrayList.add(day1Button);
        buttonArrayList.add(day2Button);
        buttonArrayList.add(day3Button);
        buttonArrayList.add(day4Button);
        buttonArrayList.add(day5Button);

        Drawable rightDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_add);
        for (int i = 0; i < buttonArrayList.size(); i++) {
            buttonArrayList.get(i).setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
            buttonArrayList.get(i).setVisibility(View.GONE);
        }

        Button editTrainingNameButton = getView().findViewById(R.id.editTrainingNameButton);
        editTrainingNameButton.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("trainingID", String.format("%d/%d", index, result2.get(index).getId()));

            EditTrainingNameDialog editTrainingNameDialog = new EditTrainingNameDialog(getView());
            editTrainingNameDialog.setArguments(args);
            editTrainingNameDialog.show(getFragmentManager(), "dialog");
        });

        Button deleteTrainingButton = getView().findViewById(R.id.deleteTraining);
        deleteTrainingButton.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("trainingID", String.format("%d/%d", index, result2.get(index).getId()));

            DeleteTrainingDialog deleteTrainingDialog = new DeleteTrainingDialog(getView());
            deleteTrainingDialog.setArguments(args);
            deleteTrainingDialog.show(getFragmentManager(), "dialog");
        });


        day1Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList.getVisibility() == View.VISIBLE) {
                    relativeLayout1.setVisibility(View.GONE);
                    exercisesList.setVisibility(View.GONE);
                } else {
                    relativeLayout1.setVisibility(View.VISIBLE);
                    exercisesList.setVisibility(View.VISIBLE);
                }

            }
        });

        day2Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList2.getVisibility() == View.VISIBLE) {
                    relativeLayout2.setVisibility(View.GONE);
                    exercisesList2.setVisibility(View.GONE);
                } else {
                    relativeLayout2.setVisibility(View.VISIBLE);
                    exercisesList2.setVisibility(View.VISIBLE);
                }

            }
        });

        day3Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList3.getVisibility() == View.VISIBLE) {
                    relativeLayout3.setVisibility(View.GONE);
                    exercisesList3.setVisibility(View.GONE);
                } else {
                    relativeLayout3.setVisibility(View.VISIBLE);
                    exercisesList3.setVisibility(View.VISIBLE);
                }

            }
        });

        day4Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList4.getVisibility() == View.VISIBLE) {
                    relativeLayout4.setVisibility(View.GONE);
                    exercisesList4.setVisibility(View.GONE);
                } else {
                    relativeLayout4.setVisibility(View.VISIBLE);
                    exercisesList4.setVisibility(View.VISIBLE);
                }

            }
        });

        day5Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList5.getVisibility() == View.VISIBLE) {
                    relativeLayout5.setVisibility(View.GONE);
                    exercisesList5.setVisibility(View.GONE);
                } else {
                    relativeLayout5.setVisibility(View.VISIBLE);
                    exercisesList5.setVisibility(View.VISIBLE);
                }

            }
        });

        for (int i = 0; i < linearLayoutArrayList.size(); i++)
        {
            linearLayoutArrayList.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < recyclerViewArrayList.size(); i++)
        {
            recyclerViewArrayList.get(i).setVisibility(View.GONE);
        }

        if (UserTrainings.getInstance().getTrainingArrayList().size() != 0)
        {
            String trainingTypeDisplay = "Rodzaj treningu: " + UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType().toLowerCase();
            String nameOfTraining = "Nazwa treningu: " + UserTrainings.getInstance().getTraining(index).getTrainingName();
            String durationDisplay = "";
            String scheduleTypeDisplay = "";
            switch (UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType()) {
                case "SPLIT":
                case "FBW":
                    durationDisplay = "Czas treningu: " + UserTrainings.getInstance().getTraining(index).getTrainingForm().getDaysCount() + " dni";
                    if (UserTrainings.getInstance().getTraining(index).getTrainingForm().getScheduleType().equals("PER_DAY")) {
                        scheduleTypeDisplay = "Forma treningu: na dzień";
                    } else {
                        scheduleTypeDisplay = "Forma treningu: powtarzalny";
                    }
                    for (int i = 0; i < UserTrainings.getInstance().getTraining(index).getTrainingForm().getDaysCount(); i++) {
                        buttonArrayList.get(i).setVisibility(View.VISIBLE);
                    }

                    break;
                case "CARDIO":
                case "FITNESS":
                    durationDisplay = "Czas treningu: " + UserTrainings.getInstance().getTraining(index).getTrainingForm().getDuration() + " minut";
                    if (UserTrainings.getInstance().getTraining(index).getTrainingForm().getScheduleType().equals("SERIES")) {
                        scheduleTypeDisplay = "Forma treningu: seriowy";
                    } else {
                        scheduleTypeDisplay = "Forma treningu: obwodowy";
                    }
                    day1Button.setVisibility(View.VISIBLE);
                    break;
            }
            trainingName.setText(nameOfTraining);
            trainingType.setText(trainingTypeDisplay);
            trainingForm.setText(scheduleTypeDisplay);
            trainingDuration.setText(durationDisplay);

            for (int i = 0; i < UserTrainings.getInstance().getTraining(index).getPlanList().size(); i++)
            {
                exerciseListAdapters.add(new ExerciseListAdapter(UserTrainings.getInstance().getTraining(index).getTrainingPlan(i).getExercisesExecutions(), this));
                recyclerViewArrayList.get(i).addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                recyclerViewArrayList.get(i).setAdapter(exerciseListAdapters.get(i));
                if (UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType().equals("FBW") || UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType().equals("SPLIT")) {
                    recyclerViewArrayList.get(i).setNestedScrollingEnabled(false);
                }

                recyclerViewArrayList.get(i).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            }

        }
    }
}
