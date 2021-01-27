package pl.gittobefit.user.acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
import pl.gittobefit.user.User;

public class AboutUs extends AppCompatActivity
{
    TextView userEmailDisplay;
    DrawerLayout drawerLayout;
    GoogleSignInClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        userEmailDisplay = findViewById(R.id.user_email_display);
        userEmailDisplay.setText(User.getInstance().getEmail());

        drawerLayout = findViewById(R.id.drawer_layout);

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
        HomePage.redirectActivity(this, Setting.class);
    }

    public void clickAboutUs(View view) {
        recreate();
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