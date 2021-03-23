package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

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

    public SavedTraining(long idForm, ArrayList<TrainingPlan> planList)
    {
        this.planList = new ArrayList<>();
        if(User.getInstance().getLoggedBy() != User.WayOfLogin.NO_LOGIN)
        {
            this.idUser = "";
        } else
        {
            this.idUser = User.getInstance().getIdSerwer();
        }

        this.idForm = idForm;
        for(int i = 0; i < planList.size(); i++)
        {
            TrainingPlan readPlan = planList.get(i);
            ArrayList<ExerciseExecutionPOJODB> savePlan = new ArrayList<>();
            for(int j = 0; j < readPlan.getExercisesExecutions().size(); j++)
            {
                savePlan.add(new ExerciseExecutionPOJODB(readPlan.getExerciseExecution(j)));
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
}
