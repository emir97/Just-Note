package enterprise.velle.justnote.SharedPreferencesSettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import enterprise.velle.justnote.R;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>SharedPreferences adapter.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class SettingsSharedPreferencesAdapter {

    public static final String IS_FIRST_RUN = "is_first_run_simplenote";
    public static final String APPLICATION_ACTIONBAR_COLOR = "application_actionbar_color";
    public static final String SHARED_PREFERENCE_NAME = "Settings";

    public static int [] actionBarColors = {
            R.color.defaultActionBarColor,
            R.color.colorPinkActionBar,
            R.color.colorPurpleActionBar,
            R.color.colorIndigoActionBar,
            R.color.colorBlueActionBar,
            R.color.colorCyanActionBar,
            R.color.colorYellowActionBar,
            R.color.colorBlueGreyActionBar,
            R.color.colorDeepPurpleActionBar
    };

    public static int [] statusBarColors = {
            R.color.defaultStatusBarColor,
            R.color.colorPinkStatusBarColor,
            R.color.colorPurpleStatusBarColor,
            R.color.colorIndigoStatusBarColor,
            R.color.colorBlueStatusBarColor,
            R.color.colorCyanStatusBarColor,
            R.color.colorYellowStatusBarColor,
            R.color.colorBlueGreyStatusBarColor,
            R.color.colorDeepPurpleStatusBarColor
    };

    public static int [] circle_background = {
            R.drawable.bg_circle_default_red_color,
            R.drawable.bg_circle_pink_color,
            R.drawable.bg_circle_purple_color,
            R.drawable.bg_circle_indigo_color,
            R.drawable.bg_circle_blue_color,
            R.drawable.bg_circle_cyan_color,
            R.drawable.bg_circle_yellow_color,
            R.drawable.bg_circle_blue_gray_color,
            R.drawable.bg_circle_deep_purple_color
    };

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SettingsSharedPreferencesAdapter(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, true);
    }

    public void putBoolean(String key, boolean value){
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
