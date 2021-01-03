package pl.gittobefit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;

/***
 * author:Dominik
 */
public class MainActivity extends AppCompatActivity
{
    // logowania przez fb
    CallbackManager callbackManager = CallbackManager.Factory.create();
    Button facebookButton;
    /////////
    // logowanie google
    GoogleSignInClient mGoogleSignInClient;
    //////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//przęjęcie buttona żeby po wylogowaniu nie wrócić do apki
        if (User.getUser().getToken() == null)
        {
            OnBackPressedCallback callback = new OnBackPressedCallback(true)
            {
                @Override
                public void handleOnBackPressed() {
                    Toast.makeText(MainActivity.this, "Musisz się zalogować", Toast.LENGTH_SHORT).show();
                }
            };
            this.getOnBackPressedDispatcher().addCallback(this, callback);
        }


        //logowanie facebook
        facebookButton = (Button) findViewById(R.id.loginButtonFacebook);
        LoginButton fbButton = (LoginButton) findViewById(R.id.loginFacebook);
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
        ////////////////
        //logowanie google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("167652090961-5dkah0ddinbeh8clnq81ieg3h2onkvjp.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //automatyczne logowanie google
        if(GoogleSignIn.getLastSignedInAccount(this)!=null)
        {
            Log.w("auto login google =" , "     login " );
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            ConnectionToServer.getConect().userServices.loginGoogle(account.getEmail(),account.getIdToken(),this);
        }
        //autologowanie facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null && !accessToken.isExpired())
        {
            ConnectionToServer.getConect().userServices.loginFacebook(accessToken,this);
        }
        //chowanie actionBara
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

    }
    /***
     * funkcja obługująca przycisk zalogouj
     * @param view ""
     */
    public void login(View view)
    {
        TextInputLayout email =(TextInputLayout)findViewById(R.id.loginMailKontener);
        TextInputLayout pass =(TextInputLayout)findViewById(R.id.loginPassKontener);
        ConnectionToServer.getConect().userServices.login(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),this);
    }
    /***
     * funkcja zmieniajaca activity po udanym logowaniu
     */
    public void loginSuccess()
    {
        Intent intet = new Intent(MainActivity.this,HomePage.class);
        startActivity(intet);
    }
    /**
     * wyswietla komunikat o nieudamym logowaniu
     * @param error informuje czy błąd spowodowany jest błędem
     */
    public void loginFail(boolean error)
    {
        TextInputLayout email =(TextInputLayout)findViewById(R.id.loginMailKontener);
        TextInputLayout pass =(TextInputLayout)findViewById(R.id.loginPassKontener);
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
    /**
     * funkcja wywoływana podczas logowania przez google
     * @param view ""
     */
    public void loginGoogle(View view)
    {
        Log.w("logowanie google = ", "         uruchamianie ");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }
    /**
     * logowanie przez fb
     * @param token token otrzymany od fb
     */
    public void loginFacebook(AccessToken token)
    {
        ConnectionToServer.getConect().userServices.loginFacebook(token,this);

    }
    /**
     * funkcja obługująca informacje otrzymane przez google
     * @param completedTask ""
     */
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            Log.w("logowanie google = ", "     ok");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            ConnectionToServer.getConect().userServices.loginGoogle(account.getEmail(),account.getIdToken(),this);
        } catch (ApiException e) {
            Log.w("logowanie google", "signInResult:failed code=" + e.getStatusCode());
            loginFail(true);
        }
    }
    /**
     * funkcja obsługująca odpowiedz od zewnetrznego api
     * @param requestCode ""
     * @param resultCode ""
     * @param data ""
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
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