package pl.gittobefit.workoutforms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.adapters.WorkoutFormAdapter;

public class GenerateTrainingForm extends Fragment {
    public GenerateTrainingForm() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicjalizacja zmiennych
        View view = inflater.inflate ( R.layout.fragment_generate_training, container, false );;
        TabLayout tabLayout = view.findViewById (R.id.tabLayoutId);;
        ViewPager2 viewPager2 = view.findViewById ( R.id.viewPagerId);;
        WorkoutFormAdapter adapter = new WorkoutFormAdapter (this);
        viewPager2.setAdapter(adapter);
        // Metoda ustawiania tekstu formularza
        new TabLayoutMediator (tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if(position == 0){
                            tab.setText("Equipment");
                        }
                        else if(position == 1){
                            tab.setText("Tab2");
                        }
                        else if(position == 2){
                            tab.setText ("Tab3");
                        }
                    }
                }).attach();
        return view;
    }
}