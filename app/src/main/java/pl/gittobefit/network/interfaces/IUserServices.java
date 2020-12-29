package pl.gittobefit.network.interfaces;

import pl.gittobefit.network.object.RespondUser;
import pl.gittobefit.network.object.TokenUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * interfejs(dao) do komunikacji sieciowej o użytkowniku
 */
public interface IUserServices
{
    /**
     *logowanie kontem aplikacji
     * @param user RespondUser
     * @return void
     * @author czapla
     */
    @POST("/user/login")
Call<Void> login(@Body RespondUser user);

    /**
     * logowanie kontem google
     * @param token token otrzymany przez google
     * @return void
     * @author czapla
     */
    @POST("/user/login/google")
Call<Void> loginGoogle(@Body TokenUser token);

    /**
     *logowanie kontem facebook
     * @param token token otrzymany przez fb
     * @return void
     * @author czapla
     */
    @POST("/user/login/facebook")
Call<Void> loginFacebook(@Body TokenUser token);

}
