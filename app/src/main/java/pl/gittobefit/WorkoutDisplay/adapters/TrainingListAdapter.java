package pl.gittobefit.WorkoutDisplay.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.ViewHolder>
{

    private ArrayList<Training> trainingArrayList;
    private Fragment fragment;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView1;
        private TextView textView2;

        public ViewHolder (View view)
        {
            super(view);

            textView1 = (TextView) view.findViewById(R.id.list_of_trainings_training_type);
            textView2 = (TextView) view.findViewById(R.id.training_additional_info);
        }
    }

    public TrainingListAdapter(ArrayList<Training> trainingArrayList,Fragment fragment) {
        this.trainingArrayList = trainingArrayList;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public TrainingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_item, parent, false);

        return new TrainingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView1.setText(trainingArrayList.get(position).getTrainingForm().getTrainingType());

        holder.textView2.setText(trainingArrayList.get(position).getGenerationDate());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(fragment.requireActivity()).get(InitiationTrainingDisplayLayoutViewModel.class);
                model.setNumberOfClickedTraining(position);
                Navigation.findNavController(fragment.getView()).navigate(R.id.list_of_trainings_to_displayReceivedTraining);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trainingArrayList.size();
    }
}
