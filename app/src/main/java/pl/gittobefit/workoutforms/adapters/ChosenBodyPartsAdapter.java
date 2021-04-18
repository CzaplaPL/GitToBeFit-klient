package pl.gittobefit.workoutforms.adapters;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.object.BodyParts;

public class ChosenBodyPartsAdapter extends RecyclerView.Adapter<ChosenBodyPartsAdapter.ViewHolder>
{
    private final ArrayList<BodyParts> bodyPartsArrayList;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;
        private final CheckBox checkBox;
        private final ImageView dot;
        public ViewHolder (View view)
        {
            super(view);

            textView = (TextView) view.findViewById(R.id.bodyPartsTextView);
            checkBox = (CheckBox) view.findViewById(R.id.bodyPartsCheckBox);
            dot = (ImageView) view.findViewById(R.id.dot);
        }
    }

    public ChosenBodyPartsAdapter(ArrayList<BodyParts> dataSet)
    {
        bodyPartsArrayList = dataSet;
    }

    @NonNull
    @Override
    public ChosenBodyPartsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.body_parts_item, parent, false);
        return new ChosenBodyPartsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenBodyPartsAdapter.ViewHolder holder, int position) {
        holder.checkBox.setVisibility(View.GONE);
        holder.dot.setVisibility(View.VISIBLE);
        holder.textView.setText(bodyPartsArrayList.get(position).getBodyName());
    }

    @Override
    public int getItemCount() {
        return bodyPartsArrayList.size();
    }
}
