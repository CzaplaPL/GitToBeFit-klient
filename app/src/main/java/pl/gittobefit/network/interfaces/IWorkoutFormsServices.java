package pl.gittobefit.network.interfaces;

import java.util.ArrayList;

import pl.gittobefit.database.entity.equipment.Checksum;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.workoutforms.object.EquipmentItem;
import pl.gittobefit.workoutforms.object.EquipmentTypeItem;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IWorkoutFormsServices
{
    @GET("/equipment-type")
    Call<ArrayList<EquipmentTypeItem>> getEquipmentType();

    @GET("/equipment")
    Call<ArrayList<EquipmentItem>> getEquipment(@Query("typeId") int id);

    @GET("/equipment/no-equipment")
    Call<Void> getNoEquipment();

    @POST("/training-plan/generate")
    Call<Training> getTrainingPlan(@Body WorkoutForm formSend);

    @GET("/check-sum")
    Call<ArrayList<Checksum>> getChecksum();

    @POST("/training-plan/generate")
    Call<Training> getTrainingPlan(@Body WorkoutForm formSend, @Header("Date") String date);

    @POST("/training-plan/generate")
    Call<Training> getTrainingPlanForLoggedInUser(@Body WorkoutForm formSend,
                                                  @Header("Authorization") String authHeader,
                                                  @Header("Date") String date);
}
