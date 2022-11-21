package com.peterwayne.toeic900.LocalData;

import android.content.Context;

public class SharedRefManager {
    private static final String PREF_IS_FIRST_INSTALL = "PREF_IS_FIRST_INSTALL";
    private static SharedRefManager instance;
    private static MySharedPreferences mySharedPreferences;
    public static void init(Context context)
    {
        instance = new SharedRefManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }
    public static SharedRefManager getInstance()
    {
        if(instance==null) return new SharedRefManager();
        else return instance;
    }
    public static void setFirstInstall(boolean isFirst)
    {
        SharedRefManager.getInstance().mySharedPreferences.putBooleanValue(PREF_IS_FIRST_INSTALL, isFirst);
    }
    public static boolean getFirstInstalled()
    {
        return SharedRefManager.getInstance().mySharedPreferences.getBooleanValue(PREF_IS_FIRST_INSTALL);
    }

}
