package pl.gittobefit.network.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IWorkoutFormsServices
{
    @GET("/user/search/{user_email}")
    Call<Void> getUserIDbyEmail(@Path("user_email") String email, @Header("Authorization") String authHeader);
}
