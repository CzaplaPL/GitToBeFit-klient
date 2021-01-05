package pl.gittobefit;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
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

import pl.gittobefit.user.User;

/**
 * @author Kuba
 */
public class HomePage extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    TextView userEmailDisplay;
    GoogleSignInClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        userEmailDisplay = findViewById(R.id.user_email_display);

        userEmailDisplay.setText(User.getInstance().getEmail());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(String.valueOf(R.string.google_token)).requestEmail().build();
        mGoogleApiClient = GoogleSignIn.getClient(this, gso);

        if (User.getInstance().getLoggedBy() != User.WayOfLogin.DEFAULT)
        {
            OnBackPressedCallback callback = new OnBackPressedCallback(true)
            {
                @Override
                public void handleOnBackPressed() {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                }
            };
            this.getOnBackPressedDispatcher().addCallback(this, callback);
        }
    }

    public void clickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickHome(View view) {
        recreate();
    }

    public void clickSetting(View view) {
        redirectActivity(this, Setting.class);
    }

    public void clickAboutUs(View view) {
        redirectActivity(this, AboutUs.class);
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
                logout(this);
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

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Wyloguj");
        builder.setMessage("Czy na pewno chesz się wylogować ?");
        builder.setPositiveButton("Tak", (dialog, which) -> {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            Toast.makeText(activity, "Wylogowano z " + User.getInstance().getLoggedBy(), Toast.LENGTH_SHORT).show();
            User.getInstance().setToken(null);
            User.getInstance().setLoggedBy(User.WayOfLogin.DEFAULT);
        });
        builder.setNegativeButton("Nie", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public void appVersion(View view)
    {
        Toast.makeText(this, "Version 2.0.0", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
