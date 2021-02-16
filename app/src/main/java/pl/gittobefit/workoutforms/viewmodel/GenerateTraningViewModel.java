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
    private WorkoutFormsRepository repository =new WorkoutFormsRepository(this) ;
    private EquipmentList equipmentList=new EquipmentList();
    private ArrayList<EquipmentForm> listData = new ArrayList<>();


    public RecyclerView.Adapter getListAdapter()
    {
        return equipmentList.getAdapter();
    }

    public void initList(ArrayList<EquipmentType> equipmentTypes , EquipmentAdapter.EquipmentListener equipmentListener)
    {
        this.listData = new ArrayList<>(equipmentTypes);
        repository.setEqiupmentTypes(equipmentTypes);
        equipmentList.setData(listData);
        equipmentList.init(equipmentListener);
    }

    public void loadEqiupmentTypes(EquipmentFragment equipmentFragment)
    {
        repository.loadEquipmentTypes(equipmentFragment);
    }

    /**
     * obsługiwanie klikniecia w liste
     * @param position pozycja w której wystąpiło wciśnięcie
     */
    public void equipmentListClick(int position)
    {
        if(position == equipmentList.getLoadingIndex())
        {//klikanie w kategorie która jest rozwinieta
            equipmentList.clickInTypes(position);

        }else if(position > equipmentList.getLoadingIndex() && position <= equipmentList.getLoadingEndIndex())
        {//klikanie w sprzet
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
        {//klikanie w kategorie nie rozwinieta
            position = equipmentList.clickInTypes(position);
            equipmentList.addLoading(position);
            repository.loadEquipment(listData.get(position).getId(), position);
        }
    }


    public void loadEquipment(int position, ArrayList<Equipment> body)
    {
        equipmentList.showEquipment(position,body);
    }
}