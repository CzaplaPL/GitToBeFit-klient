package pl.gittobefit.workoutforms.fragments.forms;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pl.gittobefit.R;

/**
 fragment tab2
 */
public class Tab2Fragment extends Fragment {

    String split = "Split to inaczej trening dzielony. Polega na ćwiczeniu każdej partii mięśni .Taki schemat ćwiczeń to najskuteczniejszy sposób na zbudowanie masy mięśniowej i wyrzeźbienie sylwetki.";
    String fbw = "Trening FBW to ćwiczenia całego ciała. W trakcie jego wykonywania angażujesz wiele partii mięśniowych. Głównym założeniem jest wywołanie jak największej odpowiedzi anabolicznej twojego ciała, poprzez pobudzanie go ćwiczeniami wielostawowymi. ";
    String cardio = "Trening cardio to ćwiczenia wytrzymałościowe dotleniające organizm, poprawiające pracę układu krążenia oraz kondycję organizmu. Zwiększa ilość oddechów na minutę oraz przyspiesza tętno, co sprawia, że komórki i tkanki mięśniowe są lepiej dotlenione.";
    String fitness  = "Trening prozdrowotny. Głównym założeniem treningu fitness jest jego pozasportowy, prozdrowotny charakter. Do zajęć fitness można więc zaliczyć wszelkie formy aktywności fizycznej, ale podejmowane świadomie i systematycznie w celu poprawy szeroko pojmowanego zdrowia.";

    String series = "Wykonywanie ćwiczenia na ilość serii.";
    String circuit = "Wykonywanie ćwiczenia przez określony czas.";

    String days = "Dostarczymy Ci plan treningowy na wybraną przez Ciebie ilość dni.";
    String minutes = "Dostarczymy Ci pojedyńczy trening w wybranych przez Ciebie ramach czaowych.";

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
        Spinner spinner1, spinner2, spinner3;
        spinner1 = (Spinner) getView().findViewById(R.id.spinner1);
        spinner2 = (Spinner) getView().findViewById(R.id.spinner2);
        spinner3 = (Spinner) getView().findViewById(R.id.spinner3);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.array1, R.layout.my_spinner);
        spinner1.setAdapter(adapter1);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.training_subtype, R.layout.my_spinner);
        spinner2.setAdapter(adapter2);

        TextView td = getView().findViewById(R.id.training_description);
        td.setText(split);

        TextView std = getView().findViewById(R.id.subtraining_description);
        std.setText(series);

        TextView dd = getView().findViewById(R.id.duration_description);
        dd.setText(days);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner1.getSelectedItem().equals("Trening split")) {
                    Toast.makeText(getContext(), "test 123",
                            Toast.LENGTH_SHORT).show();

                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.split_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);

                    td.setText(split);
                } else if (spinner1.getSelectedItem().equals("Trening fbw")){
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.fbw_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);
                    td.setText(fbw);
                }
                else if (spinner1.getSelectedItem().equals("Trening cardio")){
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.fintess_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);
                    td.setText(cardio);
                }
                else if (spinner1.getSelectedItem().equals("Trening fitness")){
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(),
                            R.array.fintess_duration, R.layout.my_spinner);
                    spinner3.setAdapter(adapter3);
                    td.setText(fitness);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner2.getSelectedItem().equals("Serie"))
                {
                    std.setText(series);
                }
                else
                {
                    std.setText(circuit);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner3.getSelectedItem().equals("3 dni") || spinner3.getSelectedItem().equals("4 dni") ||spinner3.getSelectedItem().equals("5 dni"))
                {
                    dd.setText(days);
                }
                else
                {
                    dd.setText(minutes);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}