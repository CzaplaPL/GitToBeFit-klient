package pl.gittobefit.user.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;

/**
 * fragment ustawien
 */
public class Settings extends Fragment implements View.OnClickListener
{
    public Settings()
    {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_settings, container, false);
        TextView userEmail = view.findViewById(R.id.user_email);
        userEmail.setText(User.getInstance().getEmail());
        Button button =  view.findViewById(R.id.change_email);
        button.setOnClickListener(this);
        button =  view.findViewById(R.id.change_password);
        button.setOnClickListener(this);
        button =  view.findViewById(R.id.delete_account);
        button.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.change_email:
                if (User.getInstance().getLoggedBy() == User.WayOfLogin.OUR_SERVER)
                {
                    Navigation.findNavController(view).navigate(R.id.action_setting_to_changeMailDialog);
                }
                else
                {
                    Toast.makeText(getContext(), "Opcja dostępna tylko dla ekskluzywnych użytkowników GitToBeFit", Toast.LENGTH_SHORT).show();
                }
             break;
            case R.id.change_password:
                if (User.getInstance().getLoggedBy() == User.WayOfLogin.OUR_SERVER)
                {
                    Navigation.findNavController(view).navigate(R.id.action_setting_to_changePasswordDialog);
                }
                else
                {
                    Toast.makeText(getContext(), "Opcja dostępna tylko dla ekskluzywnych użytkowników GitToBeFit", Toast.LENGTH_SHORT).show();
                }
             break;
            case R.id.delete_account:
                if (User.getInstance().getLoggedBy() == User.WayOfLogin.OUR_SERVER)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Usuń konto");
                    builder.setMessage("Czy na pewno chcesz usunąć konto ?");
                    builder.setPositiveButton("Tak", (dialog, which) -> {
                        ConnectionToServer.getInstance().userServices.deleteAccount();
                        Navigation.findNavController(view).navigate(R.id.action_setting_to_login);
                    });
                    builder.setNegativeButton("Nie", (dialog, which) -> dialog.dismiss());
                    builder.show();

                }
                else
                {
                    Toast.makeText(getContext(), "Opcja dostępna tylko dla ekskluzywnych użytkowników GitToBeFit", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}