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

   // private String repeat;
   // private String bodyPart;
   // private ArrayList<String> trainingTypes;

    public Exercise()
    {
    }

    public Exercise(int id, String name, String descriptionOfStartPosition, String descriptionOfCorrectExecution, String hints, String videoUrl, String photoUrl, boolean repeat, String bodyPart, String[] trainingTypes)
    {
        this.id = id;
        this.name = name;
        this.descriptionOfStartPosition = descriptionOfStartPosition;
        this.descriptionOfCorrectExecution = descriptionOfCorrectExecution;
        this.hints = hints;
        this.videoUrl = videoUrl;
        this.photoUrl = photoUrl;
       /* this.repeat = repeat;
        this.bodyPart = bodyPart;
        this.trainingTypes.addAll(Arrays.asList(trainingTypes));*/
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

   /* public boolean isRepeat()
    {
        return repeat;
    }

    public void setRepeat(boolean repeat)
    {
        this.repeat = repeat;
    }

    public String getBodyPart()
    {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart)
    {
        this.bodyPart = bodyPart;
    }

    public String getTrainingTypes()
    {
        return TrainingTypes;
    }

    public void setTrainingTypes(String trainingTypes)
    {
        TrainingTypes = trainingTypes;
    }*/
}
