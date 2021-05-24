package pl.gittobefit.user.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.Validation;

/**
 * fragment rejestracji
 */
public class Registration extends Fragment implements View.OnClickListener
{
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
        TextInputLayout email= view.findViewById(R.id.loginMailKontener);
        TextView linkRegulations = view.findViewById(R.id.title_checkbox);
        TextInputEditText email2= view.findViewById(R.id.loginMail);
        email2.setOnFocusChangeListener((v, hasFocus) -> email.setErrorEnabled(false));
        TextInputLayout password= view.findViewById(R.id.loginPasswordKontener);
        TextInputEditText passwordText= view.findViewById(R.id.loginPassword);
        passwordText.setOnFocusChangeListener((v, hasFocus) -> password.setErrorEnabled(false));
        TextInputLayout password2= view.findViewById(R.id.loginRewersePasswordKontener);
        TextInputEditText passwordText2= view.findViewById(R.id.loginRewersePassword);
        passwordText2.setOnFocusChangeListener((v, hasFocus) -> password2.setErrorEnabled(false));
        registr.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        linkRegulations.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_registration_to_detailsRegulationsLayout));
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
                if(!email.getEditText().getText().toString().matches( Validation.EMAIL_REGEX))
                {
                    email.setError(getResources().getString(R.string.wrongEmail));
                    correct =false;
                }else email.setErrorEnabled(false);

                if(!pass.getEditText().getText().toString().matches(Validation.PASSWORD_REGEX))
                {
                    pass.setError(getResources().getString(R.string.wrongPassword));
                   correct =false;
                }else pass.setErrorEnabled(false);
                if(!pass2.getEditText().getText().toString().matches(Validation.PASSWORD_REGEX))
                {
                    pass2.setError(getResources().getString(R.string.wrongPassword));
                    correct =false;
                }else if(!pass.getEditText().getText().toString().equals(pass2.getEditText().getText().toString()))
                {
                    pass.setError(getResources().getString(R.string.wrongPassword2));
                    pass2.setError(getResources().getString(R.string.wrongPassword2));
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
                    IShowSnackbar activity = (IShowSnackbar) getActivity();
                    activity.showSnackbar(getString(R.string.registrationStart));
                    ConnectionToServer.getInstance().userServices.singup(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),this,getView());
                }
                break;
                case R.id.checkBox_robot:
                CheckBox checkBox =  view.findViewById(R.id.checkBox_robot);
                checkBox.setChecked(false);
                SafetyNet.getClient(getActivity()).verifyWithRecaptcha("6LdH0ScaAAAAAOAnxd_zMOzmbco0_VRrazkQvdUQ")
                        .addOnSuccessListener(recaptchaTokenResponse ->
                        {
                            String userResponseToken = recaptchaTokenResponse.getTokenResult();
                            if (!userResponseToken.isEmpty())
                            {
                                checkBox.setChecked(true);
                            }
                        })
                        .addOnFailureListener(e ->
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
                        });
                break;
        }
    }
    public void Fail(boolean duplicate)
    {
        TextInputLayout email =(TextInputLayout)getView().findViewById(R.id.loginMailKontener);
        if(duplicate)
        {
            email.setError(getResources().getString(R.string.duplicatedEmail));
        }else
        {
            email.setError(getResources().getString(R.string.serwerError));
        }
    }
    public void Success(View view)
    {
        Navigation.findNavController(view).navigate(RegistrationDirections.actionRegistrationToLogin2());
        Navigation.findNavController(view).navigate(R.id.action_login_to_registrationSuccesDialog);
    }


}