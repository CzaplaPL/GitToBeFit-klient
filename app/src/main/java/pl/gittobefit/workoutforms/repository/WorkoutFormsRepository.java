package pl.gittobefit.workoutforms.repository;

import java.util.ArrayList;

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

public class WorkoutFormsRepository
{
    ArrayList<EquipmentType> equipmentTypes = new ArrayList<EquipmentType>();

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
                equipmentTypes.get(i).isLoad();
                generateTraningViewModel.loadEquipment(position,equipmentTypes.get(i).getEquipment());
                return;
            }
        }
        ConnectionToServer.getInstance().WorkoutFormsServices.getEquipment(id, position, generateTraningViewModel);
    }
    
}
