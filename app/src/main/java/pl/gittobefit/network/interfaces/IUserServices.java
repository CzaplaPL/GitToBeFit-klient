package pl.gittobefit.network.interfaces;

import pl.gittobefit.network.object.ChangeEmailUser;
import pl.gittobefit.network.object.ChangePassUser;
import pl.gittobefit.network.object.RespondUser;
import pl.gittobefit.network.object.TokenUser;
import pl.gittobefit.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * interfejs(dao) do komunikacji sieciowej o użytkowniku
 */
public interface IUserServices
{
    /**
     * logowanie kontem aplikacji
     *
     * @param user RespondUser
     * @return void
     * @author czapla
     */
    @POST("/user/login")
    Call<Void> login(@Body RespondUser user);

    /**
     * logowanie kontem google
     *
     * @param token token otrzymany przez google
     * @return void
     * @author czapla
     */
    @POST("/user/login/google")
    Call<Void> loginGoogle(@Body TokenUser token);

    /**
     * logowanie kontem facebook
     *
     * @param token token otrzymany przez fb
     * @return void
     * @author czapla
     */
    @POST("/user/login/facebook")
    Call<Void> loginFacebook(@Body TokenUser token);

    /**
     * @param email      email zalogowanego usera
     * @param authHeader token usera
     * @return void
     * @author Kuba
     */
    @GET("/user/search/{user_email}")
    Call<Void> getUserIDbyEmail(@Path("user_email") String email, @Header("Authorization") String authHeader);

    /**
     * @param id             idUser
     * @param authHeader     token usera
     * @param changePassUser body do zmiany hasła
     * @return void
     * @author Kuba
     */
    @PUT("/user/{user_id}/password-update")
    Call<Void> changePassword(@Path("user_id") String id, @Header("Authorization") String authHeader, @Body ChangePassUser changePassUser);

    /**
     * @param id         idUser
     * @param authHeader token usera
     * @return void
     * @author Kuba
     */
    @DELETE("/user/{user_id}")
    Call<Void> deleteAccount(@Path("user_id") String id, @Header("Authorization") String authHeader);

    /**
     * @param id         idUser
     * @param authHeader token usera
     * @return void
     * @author Kuba
     */
    @PUT("/user/{user_id}/email-update")
    Call<Void> changeEmail(@Path("user_id") String id,@Header("Authorization") String authHeader, @Body ChangeEmailUser changeEmailUser);
}
