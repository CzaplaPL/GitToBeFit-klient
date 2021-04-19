package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.user.User;


@Entity
public class SavedTraining
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String idUser;
    private long idForm;
    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList;
    private String generationDate;
    private String trainingName;
    private int trainingDay;

    public SavedTraining(long idForm, ArrayList<TrainingPlan> planList)
    {
        this.planList = new ArrayList<>();
        if(User.getInstance().getLoggedBy() != User.WayOfLogin.NO_LOGIN)
        {
            this.idUser = "";
        }else
        {
            this.idUser = User.getInstance().getIdServer();
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.generationDate = formatter.format(date);
        this.trainingName = "Default training name";

        this.idForm = idForm;
        for(TrainingPlan readPlan : planList)
        {
            ArrayList<ExerciseExecutionPOJODB> savePlan = new ArrayList<>();
            for(ExerciseExecution exercisesExecution : readPlan.getExercisesExecutions())
            {
                savePlan.add(new ExerciseExecutionPOJODB(exercisesExecution, readPlan.getId(), readPlan.getTrainingId()));
            }
            this.planList.add(savePlan);
        }

        this.trainingDay = 0;
    }

    public SavedTraining()
    {
        this.trainingDay = 0;
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ArrayList<ArrayList<ExerciseExecutionPOJODB>> getPlanList()
    {
        return this.planList;
    }

    public void setPlanList(ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList)
    {
        this.planList = planList;
    }

    public long getIdForm()
    {
        return idForm;
    }

    public void setIdForm(long idForm)
    {
        this.idForm = idForm;
    }

    public String getIdUser()
    {
        return idUser;
    }

    public void setIdUser(String idUser)
    {
        this.idUser = idUser;
    }

    public String getGenerationDate()
    {
        return generationDate;
    }

    public void setGenerationDate(String generationDate)
    {
        this.generationDate = generationDate;
    }

    public String getTrainingName()
    {
        return trainingName;
    }

    public void setTrainingName(String trainingName)
    {
        this.trainingName = trainingName;
    }

    public int getTrainingDay()
    {
        return trainingDay;
    }

    public void setTrainingDay(int trainingDay)
    {
        this.trainingDay = trainingDay;
    }

    public boolean isNextDay()
    {
        return planList.size() > trainingDay + 1;
    }
}
