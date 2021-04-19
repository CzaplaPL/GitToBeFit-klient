package pl.gittobefit.network;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import pl.gittobefit.LogUtils;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.user.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrainingServices
{
    private final pl.gittobefit.network.interfaces.TrainingServices training;

    public TrainingServices(Retrofit adapter) { this.training = adapter.create(pl.gittobefit.network.interfaces.TrainingServices.class); }

    public void synchronisedTraining(Context context) throws Exception
    {
        Log.w("Network", "Trainings.synchronisedTraining");
        TrainingRepository repository = TrainingRepository.getInstance(context);
        Call<ArrayList<Training>> downloadCall = training.getTrainings(User.getInstance().getToken(), User.getInstance().getIdServer());
        Response<ArrayList<Training>> downloadResponse = downloadCall.execute();
        if(!downloadResponse.isSuccessful())
        {
            Log.e("Network", "Trainings.SendTraining " + String.valueOf(downloadResponse.code()));
            LogUtils.logCause(downloadResponse.headers().get("Cause"));
            throw new Exception("get Training not work");
        }
        for(Training training : downloadResponse.body())
        {
            repository.add(training);
        }
        Call<Void> sendCall = training.sendTrainings(User.getInstance().getToken(), User.getInstance().getIdServer(), repository.getTrainingsToSend());
        Response<Void> sendResponse = sendCall.execute();
        if(!sendResponse.isSuccessful())
        {
            Log.e("Network", "Trainings.SendTraining " + String.valueOf(sendResponse.code()));
            LogUtils.logCause(sendResponse.headers().get("Cause"));
            throw new Exception("Send Training not work");
        }
        repository.synchroniseUser();
        Log.w("Network", "Trainings.synchronisedTraining sukces");
        User.getInstance().setSynchroniseTraining(User.SynchroniseTraining.Synchronise_Success);
    }
}
