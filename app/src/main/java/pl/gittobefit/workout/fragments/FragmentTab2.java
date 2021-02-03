package pl.gittobefit.workout.fragments;

import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import pl.gittobefit.R;

public class FragmentTab2 extends Fragment {
    View view;
    public FragmentTab2(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab2,container,false);
        return view;
    }
}