package pl.gittobefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.gittobefit.user.User;

/**
 * @author Kuba
 */
public class HomePage extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    TextView userEmailDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        drawerLayout = findViewById(R.id.drawer_layout);
        userEmailDisplay = findViewById(R.id.user_email_display);
        userEmailDisplay.setText(User.getUser().getEmail());
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

    public static void  closeDrawer(DrawerLayout drawerLayout) {
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
        logout(this);
    }

    public static void logout(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Wyloguj");
        builder.setMessage("Czy na pewno chesz się wylogować ?");
        builder.setPositiveButton("Tak", (dialog, which) -> {
            User.getUser().setToken(null);
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        });
        builder.setNegativeButton("Nie", (dialog, which) -> dialog.dismiss());
        builder.show();
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
