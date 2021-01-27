package pl.gittobefit.user.acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import pl.gittobefit.HomePage;
import pl.gittobefit.MainActivity;
import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.acticity.AboutUs;
import pl.gittobefit.user.User;
import pl.gittobefit.user.acticity.ChangeMailDialog;
import pl.gittobefit.user.acticity.ChangePasswordDialog;


/**
 * @author Kuba
 */
public class Setting extends AppCompatActivity implements ChangeMailDialog.DialogListener, ChangePasswordDialog.DialogListener
{
    DrawerLayout drawerLayout;
    TextView userEmailDisplay;
    private final String errMsg = "Hasło musi zawierać:\n- minimum 8 znaków\n- wielką literę\n-małą literę\n- cyfrę\n- znak specjalny";
    String passValidation = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    GoogleSignInClient mGoogleApiClient;
    TextView userEmail;
    String emailValidation = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public void applyTexts(String newEmail, String password) {

        if (newEmail.matches(emailValidation)) {
            ConnectionToServer.getInstance().userServices.changeEmail(newEmail, password, this);
        } else {
            Toast.makeText(this, "Zły format emaila :/", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void applyTexts2(String oldPassword, String newPassword) {
        if (newPassword.matches(passValidation)) {
            ConnectionToServer.getInstance().userServices.changePassword(oldPassword, newPassword, this);
        }
        else {
            Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        userEmailDisplay = findViewById(R.id.user_email_display);
        userEmailDisplay.setText(User.getInstance().getEmail());
        drawerLayout = findViewById(R.id.drawer_layout);

        userEmail = findViewById(R.id.user_email);
        userEmail.setText(User.getInstance().getEmail());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(String.valueOf(R.string.google_token)).requestEmail().build();
        mGoogleApiClient = GoogleSignIn.getClient(this, gso);
    }

    public void clickMenu(View view) {
        HomePage.openDrawer(drawerLayout);
    }

    public void clickLogo(View view) {
        HomePage.closeDrawer(drawerLayout);
    }

    public void clickHome(View view) {
        HomePage.redirectActivity(this, HomePage.class);
    }

    public void clickSetting(View view) {
        recreate();
    }

    public void clickAboutUs(View view) {
        HomePage.redirectActivity(this, AboutUs.class);
    }

    public void clickLogout(View view) {
        switch (User.getInstance().getLoggedBy()) {
            case GOOGLE:
                mGoogleApiClient.signOut();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Wylogowano z " + User.getInstance().getLoggedBy(),Toast.LENGTH_SHORT).show();
                User.getInstance().setLoggedBy(User.WayOfLogin.DEFAULT);
                break;
            case OUR_SERVER:
                HomePage.logout(this);
                break;
            case FACEBOOK:
                AccessToken.setCurrentAccessToken(null);
                if (LoginManager.getInstance() != null) {
                    LoginManager.getInstance().logOut();
                }
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Wylogowano z " + User.getInstance().getLoggedBy(), Toast.LENGTH_SHORT).show();
                User.getInstance().setLoggedBy(User.WayOfLogin.DEFAULT);
                break;
        }
    }

    public void clickChangePassword(View view)
    {
        //HomePage.redirectActivity(this, ChangePassword.class);
        if (User.getInstance().getLoggedBy() == User.WayOfLogin.OUR_SERVER)
        {
            openDialog2();
        }
        else
        {
            Toast.makeText(this, "Opcja dostępna tylko dla ekskluzywnych użytkowników GitToBeFit", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDialog2()
    {
        ChangePasswordDialog dialog = new ChangePasswordDialog();
        dialog.show(getSupportFragmentManager(), "Change password dialog");
    }

    public void clickChangeEmail(View view)
    {
        if (User.getInstance().getLoggedBy() == User.WayOfLogin.OUR_SERVER)
        {
            openDialog();
        }
        else
        {
            Toast.makeText(this, "Opcja dostępna tylko dla ekskluzywnych użytkowników GitToBeFit", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDialog()
    {
        ChangeMailDialog dialog = new ChangeMailDialog();
        dialog.show(getSupportFragmentManager(), "Change email dialog");
    }

    public void clickDeleteAccount(View view)
    {
        if (User.getInstance().getLoggedBy() == User.WayOfLogin.OUR_SERVER)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Usuń konto");
            builder.setMessage("Czy na pewno chcesz usunąć konto ?");
            builder.setPositiveButton("Tak", (dialog, which) -> {
                ConnectionToServer.getInstance().userServices.deleteAccount(this);

            });
            builder.setNegativeButton("Nie", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
        else
        {
            Toast.makeText(this, "Opcja dostępna tylko dla ekskluzywnych użytkowników GitToBeFit", Toast.LENGTH_SHORT).show();
        }

    }

    public void appVersion(View view)
    {
        Toast.makeText(this, "Version 2.0.0", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer(drawerLayout);
    }
}