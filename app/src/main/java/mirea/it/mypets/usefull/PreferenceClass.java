package mirea.it.mypets.usefull;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceClass {
    public static final String PREFERENCE_NAME = "PREFERENCE_DATA";
    private final SharedPreferences sharedpreferences;

    public PreferenceClass(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public int getCount(String userID) {
        return sharedpreferences.getInt(userID, 0);
    }

    public void setCount(int count, String userId) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(userId, count);
        editor.apply();
    }

    public void clearCount(String userID) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(userID);
        editor.apply();
    }
}