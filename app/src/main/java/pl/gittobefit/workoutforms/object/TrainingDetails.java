package pl.gittobefit.workoutforms.object;

import java.util.ArrayList;

public class TrainingDetails
{
    private String trainingType;
    private String duration;
    private String subType;
    private ArrayList<BodyParts> bodyPartsArrayListForSplit;
    private ArrayList<BodyParts> bodyPartsArrayListForFitness;

    public TrainingDetails(String trainingType, String duration, String subType, ArrayList<BodyParts> bodyPartsArrayListForSplit,  ArrayList<BodyParts> bodyPartsArrayListForFitness) {
        this.trainingType = trainingType;
        this.duration = duration;
        this.subType = subType;
        this.bodyPartsArrayListForSplit = bodyPartsArrayListForSplit;
        this.bodyPartsArrayListForFitness = bodyPartsArrayListForFitness;
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

    public ArrayList<BodyParts> getBodyPartsArrayListForSplit() {
        return bodyPartsArrayListForSplit;
    }

    public void setBodyPartsArrayListForSplit(ArrayList<BodyParts> bodyPartsArrayListForSplit) {
        this.bodyPartsArrayListForSplit = bodyPartsArrayListForSplit;
    }
}
