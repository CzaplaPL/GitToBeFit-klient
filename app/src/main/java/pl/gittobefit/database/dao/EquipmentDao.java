package pl.gittobefit.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.database.entity.equipment.Checksum;
import pl.gittobefit.database.entity.equipment.Equipment;
import pl.gittobefit.database.entity.equipment.EquipmentType;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.workoutforms.object.EquipmentItem;

@Dao
public interface EquipmentDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipmentTypes(ArrayList<EquipmentType> equipmentTypes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void initEquipmentTypes(ArrayList<EquipmentType> equipmentTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipments(ArrayList<Equipment> equipments);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void initEquipments(ArrayList<Equipment> equipments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipment(Equipment equipment);

    @Transaction
    @Query("SELECT * FROM EquipmentType")
    List<EquipmentType> getAllEquipmentType();

    @Query("SELECT id FROM Equipment WHERE name='Bez sprzetu'")
    int getNoEquipmentId();

    @Transaction
    @Query("SELECT * FROM Checksum")
    List<Checksum> getAllChecksum();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addChecksum(ArrayList<Checksum> checksum);

    @Transaction
    @Query("SELECT * FROM Checksum WHERE `Table`='git2befit.equipment'")
    Checksum getLoadedType();

    @Query("SELECT * FROM Equipment WHERE type=:typeId")
    List<Equipment> getEquipmentForType(int typeId);
}
