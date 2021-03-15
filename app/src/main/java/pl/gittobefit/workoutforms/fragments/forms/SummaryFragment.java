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
import pl.gittobefit.workoutforms.adapters.ChosenBodyPartsAdapter;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

/**
 fragment tab3
 */
public class SummaryFragment extends Fragment {

    private FragmentSummaryFormBinding binding ;
    private GenerateTraningViewModel model;
    ChosenBodyPartsAdapter bodyPartsAdapter;
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
                String[] frequency = getResources().getStringArray(R.array.split_duration);
                binding.frequency.setText(getString(R.string.chosen_frequency_prefix) + frequency[position] +  getString(R.string.chosen_frequency_sufix));
            }
        });

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
                break;
            case 1:
                binding.frequency.setVisibility(View.VISIBLE);
                binding.bodyParts.setVisibility(View.GONE);
                break;
            case 2:
                binding.frequency.setVisibility(View.GONE);
                binding.bodyParts.setVisibility(View.GONE);
                break;
            case 3:
                binding.frequency.setVisibility(View.GONE);
                binding.bodyParts.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        model.updateCheckedBodyParts();
        bodyPartsAdapter.notifyDataSetChanged();
    }
}