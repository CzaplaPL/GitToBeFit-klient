package pl.gittobefit.workoutforms.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.databinding.EquipmentItemBinding;
import pl.gittobefit.workoutforms.object.EquipmentForm;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {

private ArrayList<EquipmentForm> localDataSet;
private EquipmentListener equipmentListener;

    public EquipmentAdapter(ArrayList<EquipmentForm> dataSet,EquipmentListener equipmentListener)
    {
        localDataSet = dataSet;
        this.equipmentListener= equipmentListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.equipment_item, viewGroup, false);
        return new ViewHolder(view,equipmentListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {

        if(localDataSet.get(position).getName().equals(""))
        {
            viewHolder.getNameView().setText("trwa wczytywanie...");
            viewHolder.getNameView().setGravity(Gravity.CENTER);
            viewHolder.getButton().setVisibility(View.GONE);
            viewHolder.getImage().setVisibility(View.GONE);
            viewHolder.getCheckBox().setVisibility(View.GONE);
        }else if(localDataSet.get(position).isEqiupment())
        {
            viewHolder.getButton().setVisibility(View.GONE);
            viewHolder.getImage().setVisibility(View.VISIBLE);
            viewHolder.getNameView().setText(localDataSet.get(position).getName());
            viewHolder.getCheckBox().setVisibility(View.VISIBLE);
            if(localDataSet.get(position).isIschecked())viewHolder.getCheckBox().setChecked(true);
            else viewHolder.getCheckBox().setChecked(false);
            Glide.with(viewHolder.getContext())
                        .load("http://c4szkolenia.pl/"+localDataSet.get(position).getUrl())
                        .placeholder(R.drawable.ic_baseline_downloading_24)
                        .into(viewHolder.getImage());
        }else
        {
            viewHolder.getButton().setVisibility(View.VISIBLE);
            viewHolder.getImage().setVisibility(View.VISIBLE);
            viewHolder.getNameView().setText(localDataSet.get(position).getName());
            Glide.with(viewHolder.getContext())
                    .load("http://c4szkolenia.pl/"+localDataSet.get(position).getUrl())
                    .placeholder(R.drawable.baseline_email_24)
                    .into(viewHolder.getImage());
            viewHolder.getCheckBox().setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * View Holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView nameView;
        private final ImageView image;
        private final ImageView button;
        private final CheckBox checkBox;
        private final EquipmentListener equipmentListener;
        private final Context context;

        public ViewHolder(View view,EquipmentListener equipmentListener)
        {
            super(view);

            this.equipmentListener=equipmentListener;
            nameView = view.findViewById(R.id.equipment_item_name);
            image = view.findViewById(R.id.equipment_item_image) ;
            button = view.findViewById(R.id.equipment_drop_button) ;
            checkBox =view.findViewById(R.id.equipment_checbox) ;
            button.setOnClickListener(this);
            checkBox.setOnClickListener(this);
            context = view.getContext();
            view.setOnClickListener(this);
        }

        public TextView getNameView()
        {
            return nameView;
        }
        public ImageView getImage() {return image;}
        public ImageView getButton() {return button;}
        public CheckBox getCheckBox(){ return checkBox; }
        public Context getContext() { return context; }


        @Override
        public void onClick(View v)
        {
            equipmentListener.onItemClick(getAdapterPosition());
        }


    }
    public interface EquipmentListener
    {
        void onItemClick(int position);
    }
}

