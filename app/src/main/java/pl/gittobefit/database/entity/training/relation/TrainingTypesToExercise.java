package pl.gittobefit.database.entity.training.relation;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrainingTypesToExercise
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int exerciseId;
    private String trainingType;

    public TrainingTypesToExercise(String trainingType, int exerciseId)
    {
        this.exerciseId = exerciseId;
        this.trainingType = trainingType;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getExerciseId()
    {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId)
    {
        this.exerciseId = exerciseId;
    }

    public String getTrainingType()
    {
        return trainingType;
    }

    public void setTrainingType(String trainingType)
    {
        this.trainingType = trainingType;
    }
}
