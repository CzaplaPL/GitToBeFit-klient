package pl.gittobefit.network.interfaces;

import java.util.List;

import pl.gittobefit.workout.object.EquipmentType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IWorkoutFormsServices
{
    @GET("/equipment-type")
    Call<List<EquipmentType>> getEquipmentType();
}
