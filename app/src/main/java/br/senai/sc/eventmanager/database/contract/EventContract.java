package br.senai.sc.eventmanager.database.contract;

import br.senai.sc.eventmanager.database.entity.EventEntity;

public class EventContract {
    public static final String createTable() {
        return "CREATE TABLE " + EventEntity.TABLE_NAME + " (" +
                EventEntity._ID + " INTEGER PRIMARY KEY, " +
                EventEntity.COLUMN_NAME_NAME + " TEXT, " +
                EventEntity.COLUMN_NAME_LOCATION + " TEXT, " +
                EventEntity.COLUMN_NAME_DATE + " TEXT)";

    }
    public static final String removeTable() {
        return "DROP TABLE IF EXISTS " + EventEntity.TABLE_NAME;

    }
}
