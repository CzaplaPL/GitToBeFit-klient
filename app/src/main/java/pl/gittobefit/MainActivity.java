package pl.gittobefit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.EntityUser;
import pl.gittobefit.network.Conection;

/***
 * author:Dominik
 */
public class MainActivity extends AppCompatActivity
{
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("167652090961-5dkah0ddinbeh8clnq81ieg3h2onkvjp.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        if(GoogleSignIn.getLastSignedInAccount(this)!=null)
        {
            Intent intet = new Intent(MainActivity.this,HomePage.class);
            startActivity(intet);
        }

        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Log.i("start "," sprawdzam baze ");
        List<EntityUser> user = AppDataBase.getDatabase(this).user().getUser();
        for(EntityUser it : user)
        {
            Log.i("select", it.toString());
        }
        Log.i("koniec "," sprawdzam baze ");

    }

    public void login(View view)
    {
        TextInputLayout email =(TextInputLayout)findViewById(R.id.loginMailKontener);
        TextInputLayout pass =(TextInputLayout)findViewById(R.id.loginPassKontener);
        System.conect.user.login(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),this);
    }
    public void loginSuccess()
    {
        Intent intet = new Intent(MainActivity.this,HomePage.class);
        startActivity(intet);
    }
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

    public void loginGoogle(View view)
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            account.getIdToken();
            // Signed in successfully, show authenticated UI.
            System.conect.user.loginGoogle(account.getEmail(),account.getIdToken(),this);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("logowanie google", "signInResult:failed code=" + e.getStatusCode());
            loginFail(true);
        }
    }
}