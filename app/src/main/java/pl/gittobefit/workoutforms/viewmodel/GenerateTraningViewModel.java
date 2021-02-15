package pl.gittobefit.workoutforms.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.workoutforms.adapters.EquipmentAdapter;
import pl.gittobefit.workoutforms.adapters.EquipmentList;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.repository.WorkoutFormsRepository;

public class GenerateTraningViewModel extends ViewModel
{
    private WorkoutFormsRepository repository =new WorkoutFormsRepository() ;
    private EquipmentList equipmentList=new EquipmentList();
    private ArrayList<EquipmentForm> listData = new ArrayList<EquipmentForm>();




    public void initList(ArrayList<EquipmentType> equipmentTypes , EquipmentAdapter.EquipmentListener equipmentListener)
    {
        this.listData = new ArrayList<EquipmentForm>(equipmentTypes);
        repository.setEqiupmentTypes(equipmentTypes);
        equipmentList.setData(listData);
        equipmentList.init(equipmentListener);
    }

    public void loadEqiupmentTypes(EquipmentFragment equipmentFragment)
    {
        repository.loadEquipmentTypes(equipmentFragment);
    }
    public RecyclerView.Adapter getListAdapter()
    {
        return equipmentList.getAdapter();
    }

    public void equipmentListClick(int position)
    {
        if(position == equipmentList.getLoadingIndex())
        {
         //   Log.w("==", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loadingIndex) + " loadingend = " + String.valueOf(loadingEndIndex));
            equipmentList.clickInTypes(position);

        }else if(position > equipmentList.getLoadingIndex() && position <= equipmentList.getLoadingEndIndex())
        {
          //  Log.w(">", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loadingIndex) + " loadingend = " + String.valueOf(loadingEndIndex));
            if(listData.get(position).isIschecked())
            {
                listData.get(position).setIschecked(false);
                equipmentList.getAdapter().notifyItemChanged(position);
            }else
            {
                listData.get(position).setIschecked(true);
                equipmentList.getAdapter().notifyItemChanged(position);
            }
        }else
        {
          //  Log.w("!=", "position = " + String.valueOf(position) + " loading = " + String.valueOf(loadingIndex) + " loadingend = " + String.valueOf(loadingEndIndex));
            position = equipmentList.clickInTypes(position);

            equipmentList.addLoading(position);
            repository.loadEquipment(listData.get(position).getId(), position, this);

        }
    }

    public void loadEquipment(int position, ArrayList<Equipment> body, int typeid)
    {
        repository.addEquipment(typeid,body);
        equipmentList.showEquipment(position,body);

    }

    public void loadEquipment(int position, ArrayList<Equipment> body)
    {
        equipmentList.showEquipment(position,body);
    }
}