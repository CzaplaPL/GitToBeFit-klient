package pl.gittobefit.network.interfaces;

import java.util.ArrayList;

import pl.gittobefit.WorkoutDisplay.objects.Training;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITrainingServices
{
    @POST("/training-plan/save")
    Call<Void> sendTrainings(@Header("Authorization") String auth, @Body ArrayList<Training> trainings);

    @GET("/training-plan")
    Call<ArrayList<Training>> getTrainings(@Header("Authorization") String auth);

    @PUT("/training-plan/updateTitle/{id}")
    Call<Void> updateTrainingTitle(@Path("id") String id, @Header("Authorization") String authHeader,
                                   @Header("title") String title);
}
