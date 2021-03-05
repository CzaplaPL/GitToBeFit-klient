package pl.gittobefit.workoutforms.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentDetailFormBinding;
import pl.gittobefit.databinding.FragmentGenerateTrainingBinding;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.fragments.LoginDirections;
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

        // Metoda ustawiania tekstu formularza
        new TabLayoutMediator (binding.tabLayoutId,  binding.viewPagerId,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if(position == 0){
                            tab.setText("Equipment");
                        }
                        else if(position == 1){
                            tab.setText("Details");
                        }
                        else if(position == 2){
                            tab.setText ("Tab3");
                        }
                    }
                }).attach();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.tabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if(tab.getPosition()==2)
                {
                    binding.next.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ConnectionToServer.getInstance().WorkoutFormsServices.sendForm(model.getForm(getContext().getResources()));
                        }
                    });
                    binding.next.setText("generuj");
                }else
                {
                    binding.next.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                        }
                    });
                    binding.next.setText("next");
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
    }
}