package pl.gittobefit.WorkoutDisplay.objects;

import java.util.ArrayList;

import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.entity.training.relation.TrainingWithForm;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.database.repository.TrainingRepository;

public class Training
{
    private WorkoutForm trainingForm;
    private ArrayList<TrainingPlan> planList = new ArrayList<>();
    private String generationDate;
    private String title;
    private int id;


    public Training(WorkoutForm form, ArrayList<TrainingPlan> trainingPlansServer, String trainingName)
    {
        this.trainingForm = form;
        this.planList = trainingPlansServer;
        this.title = trainingName;
    }

    public Training(TrainingWithForm trainingDB, TrainingRepository trainingRepository)
    {
        this.trainingForm = trainingDB.form;
        this.title = trainingDB.training.getTrainingName();
        this.planList = generatePlanList(trainingDB.training.getPlanList(),trainingRepository);

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

    public String getTrainingName() {
        return title;
    }

    public void setTrainingName(String trainingName) {
        this.title = trainingName;
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

    private ArrayList<TrainingPlan> generatePlanList(ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList,TrainingRepository trainingRepository)
    {
        ArrayList<TrainingPlan> trainingPlansServer = new ArrayList<>();
        for(int i = 0; i < planList.size(); ++i)
        {
            ArrayList<Exercise> exercisesDB = trainingRepository.getExerciseForPlanList(planList.get(i));
            trainingPlansServer.add(new TrainingPlan(planList.get(i),exercisesDB));
        }
        return trainingPlansServer;
    }
}
