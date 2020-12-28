package pl.gittobefit.network;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import pl.gittobefit.MainActivity;
import pl.gittobefit.System;
import pl.gittobefit.network.interfaces.IUserServices;
import pl.gittobefit.network.object.RespondUser;
import pl.gittobefit.network.object.TokenUser;
import pl.gittobefit.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * funkcje obsługujące komunikacje z serwerem
 */
public class UserServices
{
    private final IUserServices user ;
    UserServices(Retrofit adapter)
    {
        user =adapter.create(IUserServices.class);
    }

    /**
     * logowanie kontem użytkownika
     * @param email email
     * @param password hasło
     * @param main activity
     * @author czapla
     */
    public void login(String email, String password,MainActivity main)
    {

        Log.i("Network", "user.login");
        Log.i("Network", email + " " + password);
        //przygotowanie zapytania zapytania
        Call<Void> call = user.login(new RespondUser(email,password));
        //wywołanie zapytania
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    Log.i("logowanie ","  sukces");
                    ///////////////////////////
                    //////////////////////////
                    //pytanie o id
                    ///////////////////////
                    ////////////////////////
                    User.getUser().add(email,password,response.headers().get("Authorization"),"1",main.getApplicationContext());
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

    /**
     * logowanie przez google
     * @param email email
     * @param token token google
     * @param main activity
     * @author czapla
     */
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
                    Log.i("logowanie google ","  sukces " );
                    ///////////////////////////
                    //////////////////////////
                    //pytanie o id
                    ///////////////////////
                    ////////////////////////
                    User.getUser().add(email,response.headers().get("Authorization"),"1",main.getApplicationContext());
                    main.loginSuccess();
                }else
                {
                    if(response.code()!=400)
                    {
                        Log.e("response body error : ", String.valueOf(response.code()));
                        main.loginFail(false);
                    }else
                    {
                        Log.i("response body : "," 400 zły użytkownik ");
                        main.loginFail(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  ", "logowanie google = : "+t.toString());
                main.loginFail(true);
            }
        });
    }

    /**
     * logowanie przez facebooka
     * @param token token google
     * @param main activity
     * @author czapla
     */
    public void loginFacebook(AccessToken token, MainActivity main)
    {

        Log.i("Network", "user.loginfacebook");
        Log.i("Network",  token.getToken());
        //zapytanie fb o email
        GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject me, GraphResponse response) {
                if (response.getError() != null)
                {
                    Log.i("fb pytanie o email  " , " error " );
                    Log.i("fb pytanie o email  " , response.getError().getErrorMessage() );
                } else
                {
                    String email = me.optString("email");
                    Call<Void> call = user.loginFacebook(new TokenUser(token.getToken()));
                    call.enqueue(new Callback<Void>()
                    {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful())
                            {
                                Log.i("logowanie fb ","  sukces ");
                                ///////////////////////////
                                //////////////////////////
                                //pytanie o id
                                ///////////////////////
                                ////////////////////////
                                User.getUser().add(email,response.headers().get("Authorization"),"1",main.getApplicationContext());
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
                            Log.e(" błąd  ", "logowanie facebook : "+t.toString());
                        }
                    });

                }
            }
        }).executeAsync();
    }


}
