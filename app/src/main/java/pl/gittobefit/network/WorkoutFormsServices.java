package pl.gittobefit.network;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import pl.gittobefit.IShowSnackbar;
import pl.gittobefit.LogUtils;
import pl.gittobefit.R;
import pl.gittobefit.WorkoutDisplay.objects.Training;
import pl.gittobefit.WorkoutDisplay.viewmodel.InitiationTrainingDisplayLayoutViewModel;
import pl.gittobefit.database.entity.equipment.Checksum;
import pl.gittobefit.database.entity.training.WorkoutForm;
import pl.gittobefit.database.repository.TrainingRepository;
import pl.gittobefit.network.interfaces.IWorkoutFormsServices;
import pl.gittobefit.user.User;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.EquipmentItem;
import pl.gittobefit.workoutforms.object.EquipmentTypeItem;
import pl.gittobefit.database.repository.WorkoutFormsRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkoutFormsServices
{
    private final IWorkoutFormsServices workout;

    WorkoutFormsServices(Retrofit adapter)
    {
        workout = adapter.create(IWorkoutFormsServices.class);
    }

    public void checkChecksum(WorkoutFormsRepository repository)
    {
        Log.w("Network", "WorkoutForms.checkChecksum");
        Call<ArrayList<Checksum>> call = workout.getChecksum();
        call.enqueue(new Callback<ArrayList<Checksum>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Checksum>> call, Response<ArrayList<Checksum>> response)
            {
                if(response.code()==200)
                {
                    repository.setChecksum(response.body());
                }else
                {
                    Log.e("Network","kod błędu checkChecksum " + String.valueOf(response.code()));
                    LogUtils.logCause(response.headers().get("Cause"));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Checksum>> call, Throwable t)
            {
                Log.e("Network ", "WorkoutForms.checkChecksum error = " + t.toString());
            }
        });
    }



    public void getEquipmentType(WorkoutFormsRepository repository)
    {
        Log.w("Network", "WorkoutForms.getEquipmentType");
        Call<ArrayList<EquipmentTypeItem>> call = workout.getEquipmentType();
        call.enqueue(new Callback<ArrayList<EquipmentTypeItem>>()
        {
            @Override
            public void onResponse(Call<ArrayList<EquipmentTypeItem>> call, Response<ArrayList<EquipmentTypeItem>> response)
            {
                if(response.isSuccessful())
                {
                    Call<Void> call2 = workout.getNoEquipment();
                    call2.enqueue(new Callback<Void>()
                    {
                        @Override
                        public void onResponse(Call<Void> call2, Response<Void> response2)
                        {
                            if(response2.code()==200)
                            {
                                repository.downloadEquipmentType(response.body(),Integer.parseInt(response2.headers().get("id")));
                            }else
                            {
                                Log.e("Network","kod błędu getNoEquipment " + String.valueOf(response2.code()));
                                LogUtils.logCause(response.headers().get("Cause"));
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call2, Throwable t)
                        {
                            Log.e("Network ", "WorkoutForms.getNoEquipment error = " + t.toString());
                        }
                    });

                }else
                {
                    Log.e("Network ", "WorkoutForms.getEquipmentType error " +String.valueOf(response.code()));
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }
            @Override
            public void onFailure(Call<ArrayList<EquipmentTypeItem>> call, Throwable t)
            {
                Log.e("Network ", "WorkoutForms.getEquipmentType error = " + t.toString());
            }


        });
    }
    public void getEquipment(int typeid, int position, WorkoutFormsRepository repository)
    {
        Log.w("Network", "WorkoutForms.getEquipment");
        ArrayList<EquipmentItem> data =new ArrayList<EquipmentItem>();
        Call<ArrayList<EquipmentItem>> call = workout.getEquipment(typeid);
        call.enqueue(new Callback<ArrayList<EquipmentItem>>()
        {
            @Override
            public void onResponse(Call<ArrayList<EquipmentItem>> call, Response<ArrayList<EquipmentItem>> response)
            {
                if(response.isSuccessful())
                {
                    repository.addEquipment(typeid,response.body(),position);
                }else
                {
                    Log.e("Network ", "WorkoutForms.getEquipmentType error " +String.valueOf(response.code()));
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }
            @Override
            public void onFailure(Call<ArrayList<EquipmentItem>> call, Throwable t)
            {
                Log.e("Network ", "WorkoutForms.getEquipmentType error = " + t.toString());
            }


        });
    }

    public void getTrainingPlan(Fragment fragment, WorkoutForm form)
    {
        Log.w("form",String.format("%s %s %s %s %s %s %s %s %s %s %s %s","equipmentIDs",form.getEquipmentIDs().toString()," trainingType ",form.getTrainingType()," bodyParts ",form.getBodyParts()," daysCount",form.getDaysCount()," scheduleType ",form.getScheduleType()," duration ",form.getDuration()));
        IShowSnackbar activity = (IShowSnackbar) fragment.getActivity();
        activity.showSnackbar(fragment.getString(R.string.generateTraining));
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Call<Training> call;
        if (!User.getInstance().getLoggedBy().equals(User.WayOfLogin.NO_LOGIN))
        {
             call = workout.getTrainingPlanForLoggedInUser(form, User.getInstance().getToken(), formatter.format(date));
        }
        else
        {
             call = workout.getTrainingPlan(form, formatter.format(date));
        }

        call.enqueue(new Callback<Training>()
        {
            @Override
            public void onResponse(Call<Training> call, Response<Training> response) {
                if(response.isSuccessful())
                {
                    createTraining(response.body(), fragment);
                    Navigation.findNavController(fragment.getView()).navigate(R.id.action_generateTrainingForm_to_displayReceivedTraining);
                    activity.showSnackbar(fragment.getString(R.string.generateTrainingSukccess));
                }
                else
                {
                    Log.e("Network ", "WorkoutForms.getTrainingType error " + response.code());
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }

            @Override
            public void onFailure(Call<Training> call, Throwable t)
            {
                Log.e("Network ", "WorkoutForms.getTrainingType error = " + t.toString());
            }
        });
    }

    private void createTraining(Training body, Fragment fragment) {
        body.setGenerationDate(body.getGenerationDate());
        body.setTrainingName("Default training name");
        InitiationTrainingDisplayLayoutViewModel model = new ViewModelProvider(fragment.requireActivity())
                .get(InitiationTrainingDisplayLayoutViewModel.class);
        model.addTrainingWithForm(TrainingRepository.getInstance(fragment.getContext()).add(body));
        model.setNumberOfClickedTraining(model.getTrainingWithForms().size() - 1);
    }
}
