package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class WorkoutForm
{
    @PrimaryKey(autoGenerate = true)
    private int FormId;
    private ArrayList<Integer> equipmentIDs;
    private String trainingType;
    private ArrayList<String> bodyParts;
    private int daysCount;
    private String scheduleType;
    private int duration;
    public int userOwnerId;

    public ArrayList<Integer> getEquipmentIDs()
    {
        return equipmentIDs;
    }

    public String getTrainingType()
    {
        return trainingType;
    }

    public ArrayList<String> getBodyParts()
    {
        return bodyParts;
    }

    public int getDaysCount()
    {
        return daysCount;
    }

    public String getScheduleType()
    {
        return scheduleType;
    }

    public int getDuration()
    {
        return duration;
    }

    public WorkoutForm(ArrayList<Integer> equipmentIDs, String trainingType, ArrayList<String> bodyParts, int daysCount, String scheduleType, int duration)
    {
        this.equipmentIDs = equipmentIDs;
        this.trainingType = trainingType;
        this.bodyParts = bodyParts;
        this.daysCount = daysCount;
        this.scheduleType = scheduleType;
        this.duration = duration;
    }


    public int getFormId()
    {
        return FormId;
    }

    public void setFormId(int formId)
    {
        FormId = formId;
    }

    public boolean checkIfScheduleTypeIsCircuit()
    {
        return this.scheduleType.equalsIgnoreCase("CIRCUIT");
    }
}
