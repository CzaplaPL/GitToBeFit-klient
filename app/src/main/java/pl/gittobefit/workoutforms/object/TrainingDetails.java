package pl.gittobefit.workoutforms.object;

import java.util.ArrayList;

public class TrainingDetails
{
    private String trainingType;
    private String duration;
    private String subType;
    private ArrayList<BodyParts> bodyPartsArrayList;

    public TrainingDetails(String trainingType, String duration, String subType, ArrayList<BodyParts> bodyPartsArrayList) {
        this.trainingType = trainingType;
        this.duration = duration;
        this.subType = subType;
        this.bodyPartsArrayList = bodyPartsArrayList;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public String getDuration() {
        return duration;
    }

    public String getSubType() {
        return subType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public ArrayList<BodyParts> getBodyPartsArrayList() {
        return bodyPartsArrayList;
    }

    public void setBodyPartsArrayList(ArrayList<BodyParts> bodyPartsArrayList) {
        this.bodyPartsArrayList = bodyPartsArrayList;
    }
}
