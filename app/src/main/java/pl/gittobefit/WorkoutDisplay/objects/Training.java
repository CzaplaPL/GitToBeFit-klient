package pl.gittobefit.WorkoutDisplay.objects;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.WorkoutForm;

public class Training
{
    private WorkoutForm trainingForm;
    private ArrayList<TrainingPlan> planList = new ArrayList<>();
    private String generationDate;
    private int id;
    public Training() { }

    public Training(WorkoutForm form, ArrayList<TrainingPlan> trainingPlansServer)
    {
        this.trainingForm = form;
        this.planList = trainingPlansServer;
    }

    public WorkoutForm getTrainingForm()
    {
        return trainingForm;
    }

    public ArrayList<TrainingPlan> getPlanList()
    {
        return planList;
    }

    public TrainingPlan getTrainingPlan(int i)
    {
        return planList.get(i);
    }

    public String getGenerationDate()
    {
        return generationDate;
    }

    public void setGenerationDate(String generationDate)
    {
        this.generationDate = generationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
