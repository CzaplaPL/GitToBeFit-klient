package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import pl.gittobefit.workoutforms.object.TrainingPlan;

@Entity
public class SaveTraining
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Long idForm;
    private ArrayList<TrainingPlan> planList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdForm() {
        return idForm;
    }

    public void setIdForm(Long idForm) {
        this.idForm = idForm;
    }

    public ArrayList<TrainingPlan> getPlanList() {
        return planList;
    }

    public void setPlanList(ArrayList<TrainingPlan> planList) {
        this.planList = planList;
    }
}
