package com.example.myapplication.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pc.class}, version = 1, exportSchema = false)
public abstract class AppPcDatabase extends RoomDatabase
{

    private static AppPcDatabase INSTANCE;
    public abstract  PcDao pcDao();

    public static AppPcDatabase  getAppPcDatabase(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppPcDatabase.class,"database-Pc").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public static void destroyINSTANCE()
    {
        INSTANCE = null;
    }
}
