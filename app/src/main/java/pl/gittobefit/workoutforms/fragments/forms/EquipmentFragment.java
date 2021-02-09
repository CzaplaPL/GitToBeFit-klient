package pl.gittobefit.workoutforms.fragments.forms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.gittobefit.R;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.adapters.EquipmentAdapter;
import pl.gittobefit.workoutforms.adapters.EquipmentList;
import pl.gittobefit.workoutforms.object.EquipmentForm;
import pl.gittobefit.workoutforms.object.EquipmentType;

/**
 fragment wyposażenia
 */
public class EquipmentFragment extends Fragment implements EquipmentAdapter.EquipmentListener
{
    EquipmentList equipmentList=new EquipmentList();
    EquipmentAdapter adapter;
    public EquipmentFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =inflater.inflate ( R.layout.fragment_equipment, container, false );
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        ConnectionToServer.getInstance().WorkoutFormsServices.getEquipmentType(this);
    }
    public void createList(ArrayList<EquipmentType> equipmentType)
    {
        equipmentList.setData(new ArrayList<EquipmentForm>(equipmentType));
        equipmentList.init(this);
        RecyclerView rvContacts = (RecyclerView) getView().findViewById(R.id.rvContacts);
        rvContacts.setAdapter(equipmentList.getAdapter());
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayout loading = getView().findViewById(R.id.equipmentLoading);
        loading.setVisibility(View.GONE);
    }


    @Override
    public void onItemClick(int position)
    {
        equipmentList.click(position);
    }
}