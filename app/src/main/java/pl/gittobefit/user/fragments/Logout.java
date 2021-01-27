package pl.gittobefit.user.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import pl.gittobefit.MainActivity;
import pl.gittobefit.R;
import pl.gittobefit.user.User;


public class Logout extends Fragment
{

    GoogleSignInClient mGoogleApiClient;

    public Logout()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().hide();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(String.valueOf(R.string.google_token)).requestEmail().build();
        mGoogleApiClient = GoogleSignIn.getClient(getContext(), gso);
        switch (User.getInstance().getLoggedBy()) {
            case GOOGLE:
                mGoogleApiClient.signOut();
                User.getInstance().setLoggedBy(User.WayOfLogin.DEFAULT);
                break;
            case OUR_SERVER:
                User.getInstance().setToken(null);
                User.getInstance().setLoggedBy(User.WayOfLogin.DEFAULT);
                break;
            case FACEBOOK:
                AccessToken.setCurrentAccessToken(null);
                if (LoginManager.getInstance() != null) {
                    LoginManager.getInstance().logOut();
                }
                User.getInstance().setLoggedBy(User.WayOfLogin.DEFAULT);

                break;
        }
        Navigation.findNavController(getView()).navigate(R.id.action_logout_to_login);
    }
}