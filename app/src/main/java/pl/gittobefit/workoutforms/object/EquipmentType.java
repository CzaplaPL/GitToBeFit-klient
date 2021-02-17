package pl.gittobefit.workoutforms.object;

import java.util.ArrayList;

public class EquipmentType extends EquipmentForm
{

    private ArrayList<Equipment> equipment ;
    public EquipmentType(int id, String name, String url)
    {
        super(id, name, url);
    }
    public boolean isLoad()
    {
        if(equipment==null) return false;
        return (!equipment.isEmpty());
    }
    public ArrayList<Equipment> getEquipment()
    {
        return equipment;
    }
    public void setEquipment(ArrayList<Equipment> equipment)
    {
        this.equipment = equipment;
    }
}
