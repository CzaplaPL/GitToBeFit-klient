package pl.gittobefit.network;

import pl.gittobefit.network.interfaces.IUserServices;
import pl.gittobefit.network.interfaces.IWorkoutFormsServices;
import retrofit2.Retrofit;

public class WorkoutFormsServices
{
    private final IWorkoutFormsServices workout;
    WorkoutFormsServices(Retrofit adapter)
    {
        workout = adapter.create(IWorkoutFormsServices.class);
    }

}
