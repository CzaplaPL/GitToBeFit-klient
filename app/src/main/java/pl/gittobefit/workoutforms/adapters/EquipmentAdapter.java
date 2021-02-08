package pl.gittobefit.workoutforms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {

private ArrayList<EquipmentForm> localDataSet;


public static class ViewHolder extends RecyclerView.ViewHolder
{
    private final TextView nameView;
    private final TextView idView;
    private final TextView idServerView;

    public ViewHolder(View view)
    {
        super(view);
        nameView = (TextView) view.findViewById(R.id.equipment_item_name);
        idServerView = (TextView) view.findViewById(R.id.equipment_item_id_server);
        idView = (TextView) view.findViewById(R.id.equipment_item_id);
    }

    public TextView getNameView()
    {
        return nameView;
    }
    public TextView getIdView() { return idView; }
    public TextView getIdServerViewView() {return idServerView;}
}


    public EquipmentAdapter(ArrayList<EquipmentForm> dataSet) {
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


        viewHolder.getNameView().setText(localDataSet.get(position).getName());
        //viewHolder.getIdServerViewView().setText(localDataSet.get(position).getId());
        //viewHolder.getIdView().setText(position);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}

