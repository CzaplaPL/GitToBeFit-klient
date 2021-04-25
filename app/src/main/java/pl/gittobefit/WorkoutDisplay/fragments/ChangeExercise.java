package pl.gittobefit.WorkoutDisplay.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.gittobefit.R;

public class ChangeExercise extends Fragment {

    public ChangeExercise() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null)
        {
            activity.getSupportActionBar().hide();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_exercise, container, false);
    }


}