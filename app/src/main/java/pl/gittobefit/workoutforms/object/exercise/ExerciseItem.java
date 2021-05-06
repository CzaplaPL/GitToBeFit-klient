package pl.gittobefit.workoutforms.object.exercise;

import java.util.ArrayList;

import pl.gittobefit.workoutforms.object.EquipmentItem;


public class ExerciseItem
{
    private int id;
    private String name;
    private String descriptionOfStartPosition;
    private String descriptionOfCorrectExecution;
    private String hints;
    private String videoUrl;
    private String photoUrl;
    private BodyPart bodyPart;
    private ArrayList<TrainingTypes> trainingTypes;
    private ArrayList<EquipmentItem> equipmentsNeeded;
    private ScheduleType scheduleType;

    public ExerciseItem()
    {
    }


    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescriptionOfStartPosition()
    {
        return descriptionOfStartPosition;
    }

    public String getDescriptionOfCorrectExecution()
    {
        return descriptionOfCorrectExecution;
    }

    public String getHints()
    {
        return hints;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }

    public BodyPart getBodyPart()
    {
        return bodyPart;
    }

    public ArrayList<TrainingTypes> getTrainingTypes()
    {
        return trainingTypes;
    }

    public ArrayList<EquipmentItem> getEquipmentsNeeded()
    {
        return equipmentsNeeded;
    }

    public ScheduleType getScheduleType()
    {
        return scheduleType;
    }
}
