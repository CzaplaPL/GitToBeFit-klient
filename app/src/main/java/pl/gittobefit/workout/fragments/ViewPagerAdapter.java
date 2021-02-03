package pl.gittobefit.workout.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(GenerateTraining generateTraining) {
        super (generateTraining);
    }

    // metoda ustawienia naszych fragmentow za pomoca naszego adaptera
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return  new EquipmentFragment();
            case 1:
                return  new Tab2Fragment();
            default:
                return  new Tab3Fragment();
        }
    }
    @Override
    public int getItemCount() {return 3;}
}