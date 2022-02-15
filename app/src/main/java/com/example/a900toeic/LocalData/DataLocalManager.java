package com.example.a900toeic.LocalData;

import android.content.Context;

import java.util.Set;

public class DataLocalManager {
    private static final String PREF_IS_FIRST_INSTALL = "PREF_IS_FIRST_INSTALL";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;
    public static void init(Context context)
    {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }
    public static DataLocalManager getInstance()
    {
        if(instance==null) return new DataLocalManager();
        else return instance;
    }
    public static void setFirstInstall(boolean isFirst)
    {
        DataLocalManager.getInstance().mySharedPreferences.putBooleanValue(PREF_IS_FIRST_INSTALL, isFirst);
    }
    public static boolean getFirstInstalled()
    {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(PREF_IS_FIRST_INSTALL);
    }
    public static void addDoneQuestion(String question_id)
    {
        DataLocalManager.getInstance().mySharedPreferences.putBooleanValue(question_id,true);
    }
    public static boolean isDone(String question_id)
    {
        boolean res =  DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(question_id);
        return res;
    }

}
