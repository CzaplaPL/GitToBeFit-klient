package pl.gittobefit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class HomeFragment extends Fragment implements View.OnClickListener
{

    //fdasda
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

       Button button =  view.findViewById(R.id.generate);
        button.setOnClickListener(this);




        return view;
    }
    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.generate:
               Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_generateTraining);
                break;
        }
    }
}