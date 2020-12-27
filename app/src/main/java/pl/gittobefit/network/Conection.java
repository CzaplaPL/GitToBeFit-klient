package pl.gittobefit.network;

import pl.gittobefit.user.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conection
{

    private final Retrofit restAdapter ;
    public final UserServices user;
    public Conection(String url)
    {
        restAdapter = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        user = new UserServices(restAdapter);
    }



}
