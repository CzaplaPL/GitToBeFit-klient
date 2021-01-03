package pl.gittobefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.gittobefit.user.User;

public class AboutUs extends AppCompatActivity
{
    TextView userEmailDisplay;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        userEmailDisplay = findViewById(R.id.user_email_display);
        userEmailDisplay.setText(User.getUser().getEmail());

        drawerLayout = findViewById(R.id.drawer_layout);
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
        HomePage.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer(drawerLayout);
    }
}