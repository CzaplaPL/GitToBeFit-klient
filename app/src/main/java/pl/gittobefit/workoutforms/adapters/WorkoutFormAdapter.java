package pl.gittobefit.workoutforms.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import pl.gittobefit.workoutforms.fragments.GenerateTrainingForm;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.fragments.forms.Tab2Fragment;
import pl.gittobefit.workoutforms.fragments.forms.SummaryFragment;


/**
 klasa generujacą formularze treningu
 */
public class WorkoutFormAdapter extends FragmentStateAdapter {

    public WorkoutFormAdapter(GenerateTrainingForm generateTrainingForm) {
        super ( generateTrainingForm );
    }

    // metoda ustawienia naszych fragmentow za pomoca naszego adaptera
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return  new EquipmentFragment ();
            case 1:
                return  new Tab2Fragment ();
            default:
                return  new SummaryFragment();
        }
    }
    @Override
    public int getItemCount() {return 3;}
}