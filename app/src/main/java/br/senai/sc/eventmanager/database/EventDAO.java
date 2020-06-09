package br.senai.sc.eventmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.eventmanager.database.entity.EventEntity;
import br.senai.sc.eventmanager.models.Event;


public class EventDAO {
    private DBGateway dbGateway;
    public String orderByName = "ASC";
    private final String SQL_LIST_ALL = "SELECT * FROM " + EventEntity.TABLE_NAME + " ORDER BY " + EventEntity.COLUMN_NAME_NAME + " ";
    private final String SQL_LIST_SEARCH = "SELECT * FROM " + EventEntity.TABLE_NAME + " WHERE " + EventEntity.COLUMN_NAME_NAME + " LIKE ? " + "ORDER BY " + EventEntity.COLUMN_NAME_NAME + " ";
    //private final String SQL_LIST_SEARCH_WHERE =  ";

    public EventDAO(Context context){
        dbGateway = DBGateway.getInstance(context);
    }
    public boolean save(Event event){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventEntity.COLUMN_NAME_NAME, event.getName());
        contentValues.put(EventEntity.COLUMN_NAME_LOCATION, event.getLocation());
        contentValues.put(EventEntity.COLUMN_NAME_DATE, event.getDate());
        if (event.getId() > 0) {
            return dbGateway.getDatabase().update(EventEntity.TABLE_NAME,
                    contentValues,EventEntity._ID + "=?",
                    new String[]{String.valueOf(event.getId())}) >0;
        }
        return dbGateway.getDatabase().insert(EventEntity.TABLE_NAME, null, contentValues) >0;
    }

    public int delete(Event event){
        if (event.getId() > 0) {
            int rowsDeleted =  dbGateway.getDatabase().delete(EventEntity.TABLE_NAME,
                    EventEntity._ID + "=?",
                    new String[]{String.valueOf(event.getId())});
            if (rowsDeleted>0) {
                return rowsDeleted;
            }

        }
        return 0;
    }

    public List<Event> list(boolean nameOrderByAsc){
        List<Event> events = new ArrayList<>();
        if (!nameOrderByAsc){
            orderByName = "DESC";
        } else {
            orderByName = "ASC";
        }

        System.out.println("SQL LIST ALL:");
        System.out.println(SQL_LIST_ALL + orderByName);
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LIST_ALL + orderByName, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String name = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NAME));
            String location = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_LOCATION));
            String date = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATE));
            events.add(new Event(id, name, location, date));
        }
        cursor.close();
        return events;
    }

    public List<Event> search(String searchString, boolean nameOrderByAsc){
        List<Event> events = new ArrayList<>();
        if (!nameOrderByAsc){
            orderByName = "DESC";
        }
        else {
            orderByName = "ASC";
        }
        System.out.println("SQL SEARCH:");
        System.out.println(SQL_LIST_SEARCH + orderByName);
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LIST_SEARCH + orderByName,new String[]{searchString});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String name = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NAME));
            String location = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_LOCATION));
            String date = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATE));
            events.add(new Event(id, name, location, date));
        }
        cursor.close();
        return events;
    }
}
