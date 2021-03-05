package pl.gittobefit.network.interfaces;

import java.util.ArrayList;

import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.object.Training;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IWorkoutFormsServices
{
    @GET("/equipment-type")
    Call<ArrayList<EquipmentType>> getEquipmentType();

    @GET("/equipment")
    Call<ArrayList<Equipment>> getEquipment(@Query("typeId")int id);
    @GET("/equipment/no-equipment")
    Call<Void> getNoEquipment();

    @POST("/training-plan/generate")
    Call<Training> getTrainingPlan();
}
