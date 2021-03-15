package pl.gittobefit.workoutforms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentGenerateTrainingBinding;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.adapters.WorkoutFormAdapter;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

public class GenerateTrainingForm extends Fragment
{
    private GenerateTraningViewModel model;
    private FragmentGenerateTrainingBinding binding;
    public GenerateTrainingForm() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicjalizacja zmiennych
        binding = FragmentGenerateTrainingBinding.inflate(inflater, container, false);
        WorkoutFormAdapter adapter = new WorkoutFormAdapter (this);
        binding.viewPagerId.setAdapter(adapter);
        model= new ViewModelProvider(requireActivity()).get(GenerateTraningViewModel.class);
        binding.next.setOnClickListener(v -> binding.viewPagerId.setCurrentItem(1));
        // Metoda ustawiania tekstu formularza
        new TabLayoutMediator (binding.tabLayoutId,  binding.viewPagerId,
                (tab, position) ->
                {
                    if(position == 0){
                        tab.setText(getString(R.string.eqiupment));
                    }
                    else if(position == 1){
                        tab.setText(getString(R.string.details));
                    }
                    else if(position == 2){
                        tab.setText (getString(R.string.sumary));
                    }
                }).attach();
        binding.tabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if(tab.getPosition()==2)
                {
                    binding.next.setOnClickListener(v -> ConnectionToServer.getInstance().WorkoutFormsServices.sendForm(model.getForm(getContext().getResources())));
                    binding.next.setText(getString(R.string.generate));
                }else
                {
                    binding.next.setOnClickListener(v -> binding.viewPagerId.setCurrentItem(tab.getPosition()+1));
                    binding.next.setText(getString(R.string.onlynext));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
        return binding.getRoot();

    }


}