package pl.gittobefit.workoutforms.fragments.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.viewmodel.DetailsViewModel;

/**
 fragment tab3
 */
public class Tab3Fragment extends Fragment {

    public Tab3Fragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_tab3, container, false );
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textView = getView().findViewById(R.id.tab3TextView);
        TextView textView2 = getView().findViewById(R.id.tab4TextView);
        DetailsViewModel model = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), item -> {
            String tekst;
            if ((item.getTrainingType().equals("Trening cardio")) ||  (item.getTrainingType().equals("Trening fitness")))
            {
                tekst = "Wybrałeś trening :\n" + item.getTrainingType() + "\n" + item.getDuration() + "\n" + item.getSubType();
            }
            else
            {
                tekst = "Wybrałeś trening :\n" + item.getTrainingType() + "\n" + item.getDuration();
            }

            String bodyParts = "";
            for(int i = 0; i < item.getBodyPartsArrayList().size() ;i++)
            {
               if (item.getBodyPartsArrayList().get(i).isSelected())
               {
                   bodyParts += item.getBodyPartsArrayList().get(i).getBodyPart();
               }
            }
            textView.setText(tekst);
            textView2.setText(bodyParts);

        });
    }
}
