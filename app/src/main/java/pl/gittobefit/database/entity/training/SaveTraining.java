package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.user.User;
import pl.gittobefit.workoutforms.object.TrainingPlan;

@Entity
public class SaveTraining
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String idUser;
    private Long idForm;
    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList;

    public SaveTraining(Long idForm, ArrayList<TrainingPlan> planList)
    {
        this.idUser = User.getInstance().getIdSerwer();
        this.idForm = idForm;
        for (int i = 0; i < planList.size() ; i++)
        {
            TrainingPlan readPlan = planList.get(i);
            ArrayList<ExerciseExecutionPOJODB> savePlan= new ArrayList<>();
            for (int j = 0; j < readPlan.getExercisesExecutions().size(); j++)
            {
               savePlan.add(new ExerciseExecutionPOJODB(readPlan.getExerciseExecution(j)));
            }
            this.planList.add(savePlan);
        }
    }

    public SaveTraining()
    {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ArrayList<ExerciseExecutionPOJODB>> getPlanList() {
        return this.planList;
    }
    
    public void setPlanList(ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList) {
        this.planList = planList;
    }

    public Long getIdForm() {
        return idForm;
    }

    public void setIdForm(Long idForm) {
        this.idForm = idForm;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
