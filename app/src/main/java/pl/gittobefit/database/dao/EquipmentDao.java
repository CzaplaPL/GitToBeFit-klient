package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;

import pl.gittobefit.database.entity.equipment.Equipment;
import pl.gittobefit.database.entity.equipment.EquipmentType;
import pl.gittobefit.database.entity.training.Exercise;

@Dao
public interface EquipmentDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipmentTypes(ArrayList<EquipmentType> equipmentTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipments(ArrayList<Equipment> equipments);
}
