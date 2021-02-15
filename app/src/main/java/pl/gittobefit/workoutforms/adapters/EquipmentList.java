package pl.gittobefit.workoutforms.adapters;

import android.util.Log;

import java.util.ArrayList;

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;


public class EquipmentList
{
    private ArrayList<EquipmentForm> data = new ArrayList<>();
    private EquipmentAdapter adapter;



    private int loadingIndex = -1;


    public void setLoadingEndIndex(int loadingEndIndex)
    {
        this.loadingEndIndex = loadingEndIndex;
    }

    private int loadingEndIndex = -1;


    public void setData(ArrayList<EquipmentForm> data)
    {
        this.data = data;
        loadingIndex = -1;
        loadingEndIndex = -1;
    }

    public void init(EquipmentAdapter.EquipmentListener equipmentListener)
    {
        adapter = new EquipmentAdapter(data, equipmentListener);
    }

    public int getLoadingIndex()
    {
        return loadingIndex;
    }

    public int getLoadingEndIndex()
    {
        return loadingEndIndex;
    }

    public EquipmentAdapter getAdapter()
    {
        return adapter;
    }

    /**
     * funkcja dodaje Item wczytywania
     * @param position nr pozycji po jakiej ma zostac dodany item
     */
    public void addLoading(int position)
    {
        data.add(position + 1, new EquipmentType(-1, "", ""));
        loadingIndex = position;
        loadingEndIndex = position + 1;
        adapter.notifyItemInserted(position + 1);
    }

    /**
     * funkcja obsługująca klik w kategore sprzętu
     * @param position pozycja w której wykonane było klikniecie
     * @return
     */
    public int  clickInTypes(int position)
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
        loadingIndex=-1;
        loadingEndIndex=-1;
        return position;
    }

    public void showEquipment(int position, ArrayList<Equipment> body)
    {
        if(loadingIndex == position)
        {
            data.remove(loadingIndex + 1);
            adapter.notifyItemRemoved(loadingIndex + 1);
            loadingEndIndex = position;
            for(Equipment equipment : body)
            {
                loadingEndIndex +=1;
                equipment.setEqiupment(true);
                data.add(loadingEndIndex,equipment);

            }
           adapter.notifyDataSetChanged();
        }
    }
}
