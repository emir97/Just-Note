package enterprise.velle.justnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import enterprise.velle.justnote.Activities.AboutAppActivity;
import enterprise.velle.justnote.Activities.SettingsActivity;
import enterprise.velle.justnote.Adapters.NavigationDrawerItemAdapter;
import enterprise.velle.justnote.CustomItems.NavigationItem;
import enterprise.velle.justnote.Fragments.AllNoteFragment;
import enterprise.velle.justnote.Interfaces.OnDrawerClickListener;
import enterprise.velle.justnote.SharedPreferencesSettings.SettingsSharedPreferencesAdapter;
import enterprise.velle.justnote.SqlLiteDB.SqlLiteDatabaseAdapter;

/**
 * Created by Emir on 18.9.2017.
 */
public class FragmentDrawer extends Fragment implements OnDrawerClickListener{

    private RecyclerView recyclerView;
    private NavigationDrawerItemAdapter adapter;
    private DrawerLayout mDrawer;
    private List<NavigationItem> items;

    public void setDrawerLayout(DrawerLayout mDrawer){
        this.mDrawer = mDrawer;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        items = new ArrayList<>();
        SqlLiteDatabaseAdapter dbAdapter = new SqlLiteDatabaseAdapter(getContext());
        SettingsSharedPreferencesAdapter sharedPreferencesAdapter = new SettingsSharedPreferencesAdapter(getContext());

        if(sharedPreferencesAdapter.getBoolean(SettingsSharedPreferencesAdapter.IS_FIRST_RUN)){
            sharedPreferencesAdapter.putBoolean(SettingsSharedPreferencesAdapter.IS_FIRST_RUN, false);
            putMenuItemsInMenu(dbAdapter);
        }

        items = dbAdapter.getAllMenuItems(dbAdapter);

        View v = inflater.inflate(R.layout.navigation_drawer_menu_layout,container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.drawerRecyclerViewItem);
        adapter = new NavigationDrawerItemAdapter(items, this, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return v;
    }

    public void setFragment(Fragment f, String title){
        getFragmentManager().beginTransaction().replace(R.id.mainContainer, f, title).commit();
    }
    @Override
    public void onDrawerItemSelected(View view, int position, String fragmentName) {

        if(position >= 1 && position < items.size()-1){
            Bundle bundle = new Bundle();
            bundle.putString("note_category", fragmentName);
            Fragment fragment = new AllNoteFragment();
            fragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, fragment, fragmentName)
                    .commit();
        }
        if(position == items.size()-1){
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }
        if(position == items.size()){
            startActivity(new Intent(getActivity(), AboutAppActivity.class));
        }
        if(mDrawer.isDrawerOpen(GravityCompat.START))
            mDrawer.closeDrawer(GravityCompat.START);
    }

    public void putMenuItemsInMenu(SqlLiteDatabaseAdapter dbAdapter){
        dbAdapter.putMenuItemIntoDb(dbAdapter, "All notes", R.drawable.ic_note_black_24dp, false, 1);
        dbAdapter.putMenuItemIntoDb(dbAdapter, "Personal notes", R.drawable.ic_person_black_24dp, false, 1);
        dbAdapter.putMenuItemIntoDb(dbAdapter, "Work notes", R.drawable.ic_work_black_24dp, false, 1);
        dbAdapter.putMenuItemIntoDb(dbAdapter, "Travel notes", R.drawable.ic_card_travel_black_24dp, false, 1);
        dbAdapter.putMenuItemIntoDb(dbAdapter, "Quotes", R.drawable.ic_format_quote_black_24dp, false, 1);
        dbAdapter.putMenuItemIntoDb(dbAdapter, "Trash", R.drawable.ic_delete_black_24dp, false, 3);
        dbAdapter.putMenuItemIntoDb(dbAdapter, "Settings", R.drawable.ic_settings_black_24dp, false, 3);
        dbAdapter.putMenuItemIntoDb(dbAdapter, "About App", R.drawable.ic_android_black_24dp, false, 3);
    }
    public NavigationDrawerItemAdapter getAdapter(){
        return adapter;
    }
}
