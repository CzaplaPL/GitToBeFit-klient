package pl.gittobefit.workoutforms.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.object.BodyParts;

public class BodyPartsAdapter extends RecyclerView.Adapter<BodyPartsAdapter.ViewHolder>
{
    private final ArrayList<BodyParts> bodyPartsArrayList;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;
        private final CheckBox checkBox;

        public ViewHolder (View view)
        {
            super(view);

            textView = (TextView) view.findViewById(R.id.bodyPartsTextView);
            checkBox = (CheckBox) view.findViewById(R.id.bodyPartsCheckBox);
        }
    }

    public BodyPartsAdapter(ArrayList<BodyParts> dataSet)
    {
        bodyPartsArrayList = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_spinner2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.checkBox.setChecked(bodyPartsArrayList.get(position).isSelected());
        holder.textView.setText(bodyPartsArrayList.get(position).getBodyName());
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(v ->
        {
            Integer pos = (Integer) holder.checkBox.getTag();
            bodyPartsArrayList.get(pos).setSelected(!bodyPartsArrayList.get(pos).isSelected());
        });
    }

    @Override
    public int getItemCount() {
        return bodyPartsArrayList.size();
    }
}
