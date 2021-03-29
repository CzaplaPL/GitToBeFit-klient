package pl.gittobefit.network.interfaces;

import java.util.ArrayList;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ITrainingServices
{
    @POST("/training-plan/save")
    Call<Void> sendTraining( @Header("Authorization") String auth ,@Header("Authorization") String idUser,@Body ArrayList<Training> trainings);
}
