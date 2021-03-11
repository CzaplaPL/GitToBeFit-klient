package pl.gittobefit.workoutforms.fragments.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentSummaryFormBinding;
import pl.gittobefit.workoutforms.adapters.BodyPartsAdapter;
import pl.gittobefit.workoutforms.adapters.CheckedEquipmentAdapter;
import pl.gittobefit.workoutforms.adapters.ChosenBodyPartsAdapter;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

/**
 fragment tab3
 */
public class SummaryFragment extends Fragment {

    private FragmentSummaryFormBinding binding ;
    private GenerateTraningViewModel model;
    ChosenBodyPartsAdapter bodyPartsAdapter;
    CheckedEquipmentAdapter equipmentAdapter;
    public SummaryFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSummaryFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        model= new ViewModelProvider(requireActivity()).get(GenerateTraningViewModel.class);
        bodyPartsAdapter = new ChosenBodyPartsAdapter(model.getBodyPartsChecked());
        binding.bodyPartsList.setAdapter(bodyPartsAdapter);
        binding.bodyPartsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        equipmentAdapter = new CheckedEquipmentAdapter(model.getCheckedEqiupment());
        binding.eqiupmentsList.setAdapter(equipmentAdapter);
        binding.eqiupmentsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        model.getTypeSpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {

              changeTypeTranig(position);
            }
        });
        model.getFrequencySpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                String[] frequency;
                if( model.getTypeSpinnerChose().getValue()==0)
                {
                    frequency = getResources().getStringArray(R.array.split_duration);
                }else
                {
                    frequency = getResources().getStringArray(R.array.fbw_duration);
                }
                binding.frequency.setText(getString(R.string.chosen_frequency_prefix) + frequency[position] +  getString(R.string.chosen_frequency_sufix));
            }
        });
        model.getWaySpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                String[] way = getResources().getStringArray(R.array.training_subtype);
                binding.way.setText(getString(R.string.chosenWay) + way[position] );
            }
        });
        model.getTimeCardioSpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                String[] time = getResources().getStringArray(R.array.cardio_duration);
                binding.time.setText(getString(R.string.chosenTime) + time[position] );
            }
        });
        model.getTimeFitnesSpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                String[] time = getResources().getStringArray(R.array.fintess_duration);
                binding.time.setText(getString(R.string.chosenTime) + time[position] );
            }
        });
        model.getScheduleSpinnerChose().observe(getViewLifecycleOwner(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer position)
            {
                String[] schedule = getResources().getStringArray(R.array.fbw_sheduletype);
                binding.scheule.setText(getString(R.string.chosenSchedule) + schedule[position] );
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        model.updateCheckedBodyParts();
        bodyPartsAdapter.notifyDataSetChanged();
        if(model.isNoEquipmentcheched())
        {
            binding.noEquipmentItemImage.setVisibility(View.VISIBLE);
            binding.noEquipmentTitle.setVisibility(View.VISIBLE);
        }else
        {
            binding.noEquipmentItemImage.setVisibility(View.GONE);
            binding.noEquipmentTitle.setVisibility(View.GONE);
        }
        model.updateCheckedEqiupment();
        equipmentAdapter.notifyDataSetChanged();
    }


    private void changeTypeTranig(Integer position)
    {
        String[] Type = getResources().getStringArray(R.array.trening_type_name);
        binding.typeTraning.setText(getString(R.string.chosen_type) +Type[position]);
        switch(position)
        {
            case 0:
                binding.frequency.setVisibility(View.VISIBLE);
                binding.bodyParts.setVisibility(View.VISIBLE);
                binding.divider2.setVisibility(View.VISIBLE);
                binding.way.setVisibility(View.GONE);
                binding.time.setVisibility(View.GONE);
                binding.scheule.setVisibility(View.GONE);
                break;
            case 1:
                binding.frequency.setVisibility(View.VISIBLE);
                binding.bodyParts.setVisibility(View.GONE);
                binding.divider2.setVisibility(View.GONE);
                binding.way.setVisibility(View.GONE);
                binding.time.setVisibility(View.GONE);
                binding.scheule.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.frequency.setVisibility(View.GONE);
                binding.bodyParts.setVisibility(View.GONE);
                binding.divider2.setVisibility(View.GONE);
                binding.way.setVisibility(View.VISIBLE);
                binding.time.setVisibility(View.VISIBLE);
                binding.scheule.setVisibility(View.GONE);
                break;
            case 3:
                binding.frequency.setVisibility(View.GONE);
                binding.bodyParts.setVisibility(View.VISIBLE);
                binding.divider2.setVisibility(View.VISIBLE);
                binding.way.setVisibility(View.VISIBLE);
                binding.time.setVisibility(View.VISIBLE);
                binding.scheule.setVisibility(View.GONE);
                break;
        }
    }

}