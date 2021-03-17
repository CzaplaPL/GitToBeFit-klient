package pl.gittobefit.workoutforms.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.adapters.ExerciseListAdapter;
import pl.gittobefit.workoutforms.object.ExerciseExecution;
import pl.gittobefit.workoutforms.object.UserTrainings;
import pl.gittobefit.workoutforms.viewmodel.InitiationTrainingDisplayLayoutViewModel;

public class DisplayReceivedTraining extends Fragment
{
    public DisplayReceivedTraining() {}
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


        System.out.println("Indeks:" + index);
        TextView trainingType = getView().findViewById(R.id.trainingType);
        TextView trainingForm = getView().findViewById(R.id.trainingForm);
        TextView trainingDuration = getView().findViewById(R.id.trainingDuration);


        RelativeLayout linearLayout0 = getView().findViewById(R.id.layout0);
        RelativeLayout linearLayout1 = getView().findViewById(R.id.layout1);
        RelativeLayout linearLayout2 = getView().findViewById(R.id.layout2);
        RelativeLayout linearLayout3 = getView().findViewById(R.id.layout3);
        RelativeLayout linearLayout4 = getView().findViewById(R.id.layout4);


        ArrayList<RelativeLayout> linearLayoutArrayList = new ArrayList<>();
        linearLayoutArrayList.add(linearLayout0);
        linearLayoutArrayList.add(linearLayout1);
        linearLayoutArrayList.add(linearLayout2);
        linearLayoutArrayList.add(linearLayout3);
        linearLayoutArrayList.add(linearLayout4);


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
        for (int i = 0; i < buttonArrayList.size(); i++)
        {
            buttonArrayList.get(i).setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
            buttonArrayList.get(i).setVisibility(View.GONE);
        }



        day1Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList.getVisibility() == View.VISIBLE)
                {
                    linearLayout0.setVisibility(View.GONE);
                    exercisesList.setVisibility(View.GONE);
                }
                else
                {
                    linearLayout0.setVisibility(View.VISIBLE);
                    exercisesList.setVisibility(View.VISIBLE);
                }

            }
        });

        day2Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList2.getVisibility() == View.VISIBLE)
                {
                    linearLayout1.setVisibility(View.GONE);
                    exercisesList2.setVisibility(View.GONE);
                }
                else
                {
                    linearLayout1.setVisibility(View.VISIBLE);
                    exercisesList2.setVisibility(View.VISIBLE);
                }

            }
        });

        day3Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList3.getVisibility() == View.VISIBLE)
                {
                    linearLayout2.setVisibility(View.GONE);
                    exercisesList3.setVisibility(View.GONE);
                }
                else
                {
                    linearLayout2.setVisibility(View.VISIBLE);
                    exercisesList3.setVisibility(View.VISIBLE);
                }

            }
        });

        day4Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList4.getVisibility() == View.VISIBLE)
                {
                    linearLayout3.setVisibility(View.GONE);
                    exercisesList4.setVisibility(View.GONE);
                }
                else
                {
                    linearLayout3.setVisibility(View.VISIBLE);
                    exercisesList4.setVisibility(View.VISIBLE);
                }

            }
        });

        day5Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (exercisesList5.getVisibility() == View.VISIBLE)
                {
                    linearLayout4.setVisibility(View.GONE);
                    exercisesList5.setVisibility(View.GONE);
                }
                else
                {
                    linearLayout4.setVisibility(View.VISIBLE);
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

        if (UserTrainings.getInstance().getTrainingArrayList().size() != 0 )
        {
            if(index ==  -999)
            {
                String trainingTypeDisplay = "Rodzaj treningu: " + UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType().toLowerCase();
                String durationDisplay = "";
                String scheduleTypeDisplay = "";
                switch (UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType()) {
                    case "SPLIT":
                    case "FBW":
                        durationDisplay = "Czas treningu: " + UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getDaysCount() + " dni";
                        if( UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getScheduleType().equals("PER_DAY"))
                        {
                            scheduleTypeDisplay = "Forma treningu: na dzień";
                        }
                        else
                        {
                            scheduleTypeDisplay = "Forma treningu: powtarzalny";
                        }
                        for (int i = 0; i < UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getDaysCount(); i++)
                        {
                            buttonArrayList.get(i).setVisibility(View.VISIBLE);
                        }

                        break;
                    case "CARDIO":
                    case "FITNESS":
                        durationDisplay = "Czas treningu: " + UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getDuration() + " minut";
                        if( UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getScheduleType().equals("SERIES"))
                        {
                            scheduleTypeDisplay = "Forma treningu: seriowy";
                        }
                        else
                        {
                            scheduleTypeDisplay = "Forma treningu: obwodowy";
                        }
                        day1Button.setVisibility(View.VISIBLE);
                        break;
                }

                trainingType.setText(trainingTypeDisplay);
                trainingForm.setText(scheduleTypeDisplay);
                trainingDuration.setText(durationDisplay);

                for (int i = 0; i < UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getPlanList().size(); i++)
                {
                    exerciseListAdapters.add(new ExerciseListAdapter(UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingPlan(i).getExercisesExecutions()));
                    recyclerViewArrayList.get(i).addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    recyclerViewArrayList.get(i).setAdapter(exerciseListAdapters.get(i));
                    if (UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType().equals("FBW") ||
                            UserTrainings.getInstance().getTraining(UserTrainings.getInstance().getTrainingArrayList().size() - 1).getTrainingForm().getTrainingType().equals("SPLIT"))
                    {
                        recyclerViewArrayList.get(i).setNestedScrollingEnabled(false);
                    }

                    recyclerViewArrayList.get(i).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                }
            }
            else
            {
                String trainingTypeDisplay = "Rodzaj treningu: " + UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType().toLowerCase();
                String durationDisplay = "";
                String scheduleTypeDisplay = "";
                switch (UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType()) {
                    case "SPLIT":
                    case "FBW":
                        durationDisplay = "Czas treningu: " + UserTrainings.getInstance().getTraining(index).getTrainingForm().getDaysCount() + " dni";
                        if( UserTrainings.getInstance().getTraining(index).getTrainingForm().getScheduleType().equals("PER_DAY"))
                        {
                            scheduleTypeDisplay = "Forma treningu: na dzień";
                        }
                        else
                        {
                            scheduleTypeDisplay = "Forma treningu: powtarzalny";
                        }
                        for (int i = 0; i < UserTrainings.getInstance().getTraining(index).getTrainingForm().getDaysCount(); i++)
                        {
                            buttonArrayList.get(i).setVisibility(View.VISIBLE);
                        }

                        break;
                    case "CARDIO":
                    case "FITNESS":
                        durationDisplay = "Czas treningu: " + UserTrainings.getInstance().getTraining(index).getTrainingForm().getDuration() + " minut";
                        if( UserTrainings.getInstance().getTraining(index).getTrainingForm().getScheduleType().equals("SERIES"))
                        {
                            scheduleTypeDisplay = "Forma treningu: seriowy";
                        }
                        else
                        {
                            scheduleTypeDisplay = "Forma treningu: obwodowy";
                        }
                        day1Button.setVisibility(View.VISIBLE);
                        break;
                }

                trainingType.setText(trainingTypeDisplay);
                trainingForm.setText(scheduleTypeDisplay);
                trainingDuration.setText(durationDisplay);

                for (int i = 0; i < UserTrainings.getInstance().getTraining(index).getPlanList().size(); i++)
                {
                    exerciseListAdapters.add(new ExerciseListAdapter(UserTrainings.getInstance().getTraining(index).getTrainingPlan(i).getExercisesExecutions()));
                    recyclerViewArrayList.get(i).addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    recyclerViewArrayList.get(i).setAdapter(exerciseListAdapters.get(i));
                    if (UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType().equals("FBW") ||
                            UserTrainings.getInstance().getTraining(index).getTrainingForm().getTrainingType().equals("SPLIT"))
                    {
                        recyclerViewArrayList.get(i).setNestedScrollingEnabled(false);
                    }

                    recyclerViewArrayList.get(i).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                }
            }




        }
    }
}
