package pl.gittobefit.workoutforms.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentForm;

public class CheckedEquipmentAdapter extends RecyclerView.Adapter<CheckedEquipmentAdapter.ViewHolder> {

private ArrayList<Equipment> localDataSet;

    public CheckedEquipmentAdapter(ArrayList<Equipment> dataSet)
    {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.equipment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {
            viewHolder.getButton().setVisibility(View.GONE);
            viewHolder.getImage().setVisibility(View.VISIBLE);
            viewHolder.getNameView().setText(localDataSet.get(position).getName());
            viewHolder.getCheckBox().setVisibility(View.GONE);
            Glide.with(viewHolder.getContext())
                        .load("http://c4szkolenia.pl/"+localDataSet.get(position).getUrl())
                        .placeholder(R.drawable.ic_baseline_downloading_24)
                        .into(viewHolder.getImage());

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * View Holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView nameView;
        private final ImageView image;
        private final ImageView button;
        private final CheckBox checkBox;
        private final Context context;

        public ViewHolder(View view)
        {
            super(view);
            nameView = view.findViewById(R.id.no_equipment_title);
            image = view.findViewById(R.id.no_equipment_item_image) ;
            button = view.findViewById(R.id.equipment_drop_button) ;
            checkBox = view.findViewById(R.id.no_equipment_checbox) ;
            context = view.getContext();
        }

        public TextView getNameView()
        {
            return nameView;
        }
        public ImageView getImage() {return image;}
        public ImageView getButton() {return button;}
        public CheckBox getCheckBox(){ return checkBox; }
        public Context getContext() { return context; }




    }
}

