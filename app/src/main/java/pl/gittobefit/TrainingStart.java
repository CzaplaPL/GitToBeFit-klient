package pl.gittobefit;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;

public class TrainingStart extends Fragment {

    public TrainingStart() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training_start, container, false);
        LinearLayout exerciseStart = view.findViewById(R.id.exercise_start);
        LinearLayout exerciseBackground = view.findViewById(R.id.exercise_background);
        Button miss = view.findViewById(R.id.miss);
        Button start = view.findViewById(R.id.start);
        exerciseBackground.setAlpha(0);
        exerciseStart.setAlpha(0);
        start.setOnClickListener(v -> {
            exerciseStart.setAlpha(1);
            exerciseBackground.setAlpha(1);
        });
        return view;
    }
}