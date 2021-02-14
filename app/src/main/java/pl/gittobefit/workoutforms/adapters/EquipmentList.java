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

    public void addLoading(int position, int loadingIndex, int loadingEndIndex)
    {
        data.add(position + 1, new EquipmentType(-1, "", ""));
        adapter.notifyItemInserted(position + 1);
    }

    public int  clickInTypes(int loadingIndex, int loadingEndIndex, int position)
    {
        if(loadingIndex != -1)
        {
            if(loadingEndIndex - loadingIndex == 1)
            {
                Log.w("1!=", "position 1" );
                data.remove(loadingIndex + 1);
                adapter.notifyItemRemoved(loadingIndex + 1);
                if(loadingIndex < position) position -= 1;
            }else
            {
                Log.w("1!=", "position many" );
                for(int i=loadingIndex+1;i<=loadingEndIndex;i++)
                {
                    data.remove(loadingIndex+1);
                }
                Log.w("rozmiar",String.valueOf(data.size()));
                adapter.notifyDataSetChanged();
                if(loadingIndex < position) position -= loadingEndIndex - loadingIndex;
            }
        }
        return position;
    }
}
