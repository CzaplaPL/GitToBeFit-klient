package pl.gittobefit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import pl.gittobefit.network.ConnectionToServer;


public class HomeFragment extends Fragment implements View.OnClickListener
{


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
        Button button =  view.findViewById(R.id.button7);
        button.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.button7:

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_options);
                break;


        }
    }
}