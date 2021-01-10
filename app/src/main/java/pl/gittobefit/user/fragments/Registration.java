package pl.gittobefit.user.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.Executor;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;


public class Registration extends Fragment implements View.OnClickListener
{

    String passValidation = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    String emailValidation = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public Registration()
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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        CheckBox checkBox =  view.findViewById(R.id.checkBox_robot);
        checkBox.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonRegistr:
                TextInputLayout email =(TextInputLayout)getView().findViewById(R.id.loginMailKontener);
                TextInputLayout pass =(TextInputLayout)getView().findViewById(R.id.loginPasswordKontener);
                TextInputLayout pass2 =(TextInputLayout)getView().findViewById(R.id.loginRewersePasswordKontener);
                CheckBox robot =  view.findViewById(R.id.checkBox_robot);
                CheckBox terms =  view.findViewById(R.id.checkBox_statute);



                break;
            case R.id.checkBox_robot:
                CheckBox checkBox =  view.findViewById(R.id.checkBox_robot);
                SafetyNet.getClient(getActivity()).verifyWithRecaptcha("6LdH0ScaAAAAAOAnxd_zMOzmbco0_VRrazkQvdUQ")
                        .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse)
                            {
                                String userResponseToken = recaptchaTokenResponse.getTokenResult();
                                if (!userResponseToken.isEmpty())
                                {
                                    checkBox.setChecked(true);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                checkBox.setChecked(false);
                                checkBox.setBackgroundColor(Color.RED);
                                if (e instanceof ApiException)
                                {
                                    ApiException apiException = (ApiException) e;
                                    int statusCode = apiException.getStatusCode();
                                    Log.e("Recaptcha", "Error: " + CommonStatusCodes
                                            .getStatusCodeString(statusCode));
                                } else
                                {
                                    Log.e("Recaptcha", "Error: " + e.getMessage());
                                }
                            }
                        });
                break;

        }
    }
}