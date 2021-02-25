package pl.gittobefit.workoutforms.fragments.forms;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentDetailFormBinding;
import pl.gittobefit.workoutforms.adapters.BodyPartsAdapter;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

/**
 fragment tab2
 */
public class DetailFragment extends Fragment {

    private FragmentDetailFormBinding binding;
    private GenerateTraningViewModel model;
    BodyPartsAdapter bodyPartsAdapter ;
    public DetailFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model= new ViewModelProvider(requireActivity()).get(GenerateTraningViewModel.class);


        model.setTypeDesciptionText(getString(R.string.split));
        model.setDetailDesciptionText(getString(R.string.series));
        model.setTimeDesciptionText(getString(R.string.days));
        /*model.getTypeDesciptionText().observe(getViewLifecycleOwner(), string -> binding.typeDesciption.setText(string));
        model.getDetailDesciptionText().observe(getViewLifecycleOwner(), string -> binding.wayDescription.setText(string));
        model.getTimeDesciptionText().observe(getViewLifecycleOwner(), string -> binding.frequencyDescription.setText(string));*/
        model.setBodyPartsSplit(getContext());

        //tworzenie 1 spinera
        bodyPartsAdapter = new BodyPartsAdapter(model.getBodyParts());
        binding.myRecycleView.setAdapter(bodyPartsAdapter);
        binding.myRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.array1, R.layout.my_spinner);
        binding.typeSpinner.setAdapter(adapter1);
        model.getTypeSpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                changeTypeSpiner(position);
            }
        });
        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
               model.setTypeSpinnerChose(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //tworzenie 2 spinera
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.training_subtype, R.layout.my_spinner);
        binding.waySpinner.setAdapter(adapter2);
        model.getWaySpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                waySelect(position);
            }
        });
        binding.waySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setWaySpinnerChose(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //tworzenie 3 spinera
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.split_duration, R.layout.my_spinner);
        binding.frequencySpinner.setAdapter(adapter3);
        model.getfrequencySpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                frequencySelect(position);
            }
        });
        binding.frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setfrequencySpinnerChose(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //tworzenie 4 spinera
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getContext(),
                R.array.fintess_duration, R.layout.my_spinner);
        binding.timeSpinner.setAdapter(adapter4);
        model.getTimeSpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                timeSelect(position);
            }
        });
        binding.timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setTimeSpinnerChose(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    binding.timeSpinner.setVisibility(View.GONE);
    binding.titleTime.setVisibility(View.GONE);
    }

    private void timeSelect(Integer position)
    {

    }

    private void changeTypeSpiner(Integer position)
    {
      /*  if (spinner1.getSelectedItem().equals("Trening split")) {

            ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                    R.array.split_duration, R.layout.support_simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);

            spinner2.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            std.setVisibility(View.GONE);
            wot.setVisibility(View.GONE);
            bp.setVisibility(View.VISIBLE);

            td.setText(split);

            detailsViewModel.setPositionSpinner1(position);
            spinner3.setSelection(0);
            detailsViewModel.setTrainingDetails("Trening split",null,"3 dni");
        }
        else if (spinner1.getSelectedItem().equals("Trening fbw")){
            ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                    R.array.fbw_duration, R.layout.support_simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);
            model.setTypeDesciptionText("eeeeeeeeeeeeeeeeeeeeeeeee");
            spinner2.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);

            std.setVisibility(View.GONE);
            wot.setVisibility(View.GONE);
            bp.setVisibility(View.GONE);
            Log.w("test",model.getTypeDesciptionText().toString());
            td.setText(fbw);

            detailsViewModel.setPositionSpinner1(position);
            spinner3.setSelection(0);
            detailsViewModel.setTrainingDetails("Trening fbw",null,"3 dni");
        }
        else if (spinner1.getSelectedItem().equals("Trening cardio")){
            ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                    R.array.fintess_duration, R.layout.support_simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);

            spinner2.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            std.setVisibility(View.VISIBLE);
            wot.setVisibility(View.VISIBLE);
            bp.setVisibility(View.GONE);

            td.setText(cardio);

            detailsViewModel.setPositionSpinner1(position);
            spinner3.setSelection(detailsViewModel.getPositionSpinner3());
            detailsViewModel.setTrainingDetails("Trening cardio","Serie","9 minut");
        }
        else if (spinner1.getSelectedItem().equals("Trening fitness")){
            ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                    R.array.fintess_duration, R.layout.support_simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);

            spinner2.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            std.setVisibility(View.VISIBLE);
            wot.setVisibility(View.VISIBLE);
            bp.setVisibility(View.GONE);

            td.setText(fitness);

            detailsViewModel.setPositionSpinner1(position);
            spinner3.setSelection(detailsViewModel.getPositionSpinner3());
            detailsViewModel.setTrainingDetails("Trening fitness","Serie","9 minut");
        }*/
    }

    private void waySelect(int position)
    {
        if (position==0)
        {
                  /*  std.setText(series);
                    detailsViewModel.setPositionSpinner2(position);
                    detailsViewModel.setTrainingDetails("Serie");*/
        }
        else
        {
                   /* std.setText(circuit);
                    detailsViewModel.setPositionSpinner2(position);
                    detailsViewModel.setTrainingDetails("Czas");*/
        }
    }
    private void frequencySelect(Integer position)
    {
       /* detailsViewModel.setPositionSpinner3(spinner3.getSelectedItemPosition());

        if (spinner3.getSelectedItem().equals("3 dni"))
        {
            dd.setText(days);
            detailsViewModel.setTrainingDetails("3 dni",2);
        }
        else if  (spinner3.getSelectedItem().equals("4 dni"))
        {
            dd.setText(days);
            detailsViewModel.setTrainingDetails("4 dni",2);
        }
        else if (spinner3.getSelectedItem().equals("5 dni"))
        {
            dd.setText(days);
            detailsViewModel.setTrainingDetails("5 dni",2);
        }
        else
        {
            dd.setText(minutes);
            detailsViewModel.setTrainingDetails(position);
        }
*/
    }
}