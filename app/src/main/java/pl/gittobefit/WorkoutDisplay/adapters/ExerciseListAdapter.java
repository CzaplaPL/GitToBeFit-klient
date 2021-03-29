package pl.gittobefit.WorkoutDisplay.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>
{

    private ArrayList<ExerciseExecution> exerciseArrayList;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView exerciseName;
        private TextView exerciseInfo;

        public ViewHolder (View view)
        {
            super(view);

            exerciseName = (TextView) view.findViewById(R.id.exercise_name);
            exerciseInfo = (TextView) view.findViewById(R.id.exercise_additional_info);
        }
    }

    public ExerciseListAdapter(ArrayList<ExerciseExecution> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_exercise_listview_item, parent, false);

        return new ExerciseListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.exerciseName.setText(exerciseArrayList.get(position).getExercise().getName());
        String text = "";
        if (exerciseArrayList.get(position).getTime() > 20)
        {
            text = exerciseArrayList.get(position).getSeries() + " serie, " + exerciseArrayList.get(position).getTime() + " sekund";
        }
        else
        {
            text = exerciseArrayList.get(position).getSeries() + " serie, " + exerciseArrayList.get(position).getCount() + " powtórzeń";
        }

        holder.exerciseInfo.setText(text);
    }


    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

}
