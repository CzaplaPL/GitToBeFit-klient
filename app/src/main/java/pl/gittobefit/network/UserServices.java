package pl.gittobefit.network;

import android.util.Log;

import pl.gittobefit.MainActivity;
import pl.gittobefit.System;
import pl.gittobefit.network.interfaces.IUserServices;
import pl.gittobefit.network.object.RespondUser;
import pl.gittobefit.network.object.TokenUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserServices
{

    private final IUserServices user ;
    UserServices(Retrofit adapter)
    {
        user =adapter.create(IUserServices.class);
    }

    public void login(String email, String password,MainActivity main)
    {

        Log.i("Network", "user.login");
        Log.i("Network", email + " " + password);
        Call<Void> call = user.login(new RespondUser(email,password));
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    Log.i("logowanie ","  sukces");
                    //pytanie o id
                    System.user.init(email,password,response.headers().get("Authorization"),"1",main.getApplicationContext());
                    main.loginSuccess();
                }else
                {
                    if(response.code()!=403)
                    {
                        Log.e("response body error : ", String.valueOf(response.code()));
                        main.loginFail(true);
                    }else
                    {
                        Log.i("response body : "," 403 zły użytkownik ");
                        main.loginFail(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  ", "logowanie : "+t.toString());
            }
        });
    }

    public void loginGoogle(String email,String token,MainActivity main)
    {

        Log.i("Network", "user.logingoogle");
        Log.i("Network",  email+" "+token);
        Call<Void> call = user.loginGoogle(new TokenUser(token));
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    Log.i("logowanie google ","  sukces " + response.headers().get("Authorization"));
                    //pytanie o id
                    System.user.initGoogle(email,response.headers().get("Authorization"),"1",main.getApplicationContext());
                    main.loginSuccess();
                }else
                {
                    if(response.code()!=400)
                    {
                        Log.e("response body error : ", String.valueOf(response.code()));
                        main.loginFail(true);
                    }else
                    {
                        Log.i("response body : "," 400 zły użytkownik ");
                        main.loginFail(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  ", "logowanie : "+t.toString());
            }
        });
    }
}
