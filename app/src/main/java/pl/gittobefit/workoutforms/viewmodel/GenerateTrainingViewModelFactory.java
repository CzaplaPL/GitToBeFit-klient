package pl.gittobefit.workoutforms.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GenerateTrainingViewModelFactory implements ViewModelProvider.Factory
{

    private Context context;

    public GenerateTrainingViewModelFactory(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        return (T) new GenerateTraningViewModel(context);
    }
}
