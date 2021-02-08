package pl.gittobefit.network.interfaces;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWorkoutFormsServices
{
    @GET("/equipment-type")
    Call<ArrayList<EquipmentType>> getEquipmentType();

    @GET("/equipment-type")
    Call<ArrayList<Equipment>> getEquipment(@Query("typeId")int id);
}
