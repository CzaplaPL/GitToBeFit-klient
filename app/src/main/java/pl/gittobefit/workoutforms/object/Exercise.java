package pl.gittobefit.workoutforms.object;

public class Exercise
{
    int id;
    String name;
    String descriptionOfStartPosition;
    String descriptionOfCorrectExecution;
    String hints;
    String videoUrl;
    String photoUrl;

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
}
