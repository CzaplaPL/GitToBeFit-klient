package pl.gittobefit.database.entity.training.relation;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciseToEquipment
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int exerciseId;
    private int equipmentId;

    public ExerciseToEquipment(int equipmentId, int exerciseId)
    {
        this.exerciseId = exerciseId;
        this.equipmentId = equipmentId;
    }

    public int getExerciseId()
    {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId)
    {
        this.exerciseId = exerciseId;
    }

    public int getEquipmentId()
    {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
