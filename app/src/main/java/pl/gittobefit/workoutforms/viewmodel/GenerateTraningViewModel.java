package pl.gittobefit.workoutforms.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.workoutforms.adapters.EquipmentAdapter;
import pl.gittobefit.workoutforms.adapters.EquipmentList;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.repository.WorkoutFormsRepository;

public class GenerateTraningViewModel extends ViewModel
{
    private WorkoutFormsRepository repository =new WorkoutFormsRepository() ;
    private EquipmentList equipmentList=new EquipmentList();
    private ArrayList<EquipmentForm> listData = new ArrayList<EquipmentForm>();


    private int loadingIndex = -1;
    private int loadingEndIndex = -1;


    public void initList(ArrayList<EquipmentType> equipmentTypes , EquipmentAdapter.EquipmentListener equipmentListener)
    {
        this.listData = new ArrayList<EquipmentForm>(equipmentTypes);
        equipmentList.setData(listData);
        equipmentList.init(equipmentListener);
    }

    public void loadEqiupmentTypes(EquipmentFragment equipmentFragment)
    {
        repository.loadEquipmentTypes(equipmentFragment);
    }



    public ArrayList<EquipmentForm> getListData()
    {
        return listData;
    }


    public RecyclerView.Adapter getListAdapter()
    {
        return equipmentList.getAdapter();
    }

    public void tmp()
    {
        listData.add(0,new EquipmentForm(0,"llllll",""));
        equipmentList.tmp();
    }

    public void equipmentListClick(int position)
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
            loadingIndex = position;
            loadingEndIndex = position + 1;
            equipmentList.wczytaj(position,loadingIndex,loadingEndIndex);

        }
    }
}