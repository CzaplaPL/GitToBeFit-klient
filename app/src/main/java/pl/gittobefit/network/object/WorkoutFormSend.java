package pl.gittobefit.network.object;

import java.util.ArrayList;

public class WorkoutFormSend
{
    ArrayList<Integer> equipmentIDs;
    String trainingType;
    ArrayList<String> bodyParts;
    int daysCount;
    String scheduleType;
    int duration;

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

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public WorkoutFormSend(ArrayList<Integer> equipmentIDs, String trainingType, ArrayList<String> bodyParts, int daysCount, String scheduleType, int duration)
    {
        this.equipmentIDs = equipmentIDs;
        this.trainingType = trainingType;
        this.bodyParts = bodyParts;
        this.daysCount = daysCount;
        this.scheduleType = scheduleType;
        this.duration = duration;
    }
}
