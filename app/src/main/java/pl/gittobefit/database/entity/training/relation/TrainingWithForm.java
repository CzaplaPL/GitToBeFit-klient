package pl.gittobefit.database.entity.training.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;

public class TrainingWithForm
{
    @Embedded
    public WorkoutForm form;
    @Relation(
            parentColumn = "FormId",
            entityColumn = "idForm"
    )
    public SavedTraining training;
}
