package pl.gittobefit.workout.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import pl.gittobefit.R;

public class GenerateTraining extends Fragment
{
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        return view;
    }
}