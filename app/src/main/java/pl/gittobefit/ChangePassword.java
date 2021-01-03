package pl.gittobefit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import pl.gittobefit.network.ConnectionToServer;

/**
 * @author Kuba
 */
public class ChangePassword extends AppCompatActivity
{
    private final String errMsg = "Hasło musi zawierać:\n- minimum 8 znaków\n- wielką literę\n-małą literę\n- cyfrę\n- znak specjalny";
    TextView currentPass;
    TextView newPass;
    Button changePassBtn;
    String passValidation = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        changePassBtn = findViewById(R.id.changeBtn);
        activity = this;


        changePassBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String actualPassword = currentPass.getText().toString();
                String newPassword = newPass.getText().toString();
                if (newPassword.matches(passValidation)) {
                    ConnectionToServer.getConect().userServices.changePassword(actualPassword, newPassword, activity);
                }
                else {
                    Toast.makeText(ChangePassword.this, errMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}