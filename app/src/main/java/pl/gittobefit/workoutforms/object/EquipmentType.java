package pl.gittobefit.workoutforms.object;

import android.view.View;

import java.util.ArrayList;

public class EquipmentType extends EquipmentForm
{
    private ArrayList<Equipment> equipment = new ArrayList<Equipment>();
    public EquipmentType(int id, String name, String url)
    {
        super(id, name, url);
    }

    public boolean isLoad()
    {
        return (equipment.size() != 0);
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
