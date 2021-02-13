package pl.gittobefit.workoutforms.fragments.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import pl.gittobefit.databinding.FragmentEquipmentBinding;
import pl.gittobefit.workoutforms.adapters.EquipmentAdapter;
import pl.gittobefit.workoutforms.adapters.EquipmentList;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

/**
 fragment wyposażenia
 */
public class EquipmentFragment extends Fragment implements EquipmentAdapter.EquipmentListener
{
    private FragmentEquipmentBinding binding;

    private GenerateTraningViewModel model;
    public EquipmentFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentEquipmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        model= new ViewModelProvider(requireActivity()).get(GenerateTraningViewModel.class);
        model.loadEqiupmentTypes(this);
    }
    public void createList(ArrayList<EquipmentType> equipmentType)
    {
        model.initList(equipmentType,this);
        binding.rvContacts.setAdapter(model.getListAdapter());
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.equipmentLoading.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onItemClick(int position)
    {
        model.equipmentListClick(position);
       // model.tmp();
    }


}