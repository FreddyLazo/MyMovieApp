package freddy.mymovieapp.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Freddy on 21/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class PreferencesHelper {

    public static void setCachePreference(Context context, int sortMethod) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(sortMethod), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(String.valueOf(sortMethod), true).apply();
    }

    public static boolean getCachePreference(Context context,int sortMethod) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                String.valueOf(sortMethod), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(String.valueOf(sortMethod) ,false);
    }
}
