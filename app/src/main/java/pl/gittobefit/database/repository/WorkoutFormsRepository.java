package pl.gittobefit.database.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.equipment.Checksum;
import pl.gittobefit.database.entity.equipment.Equipment;
import pl.gittobefit.database.entity.equipment.EquipmentType;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.object.EquipmentItem;
import pl.gittobefit.workoutforms.object.EquipmentTypeItem;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

public class WorkoutFormsRepository
{
    private ArrayList<EquipmentTypeItem> equipmentTypes = new ArrayList<>();
    private GenerateTraningViewModel observer;
    private Context context;
    private AppDataBase base;
    private boolean isCurrent = false;
    private boolean isChecksumChecked = false;
    private ArrayList<Integer> isEquipmentDownload = new ArrayList<>();

    private static volatile WorkoutFormsRepository INSTANCE;

    public WorkoutFormsRepository(Context context)
    {

        this.context = context;
        this.base = AppDataBase.getInstance(context);
    }

    public static WorkoutFormsRepository getInstance(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized(WorkoutFormsRepository.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = new WorkoutFormsRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    public void setObserver(GenerateTraningViewModel observer)
    {
        this.observer = observer;
    }

    public void loadEquipmentTypes()
    {
        if(ConnectionToServer.isNetwork(context))
        {
            if(!isChecksumChecked)
            {
                ConnectionToServer.getInstance().WorkoutFormsServices.checkChecksum(this);
            }else
            {

            }

        }else
        {
            setEquipmentTypes();
        }

    }

    public void loadEquipment(int typeId, int position)
    {
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(equipmentTypes.get(i).getId() == typeId)
            {
                if(equipmentTypes.get(i).isLoad())
                {
                    observer.loadEquipment(position, equipmentTypes.get(i).getEquipment());
                    return;
                }
            }
        }

        if(  ConnectionToServer.isNetwork(context) && (!isCurrent || !isEquipmentDownload.contains(typeId)))
        {
            ConnectionToServer.getInstance().WorkoutFormsServices.getEquipment(typeId, position, this);
        }else
        {
            ArrayList<Equipment> equipmentsDB =base.equipmentDao().getEquipmentForType(typeId);
            ArrayList<EquipmentItem> equipmentItems = new ArrayList<>();
            for(Equipment equipment : equipmentsDB)
            {
                equipmentItems.add(new EquipmentItem(equipment));
            }
            addEquipment(typeId,equipmentItems,position);
        }

    }



    public void addEquipment(int typeid, ArrayList<EquipmentItem> response, int position)
    {
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(equipmentTypes.get(i).getId() == typeid)
            {
                if(equipmentTypes.get(i).getName().equals("Kalistenika"))
                {
                    for(int j = 0; j < response.size(); j++)
                    {
                        if(response.get(j).getId() == observer.getNoEquipmentid())
                        {
                            response.remove(j);
                            break;
                        }
                    }
                }

                equipmentTypes.get(i).setEquipment(response);
                observer.loadEquipment(position, equipmentTypes.get(i).getEquipment());
                break;
            }
        }
    }


    public ArrayList<Integer> getIdCheckEqiupment()
    {
        ArrayList<Integer> respond = new ArrayList<>();
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(!equipmentTypes.get(i).isLoad()) continue;
            for(int j = 0; j < equipmentTypes.get(i).getEquipment().size(); j++)
            {
                if(equipmentTypes.get(i).getEquipment().get(j).isIschecked())
                {
                    respond.add(equipmentTypes.get(i).getEquipment().get(j).getId());
                }
            }
        }
        return respond;
    }

    public ArrayList<EquipmentItem> getCheckEqiupment()
    {
        ArrayList<EquipmentItem> respond = new ArrayList<>();
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(!equipmentTypes.get(i).isLoad()) continue;
            for(int j = 0; j < equipmentTypes.get(i).getEquipment().size(); j++)
            {
                if(equipmentTypes.get(i).getEquipment().get(j).isIschecked())
                {
                    respond.add(equipmentTypes.get(i).getEquipment().get(j));
                }
            }
        }
        return respond;
    }

    public void setChecksum(ArrayList<Checksum> body)
    {
        this.isCurrent = true;
        ArrayList<Checksum> baseChecksums = (ArrayList<Checksum>) base.equipmentDao().getAllChecksum();
        if(baseChecksums.size() < 1)
        {
            isCurrent = false;
            base.equipmentDao().addChecksum(body);
        }
        for(Checksum baseChecksum : baseChecksums)
        {
            for(Checksum checksum : body)
            {
                if(baseChecksum.getChecksum().equals(checksum.getChecksum()) &&
                        baseChecksum.getTable().equals(checksum.getTable()))
                {
                    body.remove(checksum);
                    break;
                }
            }
        }
        if(body.size() != 0)
        {
            base.equipmentDao().addChecksum(body);
            isCurrent = false;
        }

        if(!isCurrent)
        {
            ConnectionToServer.getInstance().WorkoutFormsServices.getEquipmentType(this);
        }
        {
            setEquipmentTypes();
        }
    }

    public void downloadEquipmentType(ArrayList<EquipmentTypeItem> equipmentTypes, int noEquipmentId)
    {
        ArrayList<EquipmentType> equipmentTypesToDB = new ArrayList<EquipmentType>();
        for(EquipmentTypeItem equipmentType : equipmentTypes)
        {
            equipmentTypesToDB.add(new EquipmentType(equipmentType));
        }
        base.equipmentDao().insertEquipmentTypes(equipmentTypesToDB);
        Equipment noEquipment = new Equipment(noEquipmentId, "Bez sprzetu", "", 4);
        base.equipmentDao().insertEquipment(noEquipment);
        this.equipmentTypes.clear();
        this.equipmentTypes.addAll(equipmentTypes);
        observer.initList(equipmentTypes);
        observer.setNoEquipmentid(noEquipmentId);
        observer.setNoEquipmentcheched(true);
    }


    private void setEquipmentTypes()
    {
        if(equipmentTypes.size() == 0)
        {
            ArrayList<EquipmentType> baseEquipmentType = new ArrayList<>(base.equipmentDao().getAllEquipmentType());
            for(EquipmentType equipmentType : baseEquipmentType)
            {
                equipmentTypes.add(new EquipmentTypeItem(equipmentType));
            }
        }
        int noEquipmentId = base.equipmentDao().getNoEquipmentId();
        observer.initList(equipmentTypes);
        observer.setNoEquipmentid(noEquipmentId);
        observer.setNoEquipmentcheched(true);
        isEquipmentDownload = new ArrayList<>(base.equipmentDao().getLoadedType());
    }
}
