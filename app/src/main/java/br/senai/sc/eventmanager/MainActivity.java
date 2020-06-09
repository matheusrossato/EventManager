package br.senai.sc.eventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.eventmanager.database.EventDAO;
import br.senai.sc.eventmanager.models.Event;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEvents;
    private ArrayAdapter<Event> adapterEvents;
    private int id = 0;
    private boolean nameOrderByAsc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");

        listViewEvents = findViewById(R.id.listView_events);
        ArrayList<Event> eventArrayList = new ArrayList<Event>();

        defineOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventDAO eventDAO = new EventDAO(getBaseContext());
        adapterEvents = new ArrayAdapter<Event>(MainActivity.this,
                android.R.layout.simple_list_item_1, eventDAO.list(nameOrderByAsc));
        listViewEvents.setAdapter(adapterEvents);
    }

    private void defineOnClickListenerListView() {
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event eventClicked = adapterEvents.getItem(position);
                Intent intent = new Intent(MainActivity.this, EventEditorActivity.class);
                intent.putExtra("editEvent", eventClicked);
                startActivity(intent);
            }
        });
    }

    public void onClickCreateEvent(View v) {
        Intent intent = new Intent(MainActivity.this, EventEditorActivity.class);
        startActivity(intent);
    }
    public void onClickSearch(View v){
        EditText editTextSearchEvent = findViewById(R.id.editText_searchEvent);
        String nameSearch = editTextSearchEvent.getText().toString();
        Toast.makeText(MainActivity.this, " Buscando eventos com nome iniciando por: " + nameSearch, Toast.LENGTH_LONG).show();
        EventDAO eventDAO = new EventDAO(getBaseContext());
        if (nameSearch!=null && !nameSearch.isEmpty()) {
            adapterEvents = new ArrayAdapter<Event>(MainActivity.this,
                    android.R.layout.simple_list_item_1, eventDAO.search(nameSearch+ "%", nameOrderByAsc));
        } else {
            adapterEvents = new ArrayAdapter<Event>(MainActivity.this,
                    android.R.layout.simple_list_item_1, eventDAO.list(nameOrderByAsc));
        }
        listViewEvents.setAdapter(adapterEvents);
    }

    public void onClickOrderBy(View v) {
        EditText editTextSearchEvent = findViewById(R.id.editText_searchEvent);
        String nameSearch = editTextSearchEvent.getText().toString();
        if(v.getId()==R.id.btn_orderDesc){
            nameOrderByAsc = false;
        } else {
            nameOrderByAsc = true;
        }
        Toast.makeText(MainActivity.this, " Ordenando eventos com nome: " + nameSearch + "ascendente: " + nameOrderByAsc, Toast.LENGTH_LONG).show();
        EventDAO eventDAO = new EventDAO(getBaseContext());
        if (nameSearch!=null && !nameSearch.isEmpty()) {
            adapterEvents = new ArrayAdapter<Event>(MainActivity.this,
                    android.R.layout.simple_list_item_1, eventDAO.search(nameSearch+"%",nameOrderByAsc));
        } else {
            adapterEvents = new ArrayAdapter<Event>(MainActivity.this,
                    android.R.layout.simple_list_item_1, eventDAO.list(nameOrderByAsc));
        }
        listViewEvents.setAdapter(adapterEvents);
    }
}
