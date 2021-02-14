package pl.gittobefit.workoutforms.repository;

import java.util.ArrayList;

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

public class WorkoutFormsRepository
{
    ArrayList<EquipmentType> equipmentTypes = new ArrayList<>();
    ArrayList<Integer> checkedEquipment = new ArrayList<Integer>();

    public void loadEquipmentTypes(EquipmentFragment equipmentFragment)
    {
        ConnectionToServer.getInstance().WorkoutFormsServices.getEquipmentType(equipmentFragment);
    }

    public void loadEquipment(int id, int position, GenerateTraningViewModel generateTraningViewModel)
    {
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(equipmentTypes.get(i).getId()==id)
            {
                boolean odp = equipmentTypes.get(i).isLoad();
                if(odp)
                {
                    generateTraningViewModel.loadEquipment(position, equipmentTypes.get(i).getEquipment());
                    return;
                }
            }
        }
        ConnectionToServer.getInstance().WorkoutFormsServices.getEquipment(id, position, generateTraningViewModel);
    }

    public void setEqiupmentChecked(int id)
    {
        checkedEquipment.add(id);
    }

    public void addEquipment(int typeid, ArrayList<Equipment> body)
    {
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(equipmentTypes.get(i).getId()==typeid)
            {
                equipmentTypes.get(i).setEquipment(body);
                return;
            }

        }
    }

    public void setEqiupmentTypes(ArrayList<EquipmentType> equipmentTypes)
    {
        this.equipmentTypes= equipmentTypes;
    }
}
