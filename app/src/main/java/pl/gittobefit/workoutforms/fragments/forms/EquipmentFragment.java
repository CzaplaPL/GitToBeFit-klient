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

import java.util.ArrayList;

import pl.gittobefit.databinding.FragmentEquipmentBinding;
import pl.gittobefit.workoutforms.adapters.EquipmentAdapter;
import pl.gittobefit.workoutforms.object.EquipmentTypeItem;
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
        model.getEquiomentIsChecked().observe(getViewLifecycleOwner(), new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if(!aBoolean)
                {
                    model.setNoEquipmentcheched(true);
                    binding.noEquipmentChecbox.setChecked(true);
                }
                binding.noEquipmentChecbox.setEnabled(aBoolean);
            }
        });
        binding.bezSprzetu.setOnClickListener(v ->
        {
            if(!binding.noEquipmentChecbox.isChecked()&& binding.noEquipmentChecbox.isEnabled())
            {
                binding.noEquipmentChecbox.setChecked(true);
                model.setNoEquipmentcheched(true);
            }else
            {
                binding.noEquipmentChecbox.setChecked(false);
                model.setNoEquipmentcheched(false);

            }
        });
        binding.noEquipmentChecbox.setOnClickListener(v -> model.setNoEquipmentcheched(binding.noEquipmentChecbox.isChecked()));
    }

    public void createList(ArrayList<EquipmentTypeItem> equipmentType, int noEquipmentId)
    {
        model.initList(equipmentType,this);
        model.setNoEquipmentid(noEquipmentId);
        model.setNoEquipmentcheched(true);
        binding.titleEquipment.setVisibility(View.VISIBLE);
        binding.bezSprzetu.setVisibility(View.VISIBLE);
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
    }



}