package pl.gittobefit.database.entity.training.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;

public class TrainingWithForm
{
    @Embedded
    public SavedTraining training;
    @Relation(
            parentColumn = "idForm",
            entityColumn = "FormId"
    )
    public WorkoutForm form;

}
