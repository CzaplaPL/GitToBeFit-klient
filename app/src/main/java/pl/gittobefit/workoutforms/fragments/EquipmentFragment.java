package pl.gittobefit.workoutforms.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.gittobefit.R;
import pl.gittobefit.workoutforms.adapters.EquipmentAdapter;
import pl.gittobefit.workoutforms.object.EquipmentType;

/**
 fragment wyposażenia
 */
public class EquipmentFragment extends Fragment {

   
    public EquipmentFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =inflater.inflate ( R.layout.fragment_equipment, container, false );
        RecyclerView rvContacts = (RecyclerView) view.findViewById(R.id.rvContacts);
        ArrayList<String> data = new ArrayList<String>();
        data.add("silownia");
        data.add("cos");
        data.add("innego");
        data.add("czwarty");
        data.add("dziesiąty");
        data.add("ten");
        // Create adapter passing in the sample user data
        EquipmentAdapter adapter = new EquipmentAdapter(data);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // Lookup the recyclerview in activity layout
    }


    @Override
    public void onResume()
    {
        super.onResume();
        Log.w("eeeeeee","bbbbbbbbbbbbbbbbbbbbbbbbbb");
    }


}