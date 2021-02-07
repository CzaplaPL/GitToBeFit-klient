package pl.gittobefit.workoutforms.fragments.forms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pl.gittobefit.R;

/**
 fragment tab2
 */
public class Tab2Fragment extends Fragment {

    public Tab2Fragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_tab2, container, false );
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