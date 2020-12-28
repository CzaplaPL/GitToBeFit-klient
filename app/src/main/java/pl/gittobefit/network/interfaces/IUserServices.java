package pl.gittobefit.network.interfaces;

import pl.gittobefit.network.object.RespondUser;
import pl.gittobefit.network.object.TokenUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserServices
{
@POST("/user/login")
Call<Void> login(@Body RespondUser user);

@POST("/user/login/google")
Call<Void> loginGoogle(@Body TokenUser token);

@POST("/user/login/facebook")
Call<Void> loginFacebook(@Body TokenUser token);
}
