package enterprise.velle.justnote.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import enterprise.velle.justnote.FragmentDrawer;
import enterprise.velle.justnote.Fragments.AllNoteFragment;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.RequestCodes;
import enterprise.velle.justnote.SharedPreferencesSettings.SettingsSharedPreferencesAdapter;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Main Activity.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private FragmentDrawer mDrawer;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout drawerLayout;
    private FloatingActionButton action_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_note_activity_layout);

        toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle("Just Note");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragmentMenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.fragmentDrawer);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset/2);
            }
        };
        drawerLayout.setDrawerListener(mToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mToggle.syncState();
            }
        });
        mDrawer.setDrawerLayout(drawerLayout);

        action_button = (FloatingActionButton) findViewById(R.id.floating_button_add_note);
        action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating bundle
                Bundle bundle = new Bundle();
                bundle.putString("typeOfCustomization", "CREATE_NEW_NOTE");

                // creating intent
                Intent intent = new Intent(MainActivity.this, AddEditReadNoteActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, RequestCodes.REQUEST_CODE_FOR_ADD_NOTE);
            }
        });

        Fragment fragment = new AllNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("note_category", "All notes");
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, "All notes")
                .commit();

    }

    @Override
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

        mDrawer.getAdapter().notifyDataSetChanged();

        action_button.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, SettingsSharedPreferencesAdapter.actionBarColors[Integer.parseInt(colorIndex)])));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if(requestCode == RequestCodes.REQUEST_CODE_FOR_ADD_NOTE && resultCode == RequestCodes.RESULT_CODE_OK){
            fragment.onActivityResult(requestCode, resultCode, data);
        } else if(requestCode == RequestCodes.REQUEST_CODE_FOR_DELETE_NOTE && resultCode == RequestCodes.RESULT_CODE_OK){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void setFragment(Fragment f, String title){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, f, title).commit();
    }
}
