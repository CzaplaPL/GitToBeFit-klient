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
    private EquipmentAdapter adapter;
    private int loadingIndex = -1;
    private int loadingEndIndex = -1;

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

    public void click(int position )
    {
        if(position == loadingIndex)
        {
            Log.w("==", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loadingIndex) + " loadingend = " + String.valueOf(loadingEndIndex));
            //chowanie
        }else if(position > loadingIndex && position <= loadingEndIndex)
        {
            Log.w(">", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loadingIndex) + " loadingend = " + String.valueOf(loadingEndIndex));
            //klikanie w sprzed
        }else
        {
            Log.w("!=", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loadingIndex) + " loadingend = " + String.valueOf(loadingEndIndex));
            if(loadingIndex != -1)
            {
           /*     if(loadingEndIndex - loadingIndex == 1)
                {
                    Log.w("!=", "position 1" );
                    data.remove(loadingIndex + 1);
                    adapter.notifyItemRemoved(loadingIndex + 1);
                    if(loadingIndex < position) position -= 1;
                }else
                {
                    Log.w("!=", "position many" );
                   for(int i=loadingIndex+1;i<=loadingEndIndex;i++)
                   {
                       data.remove(loadingIndex+1);
                   }
                   Log.w("rozmiar",String.valueOf(data.size()));
                    adapter.notifyItemRangeRemoved(loadingIndex,loadingEndIndex);
                    if(loadingIndex < position) position -= loadingEndIndex - loadingIndex;
                }*/
            }
            loadingIndex = position;
            loadingEndIndex = position + 1;
            data.add(position + 1, new EquipmentType(-1, "", ""));
            adapter.notifyItemInserted(position + 1);

//            ConnectionToServer.getInstance().WorkoutFormsServices.getEquipment(data.get(position).getId(), position, this);
        }
    }

    public void loadEquipment(int position, ArrayList<Equipment> response)
    {
        if(loadingIndex == position)
        {
            data.remove(loadingIndex + 1);
            adapter.notifyItemRemoved(loadingIndex + 1);
            loadingEndIndex = position;
            for(Equipment equipment : response)
            {
                loadingEndIndex +=1;
                equipment.setEqiupment(true);
                data.add(loadingEndIndex,equipment);

            }
             adapter.notifyDataSetChanged();
        }
    }

    public void tmp()
    {
        adapter.notifyDataSetChanged();
    }

    public void wczytaj(int position, int loadingIndex, int loadingEndIndex)
    {
        data.add(position + 1, new EquipmentType(-1, "", ""));
        adapter.notifyItemInserted(position + 1);
    }
}
