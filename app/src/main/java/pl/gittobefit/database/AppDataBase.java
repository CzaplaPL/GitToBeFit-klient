package pl.gittobefit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import pl.gittobefit.database.conventer.TrainingConverter;
import pl.gittobefit.database.dao.EquipmentDao;
import pl.gittobefit.database.dao.IExerciseDao;
import pl.gittobefit.database.dao.IFormDao;
import pl.gittobefit.database.dao.ITrainingDao;
import pl.gittobefit.database.dao.IUserDao;
import pl.gittobefit.database.data.EquipmentData;
import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.database.entity.equipment.Equipment;
import pl.gittobefit.database.entity.equipment.EquipmentType;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.SavedTraining;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.entity.training.relation.ExerciseToEquipment;
import pl.gittobefit.database.entity.training.relation.TrainingTypesToExercise;

/**
 * klasa bazy danych
 */
@Database(entities = {UserEntity.class, WorkoutForm.class, Exercise.class, SavedTraining.class, EquipmentType.class, Equipment.class, ExerciseToEquipment.class, TrainingTypesToExercise.class}, version = 2134, exportSchema = false)
@TypeConverters({TrainingConverter.class})
public abstract class AppDataBase extends RoomDatabase
{
    private static volatile AppDataBase INSTANCE;
    public abstract IUserDao userDao();
    public abstract EquipmentDao equipmentDao();
    public abstract ITrainingDao trainingDao();
    public abstract IExerciseDao exerciseDao();
    public abstract IFormDao workoutFormDao();

    public static AppDataBase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "room-db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                    INSTANCE.equipmentDao().insertEquipmentTypes(EquipmentData.equipmentTypes());
                    INSTANCE.equipmentDao().insertEquipments(EquipmentData.equipments());
                }
            }
        }
        return INSTANCE;
    }
}
