package pl.gittobefit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import pl.gittobefit.user.User;

/***
 * author:Dominik
 */
public class MainActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    //////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar myToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(myToolbar);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.setting,R.id.aboutUs, R.id.homeFragment,R.id.generateTrainingForm)
                .setOpenableLayout(drawerLayout)
                .build();



        ////////////////


        navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       NavigationUI.setupWithNavController(navigationView, navController);


        //chowanie actionBara

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment fragment =  navHostFragment.getChildFragmentManager().getFragments().get(0);
        fragment.onActivityResult(requestCode, resultCode, data);

    }

    public void openDrawer(DrawerLayout drawerLayout)
    {
        TextView emailText = navigationView.getHeaderView(0).findViewById(R.id.user_email_display);
        if(User.getInstance().getLoggedBy()== User.WayOfLogin.DEFAULT)
        {
            navigationView.getMenu().getItem(3).setTitle(getString(R.string.login));
            emailText.setText("");
        }else
        {
            navigationView.getMenu().getItem(3).setTitle(getString(R.string.logout));
            emailText.setText(User.getInstance().getEmail());
        }
        if(User.getInstance().getLoggedBy()!= User.WayOfLogin.OUR_SERVER)  navigationView.getMenu().getItem(1).setEnabled(false);
        drawerLayout.openDrawer(GravityCompat.START);

    }

}