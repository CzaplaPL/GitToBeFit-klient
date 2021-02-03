package pl.gittobefit.user.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

/**
 * fragment rejestracji
 */
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
        Button registr =  view.findViewById(R.id.buttonRegistr);
        registr.setOnClickListener(this);
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
                CheckBox robot = (CheckBox) getView().findViewById(R.id.checkBox_robot);
                CheckBox terms = (CheckBox) getView().findViewById(R.id.checkBox_statute);
                boolean correct =true;
                if(!email.getEditText().getText().toString().matches(emailValidation))
                {
                    email.setError(getResources().getString(R.string.incorrectData));
                    correct =false;
                }else email.setErrorEnabled(false);

                if(!pass.getEditText().getText().toString().matches(passValidation))
                {
                    pass.setError(getResources().getString(R.string.incorrectData));
                   correct =false;
                }else pass.setErrorEnabled(false);
                if(!pass2.getEditText().getText().toString().matches(passValidation))
                {
                    pass2.setError(getResources().getString(R.string.incorrectData));
                    correct =false;
                }else if(!pass.getEditText().getText().toString().equals(pass2.getEditText().getText().toString()))
                {
                    pass.setError(getResources().getString(R.string.incorrectpassword));
                    pass2.setError(getResources().getString(R.string.incorrectpassword));
                    correct =false;
                }else pass2.setErrorEnabled(false);
                if(!robot.isChecked())
                {
                    robot.setTextColor(ContextCompat.getColor(getContext(), R.color.ourRed));
                    correct =false;
                }else robot.setTextColor(ContextCompat.getColor(getContext(), R.color.ourGray));
                if(!terms.isChecked())
                {
                    terms.setTextColor(ContextCompat.getColor(getContext(), R.color.ourRed));
                    correct =false;
                }else terms.setTextColor(ContextCompat.getColor(getContext(), R.color.ourGray));
                if(correct)
                {
                    Log.w("rejestracja" ,"jest");
                    ConnectionToServer.getInstance().userServices.singup(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),this,getView());
                }
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
    public void Fail(boolean duplicate)
    {
        TextInputLayout email =(TextInputLayout)getView().findViewById(R.id.loginMailKontener);
        if(duplicate)
        {
            email.setError(getResources().getString(R.string.duplicateEmail));
        }else
        {
            email.setError(getResources().getString(R.string.serwerError));
        }
    }
    public void Success(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_registration_to_login2);
        Navigation.findNavController(view).navigate(R.id.action_login_to_registrationSuccesDialog);
    }
}