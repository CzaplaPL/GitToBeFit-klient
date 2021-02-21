package pl.gittobefit.workoutforms.fragments.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentEquipmentBinding;
import pl.gittobefit.databinding.FragmentSummaryFragmentBinding;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

/**
 fragment tab3
 */
public class SummaryFragment extends Fragment {

    private FragmentSummaryFragmentBinding binding ;
    private GenerateTraningViewModel model;
    public SummaryFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSummaryFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        model= new ViewModelProvider(requireActivity()).get(GenerateTraningViewModel.class);

    }
}