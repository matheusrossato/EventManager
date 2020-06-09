package br.senai.sc.eventmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import br.senai.sc.eventmanager.database.contract.EventContract;

public class DatabaseDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.event";
    private static final int DATABASE_VERSION = 1;

    public DatabaseDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventContract.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EventContract.removeTable());
        db.execSQL(EventContract.createTable());
    }
}