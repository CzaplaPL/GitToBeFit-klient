package pl.gittobefit.workoutforms.fragments.forms;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentDetailFormBinding;
import pl.gittobefit.workoutforms.adapters.BodyPartsAdapter;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;


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
        binding.detailsInfoPicture.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bright));
        binding.detailsInfoPicture.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_generateTrainingForm_to_detailsInfoLayout));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model= new ViewModelProvider(requireActivity()).get(GenerateTraningViewModel.class);
        model.setBodyPartsSplit(getContext());
        //tworzenie 1 spinera wyboru typu treningu
        bodyPartsAdapter = new BodyPartsAdapter(model.getBodyParts());
        binding.myRecycleView.setAdapter(bodyPartsAdapter);
        //binding.myRecycleView.setNestedScrollingEnabled(false);
        binding.myRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.trening_type, R.layout.my_spinner);
        binding.typeSpinner.setAdapter(adapter1);
        model.getTypeSpinnerChose().observe(getViewLifecycleOwner(), this::changeTypeSpiner);
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
        //tworzenie 2 spinera wyboru serie czy obwodowo
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
        //tworzenie 3 spinera na ilosc dni w splicie lub fbw
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



        //tworzenie 4.1 spinera czasu dla cardio
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getContext(),
                R.array.cardio_duration, R.layout.my_spinner);
        binding.timeSpinner.setAdapter(adapter4);
        binding.timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setTimeCardioSpinnerChose(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    binding.timeSpinner.setVisibility(View.GONE);

        //tworzenie 4.2 spinera czasu dla cardio
        ArrayAdapter adapter4Fitnes = ArrayAdapter.createFromResource(getContext(),
                R.array.fintess_duration, R.layout.my_spinner);
        binding.timeSpinnerFitnnes.setAdapter(adapter4Fitnes);
        binding.timeSpinnerFitnnes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setTimeFitnesSpinnerChose(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        binding.timeSpinnerFitnnes.setVisibility(View.GONE);
    binding.titleTime.setVisibility(View.GONE);
        //tworzenie 5 spinera
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(getContext(),
                R.array.fbw_sheduletype, R.layout.my_spinner);
        binding.scheduleTypeSpinner.setAdapter(adapter5);
        binding.scheduleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                model.setScheduleSpinnerChosen(position);
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
                binding.timeSpinnerFitnnes.setVisibility(View.GONE);
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
             binding.timeSpinnerFitnnes.setVisibility(View.GONE);
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
                binding.timeSpinnerFitnnes.setVisibility(View.GONE);
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
                binding.timeSpinner.setVisibility(View.GONE);
                binding.timeSpinnerFitnnes.setVisibility(View.VISIBLE);
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