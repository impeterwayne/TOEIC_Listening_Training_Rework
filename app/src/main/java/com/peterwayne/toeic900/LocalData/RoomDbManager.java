package com.peterwayne.toeic900.LocalData;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {QuestionPartOneStatus.class,
                    QuestionPartTwoStatus.class,
                    QuestionPartThreeAndFourStatus.class},
                    version = 1)
public abstract class RoomDbManager extends RoomDatabase {

    private static final String DATABASE_NAME = "status.db";
    private static RoomDbManager instance;
    public static synchronized RoomDbManager getInstance(Context context) {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDbManager.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract StatusDAO statusDAO();

}
