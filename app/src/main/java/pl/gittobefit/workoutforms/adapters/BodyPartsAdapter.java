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
    private ArrayList<BodyParts> bodyPartsArrayList;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private CheckBox checkBox;

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
        holder.textView.setText(bodyPartsArrayList.get(position).getBodyPart());
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();

                if (bodyPartsArrayList.get(pos).isSelected()) {
                    bodyPartsArrayList.get(pos).setSelected(false);
                } else {
                    bodyPartsArrayList.get(pos).setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bodyPartsArrayList.size();
    }
}
