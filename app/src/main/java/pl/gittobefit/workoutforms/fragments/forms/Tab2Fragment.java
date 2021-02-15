package pl.gittobefit.workoutforms.fragments.forms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentEquipmentBinding;
import pl.gittobefit.databinding.FragmentTab2Binding;

/**
 fragment tab2
 */
public class Tab2Fragment extends Fragment {
    private FragmentTab2Binding binding;
    public Tab2Fragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTab2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        TextView text =getView().findViewById(R.id.tmpppp);
        text.setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Log.w("eeeeeee","bbbbbbbbbbbbbbbbbbbbbbbbbb");
    }
}