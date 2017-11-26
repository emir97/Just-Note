package enterprise.velle.justnote.Activities;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import enterprise.velle.justnote.Fragments.CreateEditNoteFragment;
import enterprise.velle.justnote.Fragments.ReadNoteFragment;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.RequestCodes;
import enterprise.velle.justnote.SharedPreferencesSettings.SettingsSharedPreferencesAdapter;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Activity for Add, Edit, Read note.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class AddEditReadNoteActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_add_edit_layout_activty);

        String type = ((Bundle)getIntent().getExtras()).getString("typeOfCustomization");

        toolbar = (Toolbar) findViewById(R.id.actionBarAddEditLayoutActivity);
        toolbar.setTitle("Create new note");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(getIntent().getExtras() == null)
            finish();

        if(type.equals("CREATE_NEW_NOTE")){
            getSupportFragmentManager().beginTransaction().add(R.id.AddEditNoteactivtyFragment, new CreateEditNoteFragment(), "Create new note").commit();
        } else if(type.equals("READ_NOTE")){
            Fragment fragment = new ReadNoteFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.AddEditNoteactivtyFragment, fragment, "Read note")
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_add_create_read_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            setResult(RequestCodes.RESULT_CODE_CANCEL, null);
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // saved to database and put it in array...
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int actionBarColors = SettingsSharedPreferencesAdapter.actionBarColors[Integer.parseInt(preferences.getString(SettingsSharedPreferencesAdapter.APPLICATION_ACTIONBAR_COLOR, "0"))];
        int statusBarColors = SettingsSharedPreferencesAdapter.statusBarColors[Integer.parseInt(preferences.getString(SettingsSharedPreferencesAdapter.APPLICATION_ACTIONBAR_COLOR, "0"))];
        if(getSupportActionBar() != null){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, actionBarColors)));
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this, statusBarColors));
        }
    }
}
