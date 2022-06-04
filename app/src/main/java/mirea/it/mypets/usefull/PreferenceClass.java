package mirea.it.mypets.usefull;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceClass {
    public static final String PREFERENCE_NAME = "PREFERENCE_DATA";
    private final SharedPreferences sharedpreferences;

    public PreferenceClass(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public int getCount() {
        return sharedpreferences.getInt("count", 0);
    }

    public void setCount(int count) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("count", count);
        editor.apply();
    }

    public void clearCount() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("count");
        editor.apply();
    }
}