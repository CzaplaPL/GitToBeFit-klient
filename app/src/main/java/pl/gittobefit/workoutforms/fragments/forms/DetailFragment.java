package pl.gittobefit.workoutforms.fragments.forms;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.adapters.BodyPartsAdapter;
import pl.gittobefit.workoutforms.object.BodyParts;
import pl.gittobefit.workoutforms.object.TrainingDetails;
import pl.gittobefit.workoutforms.viewmodel.DetailsViewModel;

/**
 fragment tab2
 */
public class DetailFragment extends Fragment {

    public DetailFragment() { }
    DetailsViewModel detailsViewModel;
    ArrayList<BodyParts> bodyPartsToChooseForSplit;
    ArrayList<BodyParts> bodyPartsToChooseForFitness;

    BodyPartsAdapter bodyPartsAdapterForSplit;
    BodyPartsAdapter bodyPartsAdapterForFitness;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_detail_form, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsViewModel =  new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        detailsViewModel.setSplitList(bodyPartsToChooseForSplit);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Spinner spinner1, spinner2, spinner3;
        spinner1 = (Spinner) getView().findViewById(R.id.typeSpinner);
        spinner2 = (Spinner) getView().findViewById(R.id.waySpinner);
        spinner3 = (Spinner) getView().findViewById(R.id.frequencySpinner);


        recyclerView = getView().findViewById(R.id.myRecycleView);

        if(detailsViewModel.getPositionSpinner1() == 3)
        {
            bodyPartsToChooseForFitness = detailsViewModel.getFitnessList();
            bodyPartsAdapterForFitness = new BodyPartsAdapter(bodyPartsToChooseForFitness);
            recyclerView.setAdapter(bodyPartsAdapterForFitness);
        }
        else
        {
            bodyPartsToChooseForSplit = detailsViewModel.getSplitList();
            bodyPartsAdapterForSplit = new BodyPartsAdapter(bodyPartsToChooseForSplit);
            recyclerView.setAdapter(bodyPartsAdapterForSplit);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.array1, R.layout.my_spinner);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(detailsViewModel.getPositionSpinner1());

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.training_subtype, R.layout.my_spinner);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(detailsViewModel.getPositionSpinner2());

        spinner3.setSelection(detailsViewModel.getPositionSpinner3());

        TextView wot;
        wot = getView().findViewById(R.id.titleWay);

        TextView bp = getView().findViewById(R.id.bodyPartsText);

        detailsViewModel.select(new TrainingDetails("Trening fbw", "3 dni", null, detailsViewModel.getSplitList(),detailsViewModel.getFitnessList()));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner1.getSelectedItem().equals("Trening split")) {

                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.split_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);

                    spinner2.setVisibility(View.GONE);

                    recyclerView.setAdapter(bodyPartsAdapterForSplit);
                    recyclerView.setVisibility(View.VISIBLE);

                    wot.setVisibility(View.GONE);
                    bp.setVisibility(View.VISIBLE);

                    detailsViewModel.setPositionSpinner1(position);
                    spinner3.setSelection(0);
                    detailsViewModel.setTrainingDetails("Trening split",null,"3 dni");
                }
                else if (spinner1.getSelectedItem().equals("Trening fbw")){
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.fbw_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);

                    spinner2.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);

                    wot.setVisibility(View.GONE);
                    bp.setVisibility(View.GONE);


                    detailsViewModel.setPositionSpinner1(position);
                    spinner3.setSelection(0);
                    detailsViewModel.setTrainingDetails("Trening fbw",null,"3 dni");
                }
                else if (spinner1.getSelectedItem().equals("Trening cardio")){
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.fintess_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);

                    spinner2.setVisibility(View.VISIBLE);

                    recyclerView.setVisibility(View.GONE);

                    wot.setVisibility(View.VISIBLE);
                    bp.setVisibility(View.GONE);

                    detailsViewModel.setPositionSpinner1(position);
                    spinner3.setSelection(detailsViewModel.getPositionSpinner3());
                    detailsViewModel.setTrainingDetails("Trening cardio","Serie","9 minut");
                }
                else if (spinner1.getSelectedItem().equals("Trening fitness")){
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.fintess_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);

                    spinner2.setVisibility(View.VISIBLE);

                    bodyPartsToChooseForFitness = detailsViewModel.getFitnessList();
                    bodyPartsAdapterForFitness = new BodyPartsAdapter(bodyPartsToChooseForFitness);
                    recyclerView.setAdapter(bodyPartsAdapterForFitness);
                    recyclerView.setVisibility(View.VISIBLE);

                    wot.setVisibility(View.VISIBLE);
                    bp.setVisibility(View.VISIBLE);

                    detailsViewModel.setPositionSpinner1(position);
                    spinner3.setSelection(detailsViewModel.getPositionSpinner3());
                    detailsViewModel.setTrainingDetails("Trening fitness","Serie","9 minut");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner2.getSelectedItem().equals("Serie"))
                {
                    detailsViewModel.setPositionSpinner2(position);
                    detailsViewModel.setTrainingDetails("Serie");
                }
                else
                {
                    detailsViewModel.setPositionSpinner2(position);
                    detailsViewModel.setTrainingDetails("Czas");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                detailsViewModel.setPositionSpinner3(spinner3.getSelectedItemPosition());

                if (spinner3.getSelectedItem().equals("3 dni"))
                {
                    detailsViewModel.setTrainingDetails("3 dni",2);
                }
                else if  (spinner3.getSelectedItem().equals("4 dni"))
                {
                    detailsViewModel.setTrainingDetails("4 dni",2);
                }
                else if (spinner3.getSelectedItem().equals("5 dni"))
                {
                    detailsViewModel.setTrainingDetails("5 dni",2);
                }
                else
                {
                    detailsViewModel.setTrainingDetails(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}