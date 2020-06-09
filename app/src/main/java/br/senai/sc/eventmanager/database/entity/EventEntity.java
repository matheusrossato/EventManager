package br.senai.sc.eventmanager.database.entity;

import android.provider.BaseColumns;

public class EventEntity implements BaseColumns {

        private EventEntity() {}
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_DATE = "date";

}
