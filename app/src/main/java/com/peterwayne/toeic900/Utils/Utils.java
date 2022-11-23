package com.peterwayne.toeic900.Utils;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static final int ID_REVIEW_TRAINING = 0;
    public static final int ID_PART_ONE_TRAINING = 1;
    public static final int ID_PART_TWO_TRAINING = 2;
    public static final int ID_PART_THREE_TRAINING = 3;
    public static final int ID_PART_FOUR_TRAINING = 4;
    public static final String PART_ID = "PART_ID";
    public static final int NUMBER_QUESTION_TRAINING = 5;
    public static final float ANSWER_STATE_CHOSEN = 0.9f;
    public static final float ANSWER_STATE_FADE = 0.3f;
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
    public static int getCurrentDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    public static String getCurrentDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());

    }
}
