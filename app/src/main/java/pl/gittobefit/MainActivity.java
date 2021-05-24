package pl.gittobefit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;

import pl.gittobefit.user.User;
import pl.gittobefit.user.dialog.ChangeMailDialog;
import pl.gittobefit.user.dialog.DeleteAccountDialog;

/***
 * author:Dominik
 */
public class MainActivity extends AppCompatActivity implements ChangeMailDialog.ChangeMailDialogInterface, DeleteAccountDialog.DeleteAccountDialogInterface, IShowSnackbar, HomeFragment.HideKeyboardInterface
{
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    //////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar myToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(v -> openDrawer(drawerLayout));
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.setting,
                R.id.aboutUs,
                R.id.homeFragment,
                R.id.regulations,
                R.id.displayReceivedTraining,
                R.id.listOfTrainings,
                R.id.generateTrainingForm,
                R.id.exerciseDetails,
                R.id.change_exercise
        ).setOpenableLayout(drawerLayout).build();

        navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //chowanie actionBar

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void openDrawer(DrawerLayout drawerLayout) {
        TextView emailText = navigationView.getHeaderView(0).findViewById(R.id.user_email_display);
        if (User.getInstance().getLoggedBy() == User.WayOfLogin.NO_LOGIN) {
            navigationView.getMenu().getItem(4).setTitle(getString(R.string.login));
            emailText.setText("");
        } else {
            navigationView.getMenu().getItem(4).setTitle(getString(R.string.logout));
            emailText.setText(User.getInstance().getEmail());
        }
        navigationView.getMenu().getItem(1).setEnabled(User.getInstance().getLoggedBy() == User.WayOfLogin.OUR_SERVER);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onChangeMail(Boolean sukces, String message) {
        if (sukces) {
            Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.action_global_logout);
        }
        Snackbar.make(findViewById(R.id.nav_host_fragment), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.nav_host_fragment), message, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onAccountDelete(Boolean success, String message) {
        if (success) {
            Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.action_global_logout);
        }
        Snackbar.make(findViewById(R.id.nav_host_fragment), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideKey(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
}