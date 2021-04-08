package pl.gittobefit.WorkoutDisplay.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.adapters.ExerciseListAdapter;
import pl.gittobefit.WorkoutDisplay.dialog.DeleteTrainingDialog;
import pl.gittobefit.WorkoutDisplay.dialog.EditTrainingNameDialog;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.repository.TrainingRepository;

public class DisplayReceivedTraining extends Fragment
{
    public DisplayReceivedTraining() {
    }

    private int index;
    private InitiationTrainingDisplayLayoutViewModel model;

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
        model = new ViewModelProvider(requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);
        model.getPosition().observe(getViewLifecycleOwner(), integer -> index = integer);
    }


    @Override
    public void onResume() {

        super.onResume();
        ArrayList<ArrayList<Exercise>> exercisesArrayList = new ArrayList<>();
        TrainingWithForm trainingWithForm = model.getTrainingWithForms().get(index);

        for (int i = 0; i < trainingWithForm.training.getPlanList().size(); i++)
        {
            exercisesArrayList.add(TrainingRepository.getInstance(getContext()).getExerciseForPlanList(trainingWithForm.training.getPlanList().get(i)));
        }

        if(model.getLastIndex() != index)
        {
            model.setStatesExerciseLists(exercisesArrayList.size());
            model.setLastIndex(index);
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
            args.putString("trainingID", String.format("%d/%d", index, trainingWithForm.training.getId()));

            EditTrainingNameDialog editTrainingNameDialog = new EditTrainingNameDialog(getView());
            editTrainingNameDialog.setArguments(args);
            editTrainingNameDialog.show(getFragmentManager(), "dialog");
        });

        Button deleteTrainingButton = getView().findViewById(R.id.deleteTraining);
        deleteTrainingButton.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("trainingID", String.format("%d/%d", index, trainingWithForm.training.getId()));

            DeleteTrainingDialog deleteTrainingDialog = new DeleteTrainingDialog(getView());
            deleteTrainingDialog.setArguments(args);
            deleteTrainingDialog.show(getFragmentManager(), "dialog");
        });


        day1Button.setOnClickListener(v -> {
            if (exercisesList.getVisibility() == View.VISIBLE) {
                relativeLayout1.setVisibility(View.GONE);
                exercisesList.setVisibility(View.GONE);
            } else {
                relativeLayout1.setVisibility(View.VISIBLE);
                exercisesList.setVisibility(View.VISIBLE);
            }
            model.setState(0);

        });

        day2Button.setOnClickListener(v -> {
            if (exercisesList2.getVisibility() == View.VISIBLE) {
                relativeLayout2.setVisibility(View.GONE);
                exercisesList2.setVisibility(View.GONE);
            } else {
                relativeLayout2.setVisibility(View.VISIBLE);
                exercisesList2.setVisibility(View.VISIBLE);
            }
            model.setState(1);

        });

        day3Button.setOnClickListener(v -> {
            if (exercisesList3.getVisibility() == View.VISIBLE) {
                relativeLayout3.setVisibility(View.GONE);
                exercisesList3.setVisibility(View.GONE);
            } else {
                relativeLayout3.setVisibility(View.VISIBLE);
                exercisesList3.setVisibility(View.VISIBLE);
            }
            model.setState(2);

        });

        day4Button.setOnClickListener(v -> {
            if (exercisesList4.getVisibility() == View.VISIBLE) {
                relativeLayout4.setVisibility(View.GONE);
                exercisesList4.setVisibility(View.GONE);
            } else {
                relativeLayout4.setVisibility(View.VISIBLE);
                exercisesList4.setVisibility(View.VISIBLE);
            }
            model.setState(3);

        });

        day5Button.setOnClickListener(v -> {
            if (exercisesList5.getVisibility() == View.VISIBLE) {
                relativeLayout5.setVisibility(View.GONE);
                exercisesList5.setVisibility(View.GONE);
            } else {
                relativeLayout5.setVisibility(View.VISIBLE);
                exercisesList5.setVisibility(View.VISIBLE);
            }
            model.setState(4);

        });

        for (int i = 0; i < linearLayoutArrayList.size(); i++) {
            linearLayoutArrayList.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < recyclerViewArrayList.size(); i++) {
            recyclerViewArrayList.get(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < model.getStates().size(); i++) {
            if (model.getStates().get(i))
            {
                linearLayoutArrayList.get(i).setVisibility(View.VISIBLE);
            }
        }
        for (int i = 0; i < model.getStates().size(); i++) {
            if (model.getStates().get(i))
            {
                recyclerViewArrayList.get(i).setVisibility(View.VISIBLE);
            }

        }


        String trainingTypeDisplay = trainingWithForm.form.getTrainingType().toLowerCase();
        String nameOfTraining = trainingWithForm.training.getTrainingName();
        String durationDisplay = "";
        String scheduleTypeDisplay = "";
        switch (trainingWithForm.form.getTrainingType()) {
            case "SPLIT":
            case "FBW":
                durationDisplay = trainingWithForm.form.getDaysCount() + " dni";
                if (trainingWithForm.form.getScheduleType().equals("PER_DAY")) {
                    scheduleTypeDisplay = "wykonujesz każdego dnia inny trening";
                } else {
                    scheduleTypeDisplay = "wykonujesz każdego dnia taki sam trening";
                }
                for (int i = 0; i < trainingWithForm.form.getDaysCount(); i++) {
                    buttonArrayList.get(i).setVisibility(View.VISIBLE);
                }

                break;
            case "CARDIO":
            case "FITNESS":
                durationDisplay = trainingWithForm.form.getDuration() + " minut";
                if (trainingWithForm.form.getScheduleType().equals("SERIES")) {
                    scheduleTypeDisplay = "wykonujesz wybraną ilość serii każdego ćwiczenia";
                } else {
                    scheduleTypeDisplay = "wykonujesz jedno ćwiczenie po drugim, z przerwami pomiędzy nimi, bądź bez";
                }
                day1Button.setVisibility(View.VISIBLE);
                break;
        }
        String trainingTimeString = "Czas treningu: ";
        SpannableStringBuilder timeBuilder = getSpannableStringBuilder(durationDisplay, trainingTimeString);

        String formTrainingString = "Forma treningu: ";
        SpannableStringBuilder formBuilder = getSpannableStringBuilder(scheduleTypeDisplay, formTrainingString);

        String trainingTypeString = "Rodzaj treningu: ";
        SpannableStringBuilder typeBuilder = getSpannableStringBuilder(trainingTypeDisplay, trainingTypeString);

        String trainingNameString = "Nazwa treningu: ";
        SpannableStringBuilder nameBuilder = getSpannableStringBuilder(nameOfTraining, trainingNameString);

        trainingName.setText(nameBuilder, TextView.BufferType.SPANNABLE);
        trainingType.setText(typeBuilder, TextView.BufferType.SPANNABLE);
        trainingForm.setText(formBuilder, TextView.BufferType.SPANNABLE);
        trainingDuration.setText(timeBuilder, TextView.BufferType.SPANNABLE);
        trainingDuration.setVisibility(View.GONE);

        for (int i = 0; i < exercisesArrayList.size(); i++) {
            exerciseListAdapters.add(new ExerciseListAdapter(exercisesArrayList.get(i), trainingWithForm.training.getPlanList().get(i), trainingWithForm.form.getScheduleType(), trainingWithForm.training.getId(),this));
            recyclerViewArrayList.get(i).addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            recyclerViewArrayList.get(i).setAdapter(exerciseListAdapters.get(i));
            if (trainingWithForm.form.getTrainingType().equals("FBW") || trainingWithForm.form.getTrainingType().equals("SPLIT")) {
                recyclerViewArrayList.get(i).setNestedScrollingEnabled(false);
            }

            recyclerViewArrayList.get(i).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }


    private SpannableStringBuilder getSpannableStringBuilder(String durationDisplay, String stringToColor) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString redSpannable= new SpannableString(stringToColor);
        redSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.ourGreen)), 0, stringToColor.length(), 0);
        builder.append(redSpannable);
        builder.append(durationDisplay);
        return builder;
    }
}
