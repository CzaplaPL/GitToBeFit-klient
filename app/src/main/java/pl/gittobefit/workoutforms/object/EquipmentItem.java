package pl.gittobefit.workoutforms.object;

import pl.gittobefit.database.entity.equipment.Equipment;

public class EquipmentItem extends EquipmentForm
{

    public EquipmentItem(int id, String name, String url, String description)
    {
        super(id, name, url);
    }

    public EquipmentItem(Equipment equipment)
    {
        super(equipment.getId(),equipment.getName(),equipment.getUrl());
    }
}
