package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise
{
    @PrimaryKey
    private int id;
    private String name;
    private String descriptionOfStartPosition;
    private String descriptionOfCorrectExecution;
    private String hints;
    private  String videoUrl;
    private  String photoUrl;

    public Exercise() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescriptionOfStartPosition() {
        return descriptionOfStartPosition;
    }

    public String getDescriptionOfCorrectExecution() {
        return descriptionOfCorrectExecution;
    }

    public String getHints() {
        return hints;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescriptionOfStartPosition(String descriptionOfStartPosition) {
        this.descriptionOfStartPosition = descriptionOfStartPosition;
    }

    public void setDescriptionOfCorrectExecution(String descriptionOfCorrectExecution) {
        this.descriptionOfCorrectExecution = descriptionOfCorrectExecution;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
