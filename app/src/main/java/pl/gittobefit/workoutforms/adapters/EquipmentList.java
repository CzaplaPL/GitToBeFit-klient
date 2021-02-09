package pl.gittobefit.workoutforms.adapters;

import android.util.Log;

import java.util.ArrayList;

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;


public class EquipmentList
{
    private ArrayList<EquipmentForm> data = new ArrayList<EquipmentForm>();
    private ArrayList<Integer> loadType = new ArrayList<Integer>();
    private EquipmentAdapter adapter;
    private int loading = -1;
    private int loadingEnd = -1;

    public void setData(ArrayList<EquipmentForm> data)
    {
        this.data = data;

    }

    public void init(EquipmentAdapter.EquipmentListener equipmentListener)
    {
        adapter = new EquipmentAdapter(data, equipmentListener);
    }

    public EquipmentAdapter getAdapter()
    {
        return adapter;
    }

    public void click(int position)
    {
        if(position == loading)
        {
            Log.w("==", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loading) + " loadingend = " + String.valueOf(loadingEnd));
            //chowanie
        }else if(position > loading && position <= loadingEnd)
        {
            Log.w(">", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loading) + " loadingend = " + String.valueOf(loadingEnd));
            //klikanie w sprzed
        }else
        {
            Log.w("!=", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loading) + " loadingend = " + String.valueOf(loadingEnd));
            if(loading != -1)
            {
                if(loadingEnd - loading == 1)
                {
                    data.remove(loading + 1);
                    adapter.notifyItemRemoved(loading + 1);
                    if(loading < position) position -= 1;
                }else
                {
                    ///
                }
            }
            loading = position;
            loadingEnd = position + 1;
            data.add(position + 1, new EquipmentType(-1, "", ""));
            adapter.notifyItemInserted(position + 1);

            ConnectionToServer.getInstance().WorkoutFormsServices.getEquipment(data.get(position).getId(), position, this);
        }
    }

    public void loadEquipment(int position, ArrayList<Equipment> response)
    {
        if(loading == position)
        {
            data.remove(loading + 1);
            adapter.notifyItemRemoved(loading + 1);
            loadingEnd = position;
            for(Equipment equipment : response)
            {
                Log.w("odp",equipment.toString());
                loadingEnd+=1;
                equipment.setEqiupment(true);
                data.add(loadingEnd,equipment);

            }

             adapter.notifyDataSetChanged();
        }
    }
}
