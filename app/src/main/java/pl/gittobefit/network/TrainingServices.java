package pl.gittobefit.network;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.LogUtils;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.network.interfaces.ITrainingServices;
import pl.gittobefit.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrainingServices
{
    private final ITrainingServices training;

    public TrainingServices(Retrofit adapter)
    {
        this.training = adapter.create(ITrainingServices.class);
    }

    public void synchronisedTraining(Context context) throws Exception
    {
        Log.w("Network", "Trainings.synchronisedTraining");
        TrainingRepository repository = TrainingRepository.getInstance(context);
        User user = User.getInstance();
        repository.deleteAllTrainingsForUser(user.getIdServer());
        Call<ArrayList<Training>> downloadCall = training.getTrainings(user.getToken());
        Response<ArrayList<Training>> downloadResponse = downloadCall.execute();
        if(downloadResponse.code() != 200)
        {
            Log.e("Network", "Trainings.SendTraining " + String.valueOf(downloadResponse.code()));
            LogUtils.logCause(downloadResponse.headers().get("Cause"));
            throw new Exception("get Training not work");
        }
        for(Training training : downloadResponse.body())
        {
            repository.add(training);
        }
        Call<Void> sendCall = training.sendTrainings(user.getToken(), repository.getTrainingsToSend());
        Response<Void> sendResponse = sendCall.execute();
        if(sendResponse.code() != 200)
        {
            Log.e("Network", "Trainings.SendTraining " + String.valueOf(sendResponse.code()));
            LogUtils.logCause(sendResponse.headers().get("Cause"));
            throw new Exception("Send Training not work");
        }
        repository.synchroniseUser();
        Log.w("Network", "Trainings.synchronisedTraining sukces");
        User.getInstance().setSynchroniseTraining(User.SynchroniseTraining.Synchronise_Success);
    }

    public void updateTrainingName(long id, IShowSnackbar activity, Context context, String title)
    {
        Call<Void> call = training.updateTrainingTitle(String.valueOf(id), User.getInstance().getToken(), title);
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (response.isSuccessful())
                {
                    activity.showSnackbar(context.getResources().getString(R.string.nameChanged));
                }
                else
                {
                    LogUtils.logCause(response.headers().get("Cause"));
                }
                Log.e("error code change title", String.valueOf(code));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.e(" error ", "change training title : " + t.toString());
                activity.showSnackbar(context.getResources().getString(R.string.serwerError));
            }
        });
    }
    public void saveTrainingAfterChanges(IShowSnackbar activity, Context context)
    {
        TrainingRepository repository = TrainingRepository.getInstance(context);
        User user = User.getInstance();
        Call<Void> call = training.sendTrainings(user.getToken(), repository.getTrainingsToSendAfterChanges());
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (response.isSuccessful())
                {
                    activity.showSnackbar(context.getResources().getString(R.string.editionComplete));
                }
                else
                {
                    LogUtils.logCause(response.headers().get("Cause"));
                }
                Log.e("error save edit changes", String.valueOf(code));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(" error ", "change training title : " + t.toString());
                activity.showSnackbar(context.getResources().getString(R.string.serwerError));
            }
        });
    }

}
