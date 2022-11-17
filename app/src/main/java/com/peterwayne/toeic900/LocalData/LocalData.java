package com.peterwayne.toeic900.LocalData;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {QuestionPartOneStatus.class,
                    QuestionPartTwoStatus.class,
                    QuestionPartThreeAndFourStatus.class},
                    version = 1)
public abstract class LocalData extends RoomDatabase {

    private static final String DATABASE_NAME = "status.db";
    private static LocalData instance;
    public static synchronized LocalData getInstance(Context context) {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), LocalData.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract StatusDAO statusDAO();

}
