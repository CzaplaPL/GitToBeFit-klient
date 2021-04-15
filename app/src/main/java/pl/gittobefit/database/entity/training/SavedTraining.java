package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public SavedTraining(long idForm, ArrayList<TrainingPlan> planList,String name)
    {
        this.planList = new ArrayList<>();
        if(User.getInstance().getLoggedBy() == User.WayOfLogin.NO_LOGIN)
        {
            this.idUser = "";
        }else
        {
            this.idUser = User.getInstance().getIdServer();
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.generationDate = formatter.format(date);
        this.trainingName = name;

        this.idForm = idForm;
        for(int i = 0; i < planList.size(); i++)
        {
            TrainingPlan readPlan = planList.get(i);
            ArrayList<ExerciseExecutionPOJODB> savePlan = new ArrayList<>();
            for(int j = 0; j < readPlan.getExercisesExecutions().size(); j++)
            {
                savePlan.add(new ExerciseExecutionPOJODB(readPlan.getExerciseExecution(j), readPlan.getId(), readPlan.getTrainingId()));
            }
            this.planList.add(savePlan);
        }

    }

    public SavedTraining()
    {
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
}
