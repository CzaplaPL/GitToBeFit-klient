package pl.gittobefit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.dialog.ChangeMailDialog;

public class HomeFragment extends Fragment implements View.OnClickListener
{
    public interface HideKeyboardInterface
    {
        void hideKey(Context context, View view);
    }

    public HomeFragment() { }
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
        Button button2 = view.findViewById(R.id.button3);
        button2.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(!ConnectionToServer.isNetwork(getContext()))
        {
            Snackbar.make(getView(),getString(R.string.noNetwork),Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.generate:
               Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_generateTraining);
                break;
            case R.id.button3:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_displayReceivedTraining);
                break;

            //case R.id.button2:
                //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_trainingStart);
        }
    }
}