package pl.gittobefit.workoutforms.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.adapters.EquipmentAdapter;
import pl.gittobefit.workoutforms.adapters.EquipmentList;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.BodyParts;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.repository.WorkoutFormsRepository;

public class GenerateTraningViewModel extends ViewModel
{
    private WorkoutFormsRepository repository =new WorkoutFormsRepository(this) ;
    private EquipmentList equipmentList=new EquipmentList();
    private int noEquipmentid = -1;
    private boolean noEquipmentcheched = true;
    private ArrayList<EquipmentForm> listData = new ArrayList<>();
    private MutableLiveData<String> typeDesciptionText =new  MutableLiveData<>();
    private MutableLiveData<String> detailDesciptionText =new  MutableLiveData<>();
    private MutableLiveData<String> timeDesciptionText =new  MutableLiveData<>();
    private  ArrayList<BodyParts> bodyPartsToChoose = new ArrayList<>();
    private  ArrayList<BodyParts> bodyPartsChecked = new ArrayList<>();
    private MutableLiveData<Integer> typeSpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> waySpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> frequencySpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> timeSpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> scheduleSpinnerChose =new  MutableLiveData<>();


    public GenerateTraningViewModel()
    {
        setTypeSpinnerChose(0);
        setWaySpinnerChose(0);
        setFrequencySpinnerChose(0);
        setTimeSpinnerChose(0);
    }

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


    public MutableLiveData<String> getTypeDesciptionText()
    {
        return typeDesciptionText;
    }

    public void setTypeDesciptionText(String typeDesciptionText)
    {
        this.typeDesciptionText.setValue(typeDesciptionText);
    }

    public MutableLiveData<String> getDetailDesciptionText()
    {
        return detailDesciptionText;
    }

    public void setDetailDesciptionText(String detailDesciptionText)
    {
        this.detailDesciptionText.setValue(detailDesciptionText);
    }

    public MutableLiveData<String> getTimeDesciptionText()
    {
        return timeDesciptionText;
    }

    public void setTimeDesciptionText(String timeDesciptionText)
    {
        this.timeDesciptionText.setValue(timeDesciptionText);
    }

    public void setBodyPartsSplit(Context context)
    {
        bodyPartsToChoose.clear();
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.chest)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.sixpack)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.back)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.thighs)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.calfs)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.biceps)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.triceps)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.shoulders )));
    }
    public void setBodyPartsFitnes(Context context)
    {
        bodyPartsToChoose.clear();
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.chest)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.sixpack)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.back)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.legs)));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.arms)));

    }
    public ArrayList<BodyParts> getBodyParts()
    {
        return bodyPartsToChoose;
    }

    public MutableLiveData<Integer> getTypeSpinnerChose()
    {
        return typeSpinnerChose;
    }

    public void setTypeSpinnerChose(Integer typeSpinnerChose)
    {
        this.typeSpinnerChose.setValue(typeSpinnerChose);
    }

    public MutableLiveData<Integer> getWaySpinnerChose()
    {
        return waySpinnerChose;
    }

    public void setWaySpinnerChose(int position)
    {
        this.waySpinnerChose.setValue(position);
    }

    public MutableLiveData<Integer> getFrequencySpinnerChose()
    {
        return frequencySpinnerChose;
    }

    public void setFrequencySpinnerChose(int position)
    {
        frequencySpinnerChose.setValue(position);
    }

    public MutableLiveData<Integer> getTimeSpinnerChose()
    {
        return timeSpinnerChose;
    }

    public void setTimeSpinnerChose(int position)
    {
        timeSpinnerChose.setValue(position);
    }

    public void setScheduleSpinnerChose(int position)
    {
scheduleSpinnerChose.setValue(position);
    }

    public void updateCheckedBodyParts()
    {
        bodyPartsChecked.clear();
        for(int i=0; i< bodyPartsToChoose.size();++i)
        {
            if(bodyPartsToChoose.get(i).isSelected())
            {
                bodyPartsChecked.add(bodyPartsToChoose.get(i));
            }
        }
    }
    public ArrayList<BodyParts> getBodyPartsChecked()
    {
        return bodyPartsChecked;
    }

    public int getNoEquipmentid()
    {
        return noEquipmentid;
    }

    public void setNoEquipmentid(int noEquipmentid)
    {
        this.noEquipmentid = noEquipmentid;
    }

    public boolean isNoEquipmentcheched()
    {
        return noEquipmentcheched;
    }

    public void setNoEquipmentcheched(boolean noEquipmentcheched)
    {
        this.noEquipmentcheched = noEquipmentcheched;
    }
}