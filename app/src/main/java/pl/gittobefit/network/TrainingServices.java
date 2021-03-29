package pl.gittobefit.network;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import pl.gittobefit.LogUtils;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.objects.UserTrainings;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.network.interfaces.ITrainingServices;
import pl.gittobefit.network.interfaces.IWorkoutFormsServices;
import pl.gittobefit.user.User;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.repository.WorkoutFormsRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrainingServices
{
    private final ITrainingServices training;

    TrainingServices(Retrofit adapter)
    {
        training = adapter.create(ITrainingServices.class);
    }

    public void sendTrainings(ArrayList<Training> trainings)
    {
        Log.w("Network", "Trainings.sendTrainings");
        Call<Void> call = training.sendTraining(User.getInstance().getToken(),User.getInstance().getIdSerwer(),trainings);
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(!response.isSuccessful())
                {
                    Log.e("Network ", "Trainings.sendTrainings error " +String.valueOf(response.code()));
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e("Network ", "Trainings.sendTrainings error = " + t.toString());
            }
        });
    }

    public void synchronisedTraining(Context context) throws Exception
    {
        Log.w("Network", "Trainings.synchronisedTraining");
        TrainingRepository repository=new TrainingRepository(AppDataBase.getInstance(context));
        ArrayList<Training> tmp = repository.getTrainingsToSend();
        Call<Void> sendCall = training.sendTraining(User.getInstance().getToken(),User.getInstance().getIdSerwer(),tmp);
        Response<Void>  sendResponse = sendCall.execute();
        if(!sendResponse.isSuccessful())
        {
            Log.e("Network","Trainings.SendTraining " + String.valueOf(sendResponse.code()));
            LogUtils.logCause(sendResponse.headers().get("Cause"));
            throw new Exception("Send Training not work");
        }


    }
}
