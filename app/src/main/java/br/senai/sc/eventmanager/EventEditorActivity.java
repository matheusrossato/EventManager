package br.senai.sc.eventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import br.senai.sc.eventmanager.models.Event;

public class EventEditorActivity extends AppCompatActivity {

    private final int RESULT_CODE_CREATE_EVENT = 10;
    private final int RESULT_CODE_EDIT_EVENT = 20;
    private final int RESULT_CODE_DELETE_EVENT = 30;
    private boolean editing = false;
    private int id = 0;
    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editor);
        setTitle("Cadastro de eventos");
        final EditText editTextDate = findViewById(R.id.editText_date);
        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {
                if (b) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datePicker = new DatePickerDialog(EventEditorActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }}
        });
        loadEvent();
    }

    private void loadEvent() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("editEvent") != null ) {
            Toast.makeText(EventEditorActivity.this,"Carregando evento!", Toast.LENGTH_SHORT).show();
            Event event = (Event) intent.getExtras().get("editEvent");
            EditText editTextName = findViewById(R.id.editText_name);
            EditText editTextLocation = findViewById(R.id.editText_location);
            final EditText editTextDate = findViewById(R.id.editText_date);

            editTextName.setText(event.getName());
            editTextLocation.setText(event.getLocation());
            editTextDate.setText(event.getDate());
            editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean b) {
                    if (b) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        datePicker = new DatePickerDialog(EventEditorActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    }
                                }, year, month, day);
                        datePicker.show();
                    }}
            });

            editing = true;
            id = event.getId();
        }
    }
    public void onClickBack(View v) {
        finish();
    }

    public void onClickCreateEvent(View v) {
        EditText editTextName = findViewById(R.id.editText_name);
        EditText editTextLocation = findViewById(R.id.editText_location);
        EditText editTextDate = findViewById(R.id.editText_date);

        String name = editTextName.getText().toString();
        String location = editTextLocation.getText().toString();
        String date = editTextDate.getText().toString();

        Event event = new Event(id, name, location, date);
        Intent intent = new Intent();
        boolean validated = validate(name, location, date);
        if (validated == true) {
            if (editing) {
                intent.putExtra("editEvent", event);
                setResult(RESULT_CODE_EDIT_EVENT, intent);
            }
            else {
                intent.putExtra("createEvent", event);
                setResult(RESULT_CODE_CREATE_EVENT, intent);
            }
            finish();
        }

    }
    public void onClickDeleteEvent(View v) {
        EditText editTextName = findViewById(R.id.editText_name);
        EditText editTextLocation = findViewById(R.id.editText_location);
        EditText editTextDate = findViewById(R.id.editText_date);

        String name = editTextName.getText().toString();
        String location = editTextLocation.getText().toString();
        String date = editTextDate.getText().toString();

        Event event = new Event(id, name, location, date);
        Intent intent = new Intent();
        if (editing) {
            intent.putExtra("deleteEvent", event);
            setResult(RESULT_CODE_DELETE_EVENT, intent);
        }
        finish();
    }
    public boolean validate(String name, String location, String date) {

        if (name != null && !name.isEmpty() && location !=null && !location.isEmpty() && date != null && !date.isEmpty()) {
            return true;
        } else {
            Toast.makeText(EventEditorActivity.this, "Nome do evento: " + name + ", local: "+ location + " e data: " + date + " são obrigatórios!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}