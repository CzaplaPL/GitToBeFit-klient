package pl.gittobefit;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.EntityUser;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;


public class HomeFragment extends Fragment implements View.OnClickListener
{

    private Button button;


    public HomeFragment()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        button =  view.findViewById(R.id.generate);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String token;
                token =  AppDataBase.getInstance(getContext()).user().getToken(2);

               // Toast.makeText(getContext(),token, Toast.LENGTH_SHORT).show();
                //System.out.println(token);
                System.out.println(AppDataBase.getInstance(getContext()).user().getUser().isEmpty());
            }
        });

        return view;
    }
    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {

        }
    }
}