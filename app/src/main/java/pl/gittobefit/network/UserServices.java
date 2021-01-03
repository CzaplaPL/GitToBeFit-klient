package pl.gittobefit.network;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import pl.gittobefit.ChangePassword;
import pl.gittobefit.HomePage;
import pl.gittobefit.MainActivity;
import pl.gittobefit.Setting;
import pl.gittobefit.System;
import pl.gittobefit.network.interfaces.IUserServices;
import pl.gittobefit.network.object.ChangePassUser;
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
    public void login(String email, String password, MainActivity main)
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
                }
                else
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

    /**
     * @author Kuba
     * @param actualPassword akualne hasło
     * @param newPassword nowe hasło
     * @param activity activty
     */
    public void changePassword(String actualPassword, String newPassword, Activity activity)
    {

        Call<Void> call = user.getUserIDbyEmail(User.getUser().getEmail(), User.getUser().getToken());
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
               String userID = response.headers().get("idUser");
               Call<Void> call2 = user.changePassword(userID, User.getUser().getToken(), new ChangePassUser(User.getUser().getEmail(), actualPassword, newPassword));
               call2.enqueue(new Callback<Void>()
               {
                   @Override
                   public void onResponse(Call<Void> call, Response<Void> response) {

                       int code = response.code();
                       if (code == 409)
                       {
                           Toast.makeText(activity, "Błędne stare hasło",Toast.LENGTH_SHORT).show();
                       } else {
                           activity.startActivity(new Intent(activity, Setting.class));
                           Toast.makeText(activity, "Zmieniono hasło !",Toast.LENGTH_SHORT).show();
                       }
                       Log.e("kod błędu", String.valueOf(code));
                   }

                   @Override
                   public void onFailure(Call<Void> call, Throwable t) {
                       Log.e(" błąd  ", "zmiana hasła : "+ t.toString());
                   }
               });

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(" błąd  ", "pobiereanie id poprzez email : "+ t.toString());
            }
        });
    }

    /**
     * @author Kuba
     * @param activity  activity
     */
    public void deleteAccount(Activity activity)
    {
        Call<Void> call = user.getUserIDbyEmail(User.getUser().getEmail(), User.getUser().getToken());
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                String userID = response.headers().get("idUser");
                Call<Void> call2 = user.deleteAccount(userID, User.getUser().getToken());
                call2.enqueue(new Callback<Void>()
                {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int code =  response.code();
                        Log.e("kod błędu", String.valueOf(code));
                        User.getUser().setToken(null);
                        HomePage.redirectActivity(activity, MainActivity.class);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(" Błąd  ", "usuwanie konta: "+ t.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(" błąd  ", "pobiereanie id poprzez email : "+ t.toString());
            }
        });
    }
}
