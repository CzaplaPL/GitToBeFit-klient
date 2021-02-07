package pl.gittobefit.network;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;

import java.util.Objects;

import pl.gittobefit.LogUtils;
import pl.gittobefit.R;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.UserEntity;
import pl.gittobefit.network.interfaces.IUserServices;
import pl.gittobefit.network.object.UserChangeEmail;
import pl.gittobefit.network.object.UserChangePass;
import pl.gittobefit.network.object.RespondUser;
import pl.gittobefit.network.object.TokenUser;
import pl.gittobefit.user.User;
import pl.gittobefit.user.Validation;
import pl.gittobefit.user.fragments.Login;
import pl.gittobefit.user.fragments.Registration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * funkcje obsługujące komunikacje z serwerem
 */
public class UserServices
{
    private final IUserServices user;

    UserServices(Retrofit adapter)
    {
        user = adapter.create(IUserServices.class);
    }

    /**
     * logowanie kontem użytkownika
     *
     * @param email    email
     * @param password hasło
     * @param fragment     activity
     * @author czapla
     */
    public void login(String email, String password, Login fragment, View view)
    {

        Log.w("Network", "      user.login");
        Log.w("Network", "   " + email + " " + password);
        //przygotowanie zapytania
        if(!email.matches( Validation.emailValidation ))
        {
            fragment.loginFail(false);
            return;
        }
        Call<Void> call = user.login(new RespondUser(email, password));
        //wywołanie zapytania
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(response.isSuccessful())
                {
                    Log.w("logowanie  ", "  get_id");
                    Call<Void> call2 = user.getUserIDbyEmail(email, response.headers().get("Authorization"));
                    //wywołanie zapytania
                    call2.enqueue(new Callback<Void>()
                    {
                        @Override
                        public void onResponse(Call<Void> call2, Response<Void> response2)
                        {
                            if(response2.isSuccessful())
                            {
                                User.getInstance().add(email, password, response.headers().get("Authorization"), response2.headers().get("idUser"), User.WayOfLogin.OUR_SERVER, fragment.getContext());
                                AppDataBase.getInstance(fragment.getContext()).user().addUser(new UserEntity (email, response.headers().get("Authorization")));
                                AppDataBase.getInstance(fragment.getContext()).user().setID(1, email);
                                fragment.loginSuccess(view);
                            }else
                            {
                                if(response2.code() != 404)
                                {
                                    Log.e("get_id  error : ", "   " + response2.code());
                                }else
                                {
                                    Log.w("get_id error : ", "    404 zły użytkownik ");
                                }
                                fragment.loginFail(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t)
                        {
                            Log.e("get_id fail ", "logowanie : " + t.toString());
                        }
                    });
                }else
                {
                    if(response.code() != 403)
                    {
                        Log.e("logowanie error : ", String.valueOf(response.code()));
                        fragment.loginFail(true);
                    }else
                    {
                        Log.w("logowanie error : ", " 403 zły użytkownik ");
                        fragment.loginFail(false);
                    }
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" logowanie error fail  ", "logowanie : " + t.toString());
            }
        });
    }
    /**
     * logowanie przez google
     *
     * @param email email
     * @param token token google
     * @param fragment  activity
     * @author czapla
     */
    public void loginGoogle(String email, String token, Login fragment, View view)
    {

        Log.w("Network", "user.logingoogle");
        Log.w("Network", email + " " + token);
        Call<Void> call = user.loginGoogle(new TokenUser(token));
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(response.isSuccessful())
                {
                    Log.w("logowanie google ", "  get_id ");


                    Call<Void> call2 = user.getUserIDbyEmail(email, response.headers().get("Authorization"));
                    call2.enqueue(new Callback<Void>()
                    {
                        @Override
                        public void onResponse(Call<Void> call2, Response<Void> response2)
                        {
                            if(response2.isSuccessful())
                            {
                                User.getInstance().add(email, response.headers().get("Authorization"), "1", User.WayOfLogin.GOOGLE, fragment.getContext());
                                fragment.loginSuccess(view);
                            }else
                            {
                                if(response2.code() != 404)
                                {
                                    Log.e("get_id error : ", String.valueOf(response2.code()));
                                }else
                                {
                                    Log.w("get_id error :  ", " 404 zły użytkownik ");
                                }
                                LogUtils.logCause(response.headers().get("Cause"));
                                fragment.loginFail(true);
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t)
                        {
                            Log.e("  get_id error  ", "logowanie google = : " + t.toString());
                            fragment.loginFail(true);
                        }
                    });
                }else
                {
                    LogUtils.logCause(response.headers().get("Cause"));
                    if(response.code() != 400)
                    {
                        Log.e("network google error : ", String.valueOf(response.code()));
                        fragment.loginFail(false);
                    }else
                    {
                        Log.w("network google error : ", " 400 zły użytkownik ");
                        fragment.loginFail(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" google error   ", "  logowanie google = : " + t.toString());
                fragment.loginFail(true);
            }
        });
    }

    /**
     * logowanie przez facebooka
     *
     * @param token token google
     * @param fragment  activity
     * @author czapla
     */
    public void loginFacebook(AccessToken token, Login fragment, View view)
    {

        Log.w("Network", "user.loginfacebook");
        Log.w("Network", token.getToken());
        //zapytanie fb o email
        Call<Void> call = user.loginFacebook(new TokenUser(token.getToken()));
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(response.isSuccessful())
                {
                    Log.d("logowanie fb ", "zalogowano");


                    User.getInstance().add(response.headers().get("email"), response.headers().get("Authorization"), response.headers().get("idUser"), User.WayOfLogin.FACEBOOK, fragment.getContext());
                    fragment.loginSuccess(view);
                }else
                {
                    if(response.code() != 400)
                    {
                        Log.e("fb error : ", String.valueOf(response.code()));
                        fragment.loginFail(true);
                    }else
                    {
                        Log.w("fb  ", " 400 zły użytkownik ");
                        fragment.loginFail(false);
                    }

                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  ", "logowanie facebook : " + t.toString());
            }
        });

    }


    /**
     * @param actualPassword akualne hasło
     * @param newPassword    nowe hasło
     * @author Kuba
     */
    public void changePassword(String actualPassword, String newPassword, Context context)
    {

        Call<Void> call = user.getUserIDbyEmail(User.getInstance().getEmail(), User.getInstance().getToken());
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                String userID = response.headers().get("idUser");
                Call<Void> call2 = user.changePassword(userID, User.getInstance().getToken(), new UserChangePass (User.getInstance().getEmail(), actualPassword, newPassword));
                call2.enqueue(new Callback<Void>()
                {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response)
                    {

                        int code = response.code();
                        if(code == 409)
                        {
                            Toast.makeText(context, "Błędne stare hasło", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(context, "Zmieniono hasło !", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("kod błędu", String.valueOf(code));
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t)
                    {
                        Log.e(" błąd  ", "zmiana hasła : " + t.toString());
                    }
                });

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  ", "pobiereanie id poprzez email : " + t.toString());
            }
        });
    }

    /**
     * @author Kuba
     */
    public void deleteAccount()
    {
        Call<Void> call = user.getUserIDbyEmail(User.getInstance().getEmail(), User.getInstance().getToken());
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                String userID = response.headers().get("idUser");
                Call<Void> call2 = user.deleteAccount(userID, User.getInstance().getToken());
                call2.enqueue(new Callback<Void>()
                {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response)
                    {
                        int code = response.code();
                        Log.e("kod błędu", String.valueOf(code));
                        User.getInstance().setToken(null);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t)
                    {
                        Log.e(" Błąd  ", "usuwanie konta: " + t.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  ", "pobiereanie id poprzez email : " + t.toString());
            }
        });
    }

    public void changeEmail(String newEmail, String password, Context context)
    {
        Call<Void> call = user.getUserIDbyEmail(User.getInstance().getEmail(), User.getInstance().getToken());
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                String userID = response.headers().get("idUser");
                Call<Void> call2 = user.changeEmail(userID, User.getInstance().getToken(), new UserChangeEmail (newEmail, password));
                call2.enqueue(new Callback<Void>()
                {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response)
                    {
                        int code = response.code();
                        Log.e("kod błędu", String.valueOf(code));
                        if(code == 409)
                        {
                            Toast.makeText(context, "Błędne stare hasło", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            User.getInstance().setEmail(newEmail);
                            Toast.makeText(context, "Zmieniono email !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t)
                    {
                        Log.e(" Błąd  ", "zmiana emaila: " + t.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  ", "pobiereanie id poprzez email : " + t.toString());
            }
        });
    }

    /***
     * przypomnienie hasła
     * @param email email
     * @param context context do toast
     */
    public void remindPassword(String email, Context context)
    {
        Log.d("network  ", "przypominanie hasła");

        Call<Void> call = user.remindPass(email);
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(response.isSuccessful())
                {
                    Log.d("przypominanie hasła ", "sukces");
                    Toast.makeText(context,context.getResources().getString(R.string.sendPassword),Toast.LENGTH_SHORT).show();
                }else
                {
                    if(response.code() != 404)
                    {
                        Toast.makeText(context,context.getResources().getString(R.string.serwerError),Toast.LENGTH_SHORT).show();
                        Log.e("przypominanie hasła  : ", String.valueOf(response.code()));

                    }else
                    {
                        Log.w("przypominanie hasła   ", " 404 zły użytkownik ");
                        Toast.makeText(context,context.getResources().getString(R.string.sendPasswordError),Toast.LENGTH_SHORT).show();
                    }
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" błąd  hasła ", "przypominanie hasła " + t.toString());
            }
        });
    }

    /**
     *
     * @param email email
     * @param password hasło
     * @param fragment fragment do sukcesu/fail
     * @param view view do sukcesu
     */
    public void singup(String email, String password, Registration fragment, View view)
    {

        Log.w("Network", "      rejestracja");
        Log.w("Network", "   " + email + " " + password);
        //przygotowanie zapytania zapytania


        Call<Void> call = user.signup(new RespondUser(email, password));
        //wywołanie zapytania
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(response.isSuccessful())
                {
                    Log.w("Rejestracja  ", "  sukces");
                    fragment.Success(view);
                }else
                {
                    if(response.code() != 409)
                    {
                        Log.e("Rejestracja error : ", String.valueOf(response.code()));
                        fragment.Fail(false);
                    }else
                    {
                        fragment.Fail(Objects.equals(response.headers().get("Cause"), "duplicate entry"));
                        Log.w("Rejestracja error : ", " 409  =  " + response.headers().get("Cause"));
                    }
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" FRejestracja error   ",  t.toString());
                fragment.Fail(false);
            }
        });
    }

    public void verify(Login fragment)
    {
        Call<Void> call = user.verify(AppDataBase.getInstance(fragment.getContext()).user().getToken(1));

        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.w("Autologwanie  ", "  sukces");
                System.out.println("Kod zwracany przez autoLog: " + response.code());
                //System.out.println("otrzymany token: " + response.headers().get("Authorization"));
                AppDataBase.getInstance(fragment.getContext()).user().setToken(response.headers().get("Authorization"),1);
                String email = AppDataBase.getInstance(fragment.getContext()).user().getEmail(1);
                User.getInstance().add(email, "password", response.headers().get("Authorization"), "id", User.WayOfLogin.OUR_SERVER, fragment.getContext());
                fragment.loginSuccess(fragment.getView());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(" Autologowanie error   ",  t.toString());
            }
        });
    }
}
