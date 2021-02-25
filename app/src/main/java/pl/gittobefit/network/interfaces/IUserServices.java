package pl.gittobefit.network.interfaces;

import pl.gittobefit.network.object.UserChangeEmail;
import pl.gittobefit.network.object.UserChangePass;
import pl.gittobefit.network.object.RespondUser;
import pl.gittobefit.network.object.TokenUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * interfejs(dao) do komunikacji sieciowej o użytkowniku
 */
public interface IUserServices
{
    /**
     * logowanie kontem aplikacji
     * @author czapla
     */
    @POST("/user/login")
    Call<Void> login(@Body RespondUser user);

    /**
     * logowanie kontem google
     * @author czapla
     */
    @POST("/user/login/google")
    Call<Void> loginGoogle(@Body TokenUser token);

    /**
     * logowanie kontem facebook
     * @author czapla
     */
    @POST("/user/login/facebook")
    Call<Void> loginFacebook(@Body TokenUser token);

    /**
     * pobieranie id po emailu
     * @author Kuba
     */
    @GET("/user/search/{user_email}")
    Call<Void> getUserIDbyEmail(@Path("user_email") String email, @Header("Authorization") String authHeader);

    /**
     * zmiana hasła
     * @author Kuba
     */
    @PUT("/user/{user_id}/password-update")
    Call<Void> changePassword(@Path("user_id") String id, @Header("Authorization") String authHeader, @Body UserChangePass userChangePass);

    /**
     * usuwanie konta
     * @author Kuba
     */
    @DELETE("/user/{user_id}")
    Call<Void> deleteAccount(@Path("user_id") String id, @Header("Authorization") String authHeader, @Header("password") String userPassword);

    /**
     * zmiana maila
     * @author Kuba
     */
    @PUT("/user/{user_id}/email-update")
    Call<Void> changeEmail(@Path("user_id") String id, @Header("Authorization") String authHeader, @Body UserChangeEmail userChangeEmail);

    /**
     * przypominanie hasła
     * @author czapla
     */
    @POST("/user/remind-password")
    Call<Void> remindPass( @Query("email") String email);

    /**
     * rejestracja
     * @return void
     */
    @POST("/user/signup")
    Call<Void> signup( @Body RespondUser user);

    /***
     * @author Kuba
     * @return void
     */
    @POST("/user/token-verification")
    Call<Void> verify(@Header("token") String token);
}
