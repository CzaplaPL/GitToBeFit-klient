package pl.gittobefit.user.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Collections;

import pl.gittobefit.HomeFragment;
import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;

/**
 * fragment logowania
 */
public class Login extends Fragment implements View.OnClickListener
{
    // logowanie google
    GoogleSignInClient mGoogleSignInClient;
    // logowania przez fb
    CallbackManager callbackManager = CallbackManager.Factory.create();
    Button facebookButton;


    public Login() { }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        TextInputLayout email= view.findViewById(R.id.loginMailKontener);
        TextInputEditText email2= view.findViewById(R.id.loginMail);
        email2.setOnFocusChangeListener((v, hasFocus) -> email.setErrorEnabled(false));

        TextInputLayout password= view.findViewById(R.id.loginPassKontener);
        TextInputEditText password2= view.findViewById(R.id.loginMail);
        password2.setOnFocusChangeListener((v, hasFocus) -> password.setErrorEnabled(false));
        Button button =  view.findViewById(R.id.loginZaloguj);
        button.setOnClickListener(this);
        button =  view.findViewById(R.id.loginGoogle);
        button.setOnClickListener(this);
        button =  view.findViewById(R.id.loginRegister);
        button.setOnClickListener(this);
        button =  view.findViewById(R.id.loginSkip);
        button.setOnClickListener(this);
        TextView textview =view.findViewById(R.id.loginForgotPass);
        textview.setOnClickListener(this);
        //Logowanie facebook
        FacebookeLogin(view);
        //autologowanie facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null && !accessToken.isExpired())
        {
            ConnectionToServer.getInstance().userServices.loginFacebook(accessToken,this);
        }
        //Logowanie google
        GoogleLogin();

        //automatyczne logowanie nasz server
        if(!AppDataBase.getInstance(getContext()).user().getUser().isEmpty())
        {
            ConnectionToServer.getInstance().userServices.verify(this);
        }
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //sprawdzanie czy jest polączenie z internetem
        if(!ConnectionToServer.isNetwork(getContext()))
        {
            Navigation.findNavController(getView()).navigate(LoginDirections.actionLogin2ToHomeFragment());
        }
    }
    /***
     * funkcja zmieniajaca fragment po udanym logowaniu
     */
    public void loginSuccess()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
               try
                {
                    User.getInstance().setSynchroniseTraining(User.SynchroniseTraining.Start_Synchronise);
                    ConnectionToServer.getInstance().trainingServices.synchronisedTraining(getContext());
                }catch(Exception e)
                {
                    User.getInstance().setSynchroniseTraining(User.SynchroniseTraining.Synchronise_error);
                    Log.e("Network", "Trainings.synchronisedTraining error "+ e.toString());
                }
            }
        }).start();
        Navigation.findNavController(getView()).navigate(LoginDirections.actionLogin2ToHomeFragment());
    }
    /**
     * wyswietla komunikat o nieudamym logowaniu
     * @param error informuje czy błąd spowodowany jest błędem
     */
    public void loginFail(boolean error)
    {
        TextInputLayout email = getView().findViewById(R.id.loginMailKontener);
        TextInputLayout pass = getView().findViewById(R.id.loginPassKontener);
        email.setErrorEnabled(true);
        pass.setErrorEnabled(true);
        if (error)
        {
            email.setError(getResources().getString(R.string.serwerError));
            pass.setError(getResources().getString(R.string.serwerError));
        }
        email.setError(getResources().getString(R.string.notLogin));
        pass.setError(getResources().getString(R.string.notLogin));
    }
    public void loginFail(boolean wrongPassword,String info)
    {
        TextInputLayout email = getView().findViewById(R.id.loginMailKontener);
        TextInputLayout pass = getView().findViewById(R.id.loginPassKontener);
        email.setErrorEnabled(true);
        pass.setErrorEnabled(true);
        if (wrongPassword)
        {
            pass.setError(getResources().getString(R.string.incorrectpassword));
        }
        email.setError(info);
        pass.setError(info);
    }

    /**
     * obsługa klikniecia
     * @param view element klikniety
     */
    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.loginZaloguj:
                TextInputLayout email =(TextInputLayout)getView().findViewById(R.id.loginMailKontener);
                TextInputLayout pass =(TextInputLayout)getView().findViewById(R.id.loginPassKontener);
                IShowSnackbar activity = (IShowSnackbar) getActivity();
                ConnectionToServer.getInstance().userServices.login(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),this,activity);
                HomeFragment.HideKeyboardInterface hideKeyboard = (HomeFragment.HideKeyboardInterface) getActivity();
                hideKeyboard.hideKey(getContext(),getView());
                break;
            case R.id.loginSkip:
             Navigation.findNavController(view).navigate(LoginDirections.actionLogin2ToHomeFragment());
                break;
            case R.id.loginGoogle:
                Log.w("logowanie google = ", "         uruchamianie ");
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
                break;
            case R.id.loginForgotPass:
                Navigation.findNavController(view).navigate(R.id.action_login_to_remindPasswordDialog);
                break;
            case R.id.loginRegister:

                Navigation.findNavController(view).navigate(R.id.action_login_to_registration);
                break;
        }
    }

    /** logowanie facebook*/
    public void loginFacebook(AccessToken token)
    {
        ConnectionToServer.getInstance().userServices.loginFacebook(token,this);
    }

    private void FacebookeLogin(View view)
    {
        //logowanie facebook
        facebookButton = (Button) view.findViewById(R.id.loginButtonFacebook);
        LoginButton fbButton = (LoginButton) view.findViewById(R.id.loginFacebook);
        facebookButton.setOnClickListener(v -> fbButton.performClick());
        fbButton.setPermissions(Collections.singletonList("email"));
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Log.i("login facebook =" , "sukces " );
                loginFacebook(loginResult.getAccessToken());
            }
            @Override
            public void onCancel()
            {
                Log.w("login facebook Main = " , "   cancel " );
            }
            @Override
            public void onError(FacebookException exception)
            {
                Log.w("login facebook Main  =" , "  błąd " );
                Log.w("login facebook Main  =" , "       " +exception.toString() );
            }
        });

    }
    /** logowanie google*/
    private void GoogleLogin()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("167652090961-5dkah0ddinbeh8clnq81ieg3h2onkvjp.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            Log.w("logowanie google = ", "     ok");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            ConnectionToServer.getInstance().userServices.loginGoogle(account.getEmail(),account.getIdToken(),this);
        } catch (ApiException e) {
            Log.w("logowanie google", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.w("odpowiedz "," jest");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else
        {
           callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}