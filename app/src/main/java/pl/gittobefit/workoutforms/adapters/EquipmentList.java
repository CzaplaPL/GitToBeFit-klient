package pl.gittobefit.workoutforms.adapters;

import java.util.ArrayList;

import pl.gittobefit.workoutforms.object.EquipmentItem;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentTypeItem;


public class EquipmentList
{
    private ArrayList<EquipmentForm> data = new ArrayList<>();
    private EquipmentAdapter adapter;
    private int loadingIndex = -1;
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
        data.add(position + 1, new EquipmentTypeItem(-1, "", ""));
        loadingIndex = position;
        loadingEndIndex = position + 1;
        adapter.notifyItemInserted(position + 1);
    }

    /**
     * funkcja obsługująca klik w kategore sprzętu
     * @param position pozycja w której wykonane było klikniecie
     * @return zwraca ewentualne zmiane pozycji klikanego obiektu
     */
    public int  clickInTypes(int position)
    {
        // sprawdzanie czy lista jest rozwinieta
        if(loadingIndex != -1)
        {
            if(loadingEndIndex - loadingIndex == 1)
            { //jezeli jest tylko rozwiniete wczytywanie
                data.remove(loadingIndex + 1);
                adapter.notifyItemRemoved(loadingIndex + 1);
                if(loadingIndex < position) position -= 1;
            }else
            {// jezeli rozwiniete są sprzęty
                if(loadingEndIndex >= loadingIndex + 1)
                {
                    data.subList(loadingIndex + 1, loadingEndIndex + 1).clear();
                }
                adapter.notifyDataSetChanged();
                if(loadingIndex < position) position -= loadingEndIndex - loadingIndex;
            }
        }
        loadingIndex=-1;
        loadingEndIndex=-1;
        return position;
    }

    /**
     * funkcja rozwijajaca wczytane sprzety
     * @param position  pozycja kategori do ktorej zostały wczytane sprzety
     * @param body Array lista zawierajaca wczytane sprzety
     */
    public void showEquipment(int position, ArrayList<EquipmentItem> body)
    {
        if(loadingIndex == position)
        {
            data.remove(loadingIndex + 1);
            adapter.notifyItemRemoved(loadingIndex + 1);
            loadingEndIndex = position;
            for(EquipmentItem equipment : body)
            {
                loadingEndIndex +=1;
                equipment.setEqiupment(true);
                data.add(loadingEndIndex,equipment);

            }
           adapter.notifyDataSetChanged();
        }
    }
}
