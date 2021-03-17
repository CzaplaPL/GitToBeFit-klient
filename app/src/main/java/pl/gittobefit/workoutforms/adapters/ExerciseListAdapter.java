package pl.gittobefit.workoutforms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.object.ExerciseExecution;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>
{

    private ArrayList<ExerciseExecution> exerciseArrayList;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView1;
        private TextView textView2;

        public ViewHolder (View view)
        {
            super(view);

            textView1 = (TextView) view.findViewById(R.id.exercise_name);
            textView2 = (TextView) view.findViewById(R.id.exercise_additional_info);
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
        holder.textView1.setText(exerciseArrayList.get(position).getExercise().getName());
        String text = "";
        if (exerciseArrayList.get(position).getTime() > 20)
        {
            text = exerciseArrayList.get(position).getSeries() + " serie, " + exerciseArrayList.get(position).getTime() + " sekund";
        }
        else
        {
            text = exerciseArrayList.get(position).getSeries() + " serie, " + exerciseArrayList.get(position).getCount() + " powtórzeń";
        }

        holder.textView2.setText(text);
    }


    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

}
