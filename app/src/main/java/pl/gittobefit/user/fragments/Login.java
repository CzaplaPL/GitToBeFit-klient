package pl.gittobefit.user.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;


public class Login extends Fragment implements View.OnClickListener
{
    // logowanie google
    GoogleSignInClient mGoogleSignInClient;
    // logowania przez fb
    CallbackManager callbackManager = CallbackManager.Factory.create();
    Button facebookButton;

    public Login()
    {

    }



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
        Button button =  view.findViewById(R.id.loginZaloguj);
        button.setOnClickListener(this);
        button =  view.findViewById(R.id.loginGoogle);
        button.setOnClickListener(this);
        button =  view.findViewById(R.id.loginRegister);
        button.setOnClickListener(this);
        TextView textview =view.findViewById(R.id.loginForgotPass);
        textview.setOnClickListener(this);







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

        //autologowanie facebook
       AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null && !accessToken.isExpired())
        {
            ConnectionToServer.getInstance().userServices.loginFacebook(accessToken,this,getView());
        }




        //Logowanie google
        GoogleLogin();
        //automatyczne logowanie google
        if(GoogleSignIn.getLastSignedInAccount(getContext())!=null)
        {
            Log.w("auto login google =" , "     login " );
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
            ConnectionToServer.getInstance().userServices.loginGoogle(account.getEmail(),account.getIdToken(),this,view);
        }

        return view;
    }

    /***
     * funkcja zmieniajaca activity po udanym logowaniu
     */
    public void loginSuccess(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_login2_to_homeFragment);
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
        email.setError(getResources().getString(R.string.incorrectData));
        pass.setError(getResources().getString(R.string.incorrectData));
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.loginZaloguj:
                TextInputLayout email =(TextInputLayout)getView().findViewById(R.id.loginMailKontener);
                TextInputLayout pass =(TextInputLayout)getView().findViewById(R.id.loginPassKontener);
                ConnectionToServer.getInstance().userServices.login(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),this,view);
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


    public void loginFacebook(AccessToken token)
    {
        ConnectionToServer.getInstance().userServices.loginFacebook(token,this,getView());

    }





    private void GoogleLogin()
    {
        //logowanie google
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
            ConnectionToServer.getInstance().userServices.loginGoogle(account.getEmail(),account.getIdToken(),this,getView());
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