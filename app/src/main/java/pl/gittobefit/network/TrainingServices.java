package pl.gittobefit.network;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.LogUtils;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.network.interfaces.ITrainingServices;
import pl.gittobefit.running_training.viewmodel.ChangeExerciseViewModel;
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
            System.out.println(training.getGenerationDate());
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
        User.getInstance().setSynchroniseTraining(User.SynchroniseTraining.SYNCHRONISE_SUCCESS);
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

    public void saveTrainingAfterChanges(IShowSnackbar activity, Context context, int id)
    {
        TrainingRepository repository = TrainingRepository.getInstance(context);
        User user = User.getInstance();
        Call<Void> call = training.sendTrainings(user.getToken(), repository.getTrainingsToSendAfterChanges(id));
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

    public void deleteTrainingFromServer(IShowSnackbar activity, long id, Context context)
    {
        Call<Void> call = training.deleteTrainingPLan(String.valueOf(id), User.getInstance().getToken());
        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (response.isSuccessful())
                {

                }
                else
                {
                    LogUtils.logCause(response.headers().get("Cause"));
                }
                Log.e("error delete training", String.valueOf(code));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(" error ", "change training title : " + t.toString());
                activity.showSnackbar(context.getResources().getString(R.string.serwerError));
            }
        });
    }

    public void changeExercise(long id, WorkoutForm form, Fragment fragment)
    {
        Call<ArrayList<Exercise>> call = training.changeExercise(String.valueOf(id), form);
        call.enqueue(new Callback<ArrayList<Exercise>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Exercise>> call, Response<ArrayList<Exercise>> response) {

                int code = response.code();
                if (response.isSuccessful())
                {
                    ChangeExerciseViewModel model = new ViewModelProvider(fragment).get(ChangeExerciseViewModel.class);
                    model.setListExercises(response.body());
                    System.out.println(model.getListExercises().size());
                }
                else
                {
                    LogUtils.logCause(response.headers().get("Cause"));
                }
                Log.e("error change exercise", String.valueOf(code));
            }

            @Override
            public void onFailure(Call<ArrayList<Exercise>> call, Throwable t) {

            }
        });
    }

}
