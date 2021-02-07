package pl.gittobefit.network.interfaces;

import java.util.List;

import pl.gittobefit.workoutforms.object.EquipmentType;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IWorkoutFormsServices
{
    @GET("/equipment-type")
    Call<List<EquipmentType>> getEquipmentType();
}
