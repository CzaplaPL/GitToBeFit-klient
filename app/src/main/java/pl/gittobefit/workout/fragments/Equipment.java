package pl.gittobefit.workout.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import pl.gittobefit.R;
import pl.gittobefit.workout.object.EquipmentType;

public class Equipment extends Fragment
{

    public Equipment()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_equipment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        tmp();

    }
    public void tmp()
    {



    }

    public void generateListView(List<EquipmentType> Type)
    {

    }
}