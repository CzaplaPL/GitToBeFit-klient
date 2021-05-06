package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;

import pl.gittobefit.workoutforms.object.exercise.ExerciseItem;

@Entity
public class Exercise
{
    @PrimaryKey
    private int id;
    private String name;
    private String descriptionOfStartPosition;
    private String descriptionOfCorrectExecution;
    private String hints;
    private String videoUrl;
    private String photoUrl;
    private String scheduleType;
    private String bodyPart;

    public Exercise()
    {
    }

    public Exercise(int id, String name, String descriptionOfStartPosition, String descriptionOfCorrectExecution, String hints, String videoUrl, String photoUrl, String scheduleType, String bodyPart)
    {
        this.id = id;
        this.name = name;
        this.descriptionOfStartPosition = descriptionOfStartPosition;
        this.descriptionOfCorrectExecution = descriptionOfCorrectExecution;
        this.hints = hints;
        this.videoUrl = videoUrl;
        this.photoUrl = photoUrl;
        this.scheduleType = scheduleType;
        this.bodyPart = bodyPart;
    }

    public Exercise(ExerciseItem exercise)
    {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.descriptionOfStartPosition = exercise.getDescriptionOfStartPosition();
        this.descriptionOfCorrectExecution = exercise.getDescriptionOfCorrectExecution();
        this.hints = exercise.getHints();
        this.videoUrl = exercise.getVideoUrl();
        this.photoUrl = exercise.getPhotoUrl();
        this.scheduleType = exercise.getScheduleType();
        this.bodyPart = exercise.getBodyPart();
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

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescriptionOfStartPosition(String descriptionOfStartPosition)
    {
        this.descriptionOfStartPosition = descriptionOfStartPosition;
    }

    public void setDescriptionOfCorrectExecution(String descriptionOfCorrectExecution)
    {
        this.descriptionOfCorrectExecution = descriptionOfCorrectExecution;
    }

    public void setHints(String hints)
    {
        this.hints = hints;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public void setPhotoUrl(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }



    public String getBodyPart()
    {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart)
    {
        this.bodyPart = bodyPart;
    }

    public String getScheduleType()
    {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType)
    {
        this.scheduleType = scheduleType;
    }
}
