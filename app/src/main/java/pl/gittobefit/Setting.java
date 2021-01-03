package pl.gittobefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;


/**
 * @author Kuba
 */
public class Setting extends AppCompatActivity
{
    DrawerLayout drawerLayout;
    TextView userEmailDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        }

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
        recreate();
    }

    public void clickAboutUs(View view) {
        HomePage.redirectActivity(this, AboutUs.class);
    }

    public void clickLogout(View view) {
        HomePage.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer(drawerLayout);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat
    {

        String emailValidation = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            //change email
            EditTextPreference editTextPreference = findPreference(getString(R.string.email));
            editTextPreference.setText(User.getUser().getEmail());
            editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    if (newValue.toString().matches(emailValidation)) {
                        Toast.makeText(getContext(),newValue.toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        Toast.makeText(getContext(),"Zły format emaila :/", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            });

            //button change password
            Preference changePassButton = findPreference(getString(R.string.change));
            changePassButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    HomePage.redirectActivity(getActivity(), ChangePassword.class);
                    return true;
                }
            });

            //delete acc
            Preference deleteButton = findPreference(getString(R.string.delete));
            deleteButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Usuń konto");
                    builder.setMessage("Czy na pewno chcesz usunąć konto ?");
                    builder.setPositiveButton("Tak", (dialog, which) -> {
                        ConnectionToServer.getConect().userServices.deleteAccount(getActivity());

                    });
                    builder.setNegativeButton("Nie", (dialog, which) -> dialog.dismiss());
                    builder.show();
                    return true;
                }
            });
        }
    }

}