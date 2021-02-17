package pl.gittobefit.workoutforms.repository;

import java.util.ArrayList;

import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.workoutforms.fragments.forms.EquipmentFragment;
import pl.gittobefit.workoutforms.object.Equipment;
import pl.gittobefit.workoutforms.object.EquipmentType;
import pl.gittobefit.workoutforms.viewmodel.GenerateTraningViewModel;

public class WorkoutFormsRepository
{
    ArrayList<EquipmentType> equipmentTypes = new ArrayList<>();
    GenerateTraningViewModel observer;

    public WorkoutFormsRepository(GenerateTraningViewModel generateTraningViewModel)
    {
        observer=generateTraningViewModel;
    }

    /**
     * wczytywanie kategori sprzetu
     * @param equipmentFragment fragment w którym mają byc wyświetlone wczytane kategorie
     */
    public void loadEquipmentTypes(EquipmentFragment equipmentFragment)
    {
        ConnectionToServer.getInstance().WorkoutFormsServices.getEquipmentType(equipmentFragment);
    }

    /**
     * funkcja pobierajaca cwiczenia z danej kategori
     * @param typeid id kategori
     * @param position pozycja po ktorej nastepuje wczytywanie
     */
    public void loadEquipment(int typeid, int position)
    {
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(equipmentTypes.get(i).getId()==typeid)
            {
                if(equipmentTypes.get(i).isLoad())
                {
                    observer.loadEquipment(position, equipmentTypes.get(i).getEquipment());
                    return;
                }
            }
        }
        ConnectionToServer.getInstance().WorkoutFormsServices.getEquipment(typeid, position, this);
    }

    /**
     * funkcja dodajaca sprzet do konkretnej kategori i informujaca o tym obserwatora
     * @param typeid id kategori
     * @param response  lista sprzetów do dodania
     * @param position pozycja po której był wczytywany sprzet
     */
    public void addEquipment(int typeid, ArrayList<Equipment> response,int position)
    {
        for(int i = 0; i < equipmentTypes.size(); i++)
        {
            if(equipmentTypes.get(i).getId()==typeid)
            {
                equipmentTypes.get(i).setEquipment(response);
                observer.loadEquipment(position, equipmentTypes.get(i).getEquipment());
               break;
            }
        }
    }


    public void setEqiupmentTypes(ArrayList<EquipmentType> equipmentTypes)
    {
        this.equipmentTypes= equipmentTypes;
    }
}
