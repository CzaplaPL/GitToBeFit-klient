package pl.gittobefit.workoutforms.object;

import java.util.ArrayList;

public class EquipmentTypeItem extends EquipmentForm
{

    private ArrayList<EquipmentItem> equipment;

    public EquipmentTypeItem(int id, String name, String url)
    {
        super(id, name, url);
        equipment = new ArrayList<>();
    }

    public boolean isLoad()
    {
        if(equipment == null) return false;
        return (!equipment.isEmpty());
    }

    public ArrayList<EquipmentItem> getEquipment()
    {
        return equipment;
    }

    public void setEquipment(ArrayList<EquipmentItem> equipment)
    {

        this.equipment = equipment;
    }
}
