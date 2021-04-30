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
                add(new EquipmentType(1, "Maszyna", "020-treadmill-2.png"));
                add(new EquipmentType(2, "Sprzęt fitness", "jumping-rope.png"));
                add(new EquipmentType(3, "Ciężary", "037-dumbbell-2.png"));
            }
        };
    }

    public static ArrayList<Equipment> equipments()
    {
        return new ArrayList<Equipment>()
        {
            {
                add(new Equipment(3, "Ławeczka treningowa płaska", "bench.png", 1));
                add(new Equipment(4, "Ławeczka treningowa regulowana", "009-machine-4.png ", 1));
                add(new Equipment(6, "Step", "027-step.png", 1));
                add(new Equipment(8, "Wyciąg górny", "035-machine.png", 1));
                add(new Equipment(11, "Roller", "036-dumbbell-3.png", 2));
                add(new Equipment(13, "Taśma treningowa", "031-measuring-tape.png", 2));
                add(new Equipment(25, "Mata treningowa", "041-yoga-mat.png", 2));
                add(new Equipment(14, "Gryf", "barbell-bar.png", 3));
                add(new Equipment(15, "Hantle", "barbell-bar.png", 3));
                add(new Equipment(23, "Ławka rzymska", "013-machine.png", 3));
                add(new Equipment(20, "Bez sprzetu", "", 4));
            }
        };
    }
}
