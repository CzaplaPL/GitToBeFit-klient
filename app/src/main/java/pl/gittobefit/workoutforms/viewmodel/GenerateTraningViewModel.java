package pl.gittobefit.workoutforms.viewmodel;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.database.entity.training.WorkoutForm;
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
    private ArrayList<Equipment> checkedEqiupment = new ArrayList<>();
    private  ArrayList<BodyParts> bodyPartsToChoose = new ArrayList<>();
    private  ArrayList<BodyParts> bodyPartsChecked = new ArrayList<>();
    private MutableLiveData<Integer> typeSpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> waySpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> frequencySpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> timeCardioSpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> timeFitnesSpinnerChose =new  MutableLiveData<>();
    private MutableLiveData<Integer> scheduleSpinnerChose =new  MutableLiveData<>();
   public MutableLiveData<Integer> getScheduleSpinnerChose()
    {
        return scheduleSpinnerChose;
    }

    public GenerateTraningViewModel()
    {
        setTypeSpinnerChose(0);
        setWaySpinnerChose(0);
        setFrequencySpinnerChose(0);
        setTimeCardioSpinnerChose(0);
        setTimeFitnesSpinnerChose(0);
        setScheduleSpinnerChose(0);
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




    public void setBodyPartsSplit(Context context)
    {
        bodyPartsToChoose.clear();
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.chest),"CHEST"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.sixpack),"SIXPACK"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.back),"BACK"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.thighs),"THIGHS"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.calfs),"CALVES"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.biceps),"BICEPS"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.triceps),"TRICEPS"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.shoulders ),"SHOULDERS"));
    }
    public void setBodyPartsFitnes(Context context)
    {
        bodyPartsToChoose.clear();
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.chest),"CHEST"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.sixpack),"SIXPACK"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.back),"BACK"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.arms),"ARMS"));
        bodyPartsToChoose.add(new BodyParts(context.getString(R.string.legs),"LEGS"));

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

    public MutableLiveData<Integer> getTimeCardioSpinnerChose()
    {
        return timeCardioSpinnerChose;
    }

    public void setTimeCardioSpinnerChose(int position)
    {
        timeCardioSpinnerChose.setValue(position);
    }

    public MutableLiveData<Integer> getTimeFitnesSpinnerChose()
    {
        return timeFitnesSpinnerChose;
    }

    public void setTimeFitnesSpinnerChose(int position)
    {
        timeFitnesSpinnerChose.setValue(position);
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
    public ArrayList<Integer> getIdCheckedEqiupment()
    {
        ArrayList<Integer> checked = repository.getIdCheckEqiupment();
        if(isNoEquipmentcheched())
        {
            checked.add(getNoEquipmentid());
        }
        if(checked.size()<1)
            checked.add(getNoEquipmentid());
        return  checked;
    }
    public ArrayList<Equipment> getCheckedEqiupment()
    {
        return  checkedEqiupment;
    }

    public void updateCheckedEqiupment()
    {
        ArrayList<Equipment> equipmentInRepo = repository.getCheckEqiupment();
        checkedEqiupment.clear();
        checkedEqiupment.addAll(equipmentInRepo);
    }
    public WorkoutForm getForm(Resources resources)
    {
        String[] Type = resources.getStringArray(R.array.trening_type_name);
        String[] daysCount ;
        String[] scheduleType= new String[] {"REPETITIVE","PER_DAY"};
        String[] subtype= new String[] {"SERIES","CIRCUIT"};
        int[] duration;
        switch(getTypeSpinnerChose().getValue())
        {
            case 0:
                 daysCount = resources.getStringArray(R.array.split_duration);
                return new WorkoutForm(
                        getIdCheckedEqiupment(),
                        Type[getTypeSpinnerChose().getValue()],
                        getBodyPartsIdChecked(),
                        Integer.parseInt(daysCount[getFrequencySpinnerChose().getValue()]),
                        scheduleType[getScheduleSpinnerChose().getValue()],
                        0);

                case 1:
                daysCount = resources.getStringArray(R.array.fbw_duration);
                return new WorkoutForm(
                        getIdCheckedEqiupment(),
                        Type[getTypeSpinnerChose().getValue()],
                        new ArrayList<String>(),
                        Integer.parseInt(daysCount[getFrequencySpinnerChose().getValue()]),
                        scheduleType[getScheduleSpinnerChose().getValue()],
                        0);
            case 2:
                duration = new int[] {9,12,15,18,21,24,27,30};
                return new WorkoutForm(
                        getIdCheckedEqiupment(),
                        Type[getTypeSpinnerChose().getValue()],
                        new ArrayList<String>(),
                        0,
                        subtype[getWaySpinnerChose().getValue()],
                        duration[getTimeCardioSpinnerChose().getValue()]);
            case 3:
                duration = new int[] {15,18,21,24,27,30};
                return new WorkoutForm(
                        getIdCheckedEqiupment(),
                        Type[getTypeSpinnerChose().getValue()],
                        getBodyPartsIdChecked(),
                        0,
                        subtype[getWaySpinnerChose().getValue()],
                        duration[getTimeFitnesSpinnerChose().getValue()]);
        }

        return new WorkoutForm(
                getIdCheckedEqiupment(),
                "",
                getBodyPartsIdChecked(),
                0,
                "",
                0);
    }

    private ArrayList<String> getBodyPartsIdChecked()
    {
        ArrayList<String> bodyparts = new ArrayList<>();
        for(int i=0; i< bodyPartsChecked.size();++i)
        {
            bodyparts.add(bodyPartsChecked.get(i).getBodyTitle());
        }
        return bodyparts;
    }
}