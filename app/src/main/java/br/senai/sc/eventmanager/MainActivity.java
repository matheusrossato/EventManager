package br.senai.sc.eventmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.eventmanager.models.Event;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_CREATE_EVENT = 1;
    private final int RESULT_CODE_CREATE_EVENT = 10;
    private final int REQUEST_CODE_EDIT_EVENT = 2;
    private final int RESULT_CODE_EDIT_EVENT = 20;
    private final int RESULT_CODE_DELETE_EVENT = 30;

    private ListView listViewEvents;
    private ArrayAdapter<Event> adapterEvents;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");

        listViewEvents = findViewById(R.id.listView_events);
        ArrayList<Event> eventArrayList = new ArrayList<Event>();

        adapterEvents = new ArrayAdapter<Event>(MainActivity.this, android.R.layout.simple_list_item_1, eventArrayList);
        listViewEvents.setAdapter(adapterEvents);
        defineOnClickListenerListView();
    }
    private void defineOnClickListenerListView(){
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event eventClicked = adapterEvents.getItem(position);
                Intent intent = new Intent(MainActivity.this, EventEditorActivity.class);
                intent.putExtra("editEvent", eventClicked );
                startActivityForResult(intent, REQUEST_CODE_EDIT_EVENT);
            }
        });

    }

    public void onClickCreateEvent(View v) {
        Intent intent = new Intent(MainActivity.this, EventEditorActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CREATE_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode== REQUEST_CODE_CREATE_EVENT && resultCode== RESULT_CODE_CREATE_EVENT){
            Event event = (Event) data.getExtras().getSerializable("createEvent");
            event.setId(++id);
            this.adapterEvents.add(event);
            Toast.makeText(MainActivity.this,"Item adicionado com sucesso!", Toast.LENGTH_LONG).show();
        }
        else if (requestCode== REQUEST_CODE_EDIT_EVENT && resultCode== RESULT_CODE_EDIT_EVENT){
            Event editedEvent = (Event) data.getExtras().getSerializable("editEvent");
            for(int i = 0; i < adapterEvents.getCount(); i++) {
                Event event = adapterEvents.getItem(i);
                if(event.getId() == editedEvent.getId()) {
                    adapterEvents.remove(event);
                    adapterEvents.insert(editedEvent, i);
                    break;
                }
            }
            Toast.makeText(MainActivity.this,"Item editado com sucesso!", Toast.LENGTH_LONG).show();
        }
        else if (requestCode== REQUEST_CODE_EDIT_EVENT && resultCode== RESULT_CODE_DELETE_EVENT){
            Event deleteEvent= (Event) data.getExtras().getSerializable("deleteEvent");
            for(int i = 0; i < adapterEvents.getCount(); i++) {
                Event event = adapterEvents.getItem(i);
                if(event.getId()==deleteEvent.getId()) {
                    adapterEvents.remove(event);
                    Toast.makeText(MainActivity.this, " Item excluido com sucesso!", Toast.LENGTH_LONG).show();
                    break;
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

