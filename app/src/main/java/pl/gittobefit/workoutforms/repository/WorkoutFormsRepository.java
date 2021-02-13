package pl.gittobefit.workoutforms.repository;

import java.util.ArrayList;

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.EquipmentType;

public class WorkoutFormsRepository
{
    ArrayList<EquipmentType> equipmentTypes = new ArrayList<EquipmentType>();

    public void loadEquipmentTypes(EquipmentFragment equipmentFragment)
    {
        //TODO  wersja offline
        ConnectionToServer.getInstance().WorkoutFormsServices.getEquipmentType(equipmentFragment);
    }
}
