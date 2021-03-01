package pl.gittobefit.network;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pl.gittobefit.LogUtils;
import pl.gittobefit.network.interfaces.IWorkoutFormsServices;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.object.Training;
import pl.gittobefit.workoutforms.repository.WorkoutFormsRepository;
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
    public void getEquipmentType(EquipmentFragment fragment)
    {
        Log.w("Network", "WorkoutForms.getEquipmentType");
        Call<ArrayList<EquipmentType>> call = workout.getEquipmentType();
        call.enqueue(new Callback<ArrayList<EquipmentType>>()
        {
            @Override
            public void onResponse(Call<ArrayList<EquipmentType>> call, Response<ArrayList<EquipmentType>> response)
            {
                if(response.isSuccessful())
                {
                   fragment.createList(response.body());
                }else
                {
                    Log.e("Network ", "WorkoutForms.getEquipmentType error " +String.valueOf(response.code()));
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }
            @Override
            public void onFailure(Call<ArrayList<EquipmentType>> call, Throwable t)
            {
                Log.e("Network ", "WorkoutForms.getEquipmentType error = " + t.toString());
            }


        });
    }
    public void getEquipment(int typeid, int position, WorkoutFormsRepository repository)
    {
        Log.w("Network", "WorkoutForms.getEquipmentType");
        ArrayList<Equipment> data =new ArrayList<Equipment>();
        Call<ArrayList<Equipment>> call = workout.getEquipment(typeid);
        call.enqueue(new Callback<ArrayList<Equipment>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Equipment>> call, Response<ArrayList<Equipment>> response)
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
            public void onFailure(Call<ArrayList<Equipment>> call, Throwable t)
            {
                Log.e("Network ", "WorkoutForms.getEquipmentType error = " + t.toString());
            }


        });
    }

    public void getTrainingPlan(Fragment fragment)
    {
        Call<Training> call = workout.getTrainingPlan();
        call.enqueue(new Callback<Training>()
        {
            @Override
            public void onResponse(Call<Training> call, Response<Training> response) {
                if(response.isSuccessful())
                {
                  createTraining(response.body());
                }
                else
                {
                    Log.e("Network ", "WorkoutForms.getTrainingType error " + response.code());
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }

            @Override
            public void onFailure(Call<Training> call, Throwable t) {
                Log.e("Network ", "WorkoutForms.getTrainingType error = " + t.toString());
            }
        });
    }

    private void createTraining(Training body) {
        System.out.println(body.toString());
    }
}
