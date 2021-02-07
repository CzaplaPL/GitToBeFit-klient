package pl.gittobefit.network;

import android.util.Log;

import java.util.List;

import pl.gittobefit.LogUtils;
import pl.gittobefit.network.interfaces.IWorkoutFormsServices;
import pl.gittobefit.workoutforms.object.EquipmentType;
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
    public void getEquipmentType(/*Equipment fragment*/)
    {
        Log.w("Network", "WorkoutForms.getEquipmentType");
        Call<List<EquipmentType>> call = workout.getEquipmentType();
        call.enqueue(new Callback<List<EquipmentType>>()
        {
            @Override
            public void onResponse(Call<List<EquipmentType>> call, Response<List<EquipmentType>> response)
            {
                if(response.isSuccessful())
                {
                //    fragment.generateListView(response.body());
                }else
                {
                    Log.e("Network ", "WorkoutForms.getEquipmentType error " +String.valueOf(response.code()));
                    LogUtils.logCause(response.headers().get("Cause"));
                }
            }
            @Override
            public void onFailure(Call<List<EquipmentType>> call, Throwable t)
            {
                Log.e("Network ", "WorkoutForms.getEquipmentType error = " + t.toString());
            }


        });
    }
}
