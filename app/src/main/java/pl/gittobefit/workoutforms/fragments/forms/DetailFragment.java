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
        model.setBodyPartsSplit(getContext());

        //tworzenie 1 spinera
        bodyPartsAdapter = new BodyPartsAdapter(model.getBodyParts());
        binding.myRecycleView.setAdapter(bodyPartsAdapter);
        binding.myRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.trening_type, R.layout.my_spinner);
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
    binding.waySpinner.setVisibility(View.GONE);
    binding.titleWay.setVisibility(View.GONE);
        //tworzenie 3 spinera
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.split_duration, R.layout.my_spinner);
        binding.frequencySpinner.setAdapter(adapter3);

        binding.frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setFrequencySpinnerChose(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //tworzenie 4 spinera
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getContext(),
                R.array.fintess_duration, R.layout.my_spinner);
        binding.timeSpinner.setAdapter(adapter4);
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
        //tworzenie 5 spinera
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(getContext(),
                R.array.fbw_sheduletype, R.layout.my_spinner);
        binding.scheduleTypeSpinner.setAdapter(adapter5);
        binding.scheduleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setScheduleSpinnerChose(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        binding.scheduleTypeSpinner.setVisibility(View.GONE);
        binding.titleScheduleType.setVisibility(View.GONE);


    }
    private void changeTypeSpiner(Integer position)
    {
        ArrayAdapter adapter;
        switch(position)
        {
            case 0:

                binding.titleWay.setVisibility(View.GONE);
                binding.waySpinner.setVisibility(View.GONE);
                binding.titleFrequency.setVisibility(View.VISIBLE);
                 adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.split_duration, R.layout.my_spinner);
                binding.frequencySpinner.setAdapter(adapter);
                binding.frequencySpinner.setVisibility(View.VISIBLE);
                binding.titleTime.setVisibility(View.GONE);
                binding.timeSpinner.setVisibility(View.GONE);
                binding.titleScheduleType.setVisibility(View.GONE);
                binding.scheduleTypeSpinner.setVisibility(View.GONE);
                binding.bodyPartsText.setVisibility(View.VISIBLE);
                model.setBodyPartsSplit(getContext());
                bodyPartsAdapter.notifyDataSetChanged();
                binding.myRecycleView.setVisibility(View.VISIBLE);
                break;

            case 1:

             binding.titleWay.setVisibility(View.GONE);
             binding.waySpinner.setVisibility(View.GONE);
             binding.titleFrequency.setVisibility(View.VISIBLE);
             adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.fbw_duration, R.layout.my_spinner);
             binding.frequencySpinner.setAdapter(adapter);
             binding.frequencySpinner.setVisibility(View.VISIBLE);
             binding.titleTime.setVisibility(View.GONE);
             binding.timeSpinner.setVisibility(View.GONE);
             binding.titleScheduleType.setVisibility(View.VISIBLE);
             binding.scheduleTypeSpinner.setVisibility(View.VISIBLE);
                binding.bodyPartsText.setVisibility(View.GONE);
                binding.myRecycleView.setVisibility(View.GONE);
              break;
            case 2:

                binding.titleWay.setVisibility(View.VISIBLE);
                binding.waySpinner.setVisibility(View.VISIBLE);
                binding.titleFrequency.setVisibility(View.GONE);
                binding.frequencySpinner.setVisibility(View.GONE);
                binding.titleTime.setVisibility(View.VISIBLE);
                binding.timeSpinner.setVisibility(View.VISIBLE);
                binding.titleScheduleType.setVisibility(View.GONE);
                binding.scheduleTypeSpinner.setVisibility(View.GONE);
                binding.bodyPartsText.setVisibility(View.GONE);
                binding.myRecycleView.setVisibility(View.GONE);
                break;
            case 3:

                binding.titleWay.setVisibility(View.VISIBLE);
                binding.waySpinner.setVisibility(View.VISIBLE);
                binding.titleFrequency.setVisibility(View.GONE);
                binding.frequencySpinner.setVisibility(View.GONE);
                binding.titleTime.setVisibility(View.VISIBLE);
                binding.timeSpinner.setVisibility(View.VISIBLE);
                binding.titleScheduleType.setVisibility(View.GONE);
                binding.scheduleTypeSpinner.setVisibility(View.GONE);
                binding.bodyPartsText.setVisibility(View.VISIBLE);
                model.setBodyPartsFitnes(getContext());
                bodyPartsAdapter.notifyDataSetChanged();
                binding.myRecycleView.setVisibility(View.VISIBLE);
                break;
        }
    }


}