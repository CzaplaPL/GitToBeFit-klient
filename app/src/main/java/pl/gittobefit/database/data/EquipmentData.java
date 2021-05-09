package pl.gittobefit.database.data;

import java.util.ArrayList;

import pl.gittobefit.database.entity.equipment.Equipment;
import pl.gittobefit.database.entity.equipment.EquipmentType;

public class EquipmentData
{
    public static ArrayList<EquipmentType> equipmentTypes()
    {
        return new ArrayList<EquipmentType>()
        {
            {
                add(new EquipmentType(1, "Maszyna", "z020treadmill2"));
                add(new EquipmentType(2, "Sprzęt fitness", "jumpingrope"));
                add(new EquipmentType(3, "Ciężary", "z037dumbbell2"));
            }
        };
    }

    public static ArrayList<Equipment> equipments()
    {
        return new ArrayList<Equipment>()
        {
            {
                add(new Equipment(3, "Ławeczka treningowa płaska", "bench", 1, true));
                add(new Equipment(4, "Ławeczka treningowa regulowana", "009machine4", 1, true));
                add(new Equipment(6, "Step", "z027step", 1, true));
                add(new Equipment(8, "Wyciąg górny", "z035machine", 1, true));
                add(new Equipment(11, "Roller", "z036dumbbell3", 2, true));
                add(new Equipment(13, "Taśma treningowa", "z031measuringtape", 2, true));
                add(new Equipment(25, "Mata treningowa", "z041yogamat", 2, true));
                add(new Equipment(14, "Gryf", "barbellbar", 3, true));
                add(new Equipment(15, "Hantle", "dumbbellswithweights", 3, true));
                add(new Equipment(23, "Ławka rzymska", "z013machine", 3, true));
                add(new Equipment(20, "Bez sprzetu", "", 4, true));
            }
        };
    }
}
