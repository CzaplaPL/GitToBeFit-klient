package pl.gittobefit.network.interfaces;

import java.util.ArrayList;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ITrainingServices
{
    @POST("/training-plan/save")
    Call<Void> sendTrainings( @Header("Authorization") String auth ,@Body ArrayList<Training> trainings);

    @GET("/training-plan")
    Call<ArrayList<Training>> getTrainings( @Header("Authorization") String auth );
}
