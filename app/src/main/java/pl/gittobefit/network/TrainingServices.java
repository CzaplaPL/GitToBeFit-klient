package pl.gittobefit.network;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import pl.gittobefit.LogUtils;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.AppDataBase;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.network.interfaces.ITrainingServices;
import pl.gittobefit.user.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrainingServices
{
    private final ITrainingServices training;

    TrainingServices(Retrofit adapter)
    {
        training = adapter.create(ITrainingServices.class);
    }

    public void synchronisedTraining(Context context) throws Exception
    {
        Log.w("Network", "Trainings.synchronisedTraining");
        TrainingRepository repository = new TrainingRepository(AppDataBase.getInstance(context));
        Call<ArrayList<Training>> getCall = training.getTrainings(User.getInstance().getToken(), User.getInstance().getIdSerwer());
        Response<ArrayList<Training>> getResponse = getCall.execute();
        if(!getResponse.isSuccessful())
        {
            Log.e("Network", "Trainings.SendTraining " + String.valueOf(getResponse.code()));
            ;
            LogUtils.logCause(getResponse.headers().get("Cause"));
            throw new Exception("get Training not work");
        }
        for(Training training : getResponse.body())
        {
            repository.add(training);
        }
        Call<Void> sendCall = training.sendTrainings(User.getInstance().getToken(), User.getInstance().getIdSerwer(), repository.getTrainingsToSend());
        Response<Void> sendResponse = sendCall.execute();
        if(!sendResponse.isSuccessful())
        {
            Log.e("Network", "Trainings.SendTraining " + String.valueOf(sendResponse.code()));
            ;
            LogUtils.logCause(sendResponse.headers().get("Cause"));
            throw new Exception("Send Training not work");
        }
        repository.synchroniseUser();
        Log.w("Network", "Trainings.synchronisedTraining sukces");
        User.getInstance().setSynchroniseTraining(User.SynchroniseTraining.Synchronize_Success);
    }
}
