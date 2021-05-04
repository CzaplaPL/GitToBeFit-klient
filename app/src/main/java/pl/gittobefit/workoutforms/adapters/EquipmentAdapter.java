package pl.gittobefit.workoutforms.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.object.EquipmentForm;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder>
{

    private final ArrayList<EquipmentForm> localDataSet;
    private final EquipmentListener equipmentListener;

    public EquipmentAdapter(ArrayList<EquipmentForm> dataSet, EquipmentListener equipmentListener)
    {
        localDataSet = dataSet;
        this.equipmentListener = equipmentListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.equipment_item, viewGroup, false);
        return new ViewHolder(view, equipmentListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {
        Context context = viewHolder.getContext();
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
            RelativeLayout.LayoutParams margin = new RelativeLayout.LayoutParams(viewHolder.getContainer().getLayoutParams());
            margin.setMargins(30, 0, 0, 0);
            viewHolder.getContainer().setLayoutParams(margin);
            viewHolder.getNameView().setText(localDataSet.get(position).getName());
            viewHolder.getCheckBox().setVisibility(View.VISIBLE);
            viewHolder.getCheckBox().setChecked(localDataSet.get(position).isIschecked());
            if(localDataSet.get(position).isOffline())
            {
                Glide.with(context)
                        .load(context.getResources().getIdentifier(
                                localDataSet.get(position).getUrl(),
                                "drawable",
                                context.getPackageName()))
                        .placeholder(R.drawable.ic_baseline_downloading_24)
                        .into(viewHolder.getImage());
            }else
            {
                Glide.with(viewHolder.getContext())
                        .load(ConnectionToServer.PREFIX_PHOTO_URL + localDataSet.get(position).getUrl())
                        .placeholder(R.drawable.ic_baseline_downloading_24)
                        .into(viewHolder.getImage());
            }

        }else
        {
            RelativeLayout.LayoutParams margin = new RelativeLayout.LayoutParams(viewHolder.getContainer().getLayoutParams());
            margin.setMargins(0, 0, 0, 0);
            viewHolder.getContainer().setLayoutParams(margin);
            viewHolder.getButton().setVisibility(View.VISIBLE);
            viewHolder.getImage().setVisibility(View.VISIBLE);
            viewHolder.getNameView().setText(localDataSet.get(position).getName());
            if(localDataSet.get(position).isOffline())
            {
                Glide.with(context)
                        .load(context.getResources().getIdentifier(
                                localDataSet.get(position).getUrl(),
                                "drawable",
                                context.getPackageName()))
                        .placeholder(R.drawable.ic_baseline_downloading_24)
                        .into(viewHolder.getImage());
            }else
            {
                Glide.with(viewHolder.getContext())
                        .load(ConnectionToServer.PREFIX_PHOTO_URL + localDataSet.get(position).getUrl())
                        .placeholder(R.drawable.ic_baseline_downloading_24)
                        .into(viewHolder.getImage());

            }
            viewHolder.getCheckBox().setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView nameView;
        private final ImageView image;
        private final ImageView button;
        private final CheckBox checkBox;
        private final EquipmentListener equipmentListener;
        private final Context context;
        private final ConstraintLayout container;

        public ViewHolder(View view, EquipmentListener equipmentListener)
        {
            super(view);
            this.equipmentListener = equipmentListener;
            nameView = view.findViewById(R.id.no_equipment_title);
            image = view.findViewById(R.id.no_equipment_item_image);
            button = view.findViewById(R.id.bodyButton);
            checkBox = view.findViewById(R.id.no_equipment_checbox);
            button.setOnClickListener(this);
            checkBox.setOnClickListener(this);
            context = view.getContext();
            container = view.findViewById(R.id.equipment_item_container);
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

        public ConstraintLayout getContainer()
        {
            return container;
        }
    }

    public interface EquipmentListener
    {
        void onItemClick(int position);
    }
}

