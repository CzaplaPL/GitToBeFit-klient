package pl.gittobefit.workout.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import pl.gittobefit.R;

public class GenerateTraining  extends Fragment
{
    private TabLayout tabLayout;


    ViewPagerAdapter demoCollectionAdapter;
    ViewPager2 viewPager;

    public GenerateTraining()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_generate_training, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutId);
        viewPager = view.findViewById(R.id.viewPagerId);
       // ViewPagerAdapter adapter = new ViewPagerAdapter();

        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        demoCollectionAdapter = new ViewPagerAdapter(this);
        viewPager = view.findViewById(R.id.viewPagerId);
        viewPager.setAdapter(demoCollectionAdapter);
    }
}
