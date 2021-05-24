package pl.gittobefit.workoutforms.object;

import pl.gittobefit.database.entity.equipment.Equipment;

public class EquipmentItem extends EquipmentForm
{
    public EquipmentItem(Equipment equipment)
    {
        super(equipment.getId(), equipment.getName(), equipment.getUrl(), equipment.isOffline());
    }
}
