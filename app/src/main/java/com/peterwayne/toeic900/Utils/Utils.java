package com.peterwayne.toeic900.Utils;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import com.google.firebase.auth.FirebaseAuth;

public class Utils {
    public static ColorStateList COLOR_RIGHT_KEY_STATE_LIST = new ColorStateList(
            new int[][]
                    {
                            new int[]{-android.R.attr.state_enabled}, // Disabled
                            new int[]{android.R.attr.state_enabled}   // Enabled
                    },
            new int[]
                    {
                            Color.BLACK, // disabled
                            Color.GREEN   // enabled
                    }
    );
    public static ColorStateList COLOR_WRONG_KEY_STATE_LIST = new ColorStateList(
            new int[][]
                    {
                            new int[]{-android.R.attr.state_enabled}, // Disabled
                            new int[]{android.R.attr.state_enabled}   // Enabled
                    },
            new int[]
                    {
                            Color.BLACK, // disabled
                            Color.RED   // enabled
                    }
    );
    public static final int RESULT_VIEW_TYPE = 1;
    public static final int TEST_VIEW_TYPE = 2;
    public static final int ID_PART_ONE_TRAINING = 1;
    public static final int ID_PART_TWO_TRAINING = 2;
    public static final int ID_PART_THREE_TRAINING = 3;
    public static final int ID_PART_FOUR_TRAINING = 4;
    public static final String PART_ID = "PART_ID";
    public static String getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    @SuppressLint("DefaultLocale")
    public static String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);
        buf
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));
        return buf.toString();
    }
}
