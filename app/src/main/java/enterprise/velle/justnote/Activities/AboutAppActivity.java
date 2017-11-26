package enterprise.velle.justnote.Activities;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import enterprise.velle.justnote.R;
import enterprise.velle.justnote.SharedPreferencesSettings.SettingsSharedPreferencesAdapter;
/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Activity for "AboutApp" layout.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar_about_app);
        toolbar.setTitle("About App");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String colorIndex = preferences.getString(SettingsSharedPreferencesAdapter.APPLICATION_ACTIONBAR_COLOR, "0");
        if(getSupportActionBar() != null){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, SettingsSharedPreferencesAdapter.actionBarColors[Integer.parseInt(colorIndex)])));
        }
        if(Build.VERSION.SDK_INT > 20){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, SettingsSharedPreferencesAdapter.statusBarColors[Integer.parseInt(colorIndex)]));
        }
    }
}
