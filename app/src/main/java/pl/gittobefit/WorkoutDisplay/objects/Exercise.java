package pl.gittobefit.WorkoutDisplay.objects;

public class Exercise
{
    private int id;
    private String name;
    private String descriptionOfStartPosition;
    private String descriptionOfCorrectExecution;
    private String hints;
    private String videoUrl;
    private String photoUrl;

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