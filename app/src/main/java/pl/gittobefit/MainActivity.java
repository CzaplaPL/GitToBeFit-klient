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
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;

import pl.gittobefit.dialog.RemindPasswoedDialog;
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

    //////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        //autologowanie facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null && !accessToken.isExpired())
        {
            ConnectionToServer.getInstance().userServices.loginFacebook(accessToken,this);
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
    /*public void login(View view)
    {
        TextInputLayout email =(TextInputLayout)findViewById(R.id.loginMailKontener);
        TextInputLayout pass =(TextInputLayout)findViewById(R.id.loginPassKontener);
        ConnectionToServer.getInstance().userServices.login(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),this);
    }*/
    /***
     * funkcja zmieniajaca activity po udanym logowaniu
     */

    /**
     * funkcja wywoływana podczas logowania przez google
     * @param view ""
     */
    public void loginGoogle(View view)
    {

    }
    /**
     * logowanie przez fb
     * @param token token otrzymany od fb
     */
    public void loginFacebook(AccessToken token)
    {
        ConnectionToServer.getInstance().userServices.loginFacebook(token,this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == 1)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else
        {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }*/
    }

    public void remindPassword(View view)
    {
        RemindPasswoedDialog dialog = new RemindPasswoedDialog();
        dialog.show(getSupportFragmentManager(),"remind password");
    }
}