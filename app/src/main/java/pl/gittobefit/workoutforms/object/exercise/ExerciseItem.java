package pl.gittobefit.workoutforms.object.exercise;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.Exercise;
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

    public ExerciseItem(Exercise exercise)
    {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.descriptionOfStartPosition = exercise.getDescriptionOfStartPosition();
        this.descriptionOfCorrectExecution = exercise.getDescriptionOfCorrectExecution();
        this.hints = exercise.getHints();
        this.videoUrl = exercise.getVideoUrl();
        this.photoUrl = exercise.getPhotoUrl();
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

    public String getBodyPart()
    {
        return bodyPart.getName();
    }

    public ArrayList<TrainingTypes> getTrainingTypes()
    {
        return trainingTypes;
    }

    public ArrayList<EquipmentItem> getEquipmentsNeeded()
    {
        return equipmentsNeeded;
    }

    public String getScheduleType()
    {
        return scheduleType.getName();
    }
}
