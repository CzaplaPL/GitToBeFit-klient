package pl.gittobefit.WorkoutDisplay.objects;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.WorkoutForm;

public class Training
{
    private WorkoutForm trainingForm;
    private ArrayList<TrainingPlan> planList = new ArrayList<>();
    private String generationDate;
    private String trainingName;
    private int id;


    public WorkoutForm getTrainingForm() {
        return trainingForm;
    }

    public ArrayList<TrainingPlan> getPlanList() {
        return planList;
    }

    public TrainingPlan getTrainingPlan(int i)
    {
        return planList.get(i);
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public void setTrainingForm(WorkoutForm trainingForm) {
        this.trainingForm = trainingForm;
    }

    public void setPlanList(ArrayList<TrainingPlan> planList) {
        this.planList = planList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
