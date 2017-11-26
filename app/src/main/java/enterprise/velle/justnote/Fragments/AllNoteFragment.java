package enterprise.velle.justnote.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import enterprise.velle.justnote.Activities.MainActivity;
import enterprise.velle.justnote.Adapters.AllNoteAdapterRecyclerView;
import enterprise.velle.justnote.CustomItems.NoteItem;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.RequestCodes;
import enterprise.velle.justnote.SqlLiteDB.SqlLiteDatabaseAdapter;



/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>View note fragment.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class AllNoteFragment extends Fragment {

    private LinearLayoutManager mManager;
    private AllNoteAdapterRecyclerView mAdapter;
    private List<NoteItem> items;
    private RecyclerView recyclerView;
    private SqlLiteDatabaseAdapter dbAdapter;
    private String categoryFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dbAdapter = new SqlLiteDatabaseAdapter(getContext());
        categoryFragment = getArguments().getString("note_category");
        if(getActivity().getActionBar() != null){
            getActivity().getActionBar().setTitle(categoryFragment);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.note_list_fragment, container, false);

        mManager = new LinearLayoutManager(getContext());

        if(categoryFragment.equals("All notes")){
            items = dbAdapter.getAllNotesForCategory(dbAdapter, null);
        }else {
            items = dbAdapter.getAllNotesForCategory(dbAdapter, categoryFragment);
        }
        mAdapter = new AllNoteAdapterRecyclerView(items, getContext());
        if(items == null || items.size() == 0){
            return inflater.inflate(R.layout.there_is_nothing_here, container, false);
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.viewAllNotesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onResume() {
       super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(categoryFragment.equals("Trash")){
            inflater.inflate(R.menu.empty_trash_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.empty_trash && categoryFragment.equals("Trash")){
            if(items != null){
                dbAdapter.emptyTrashDb(dbAdapter);
                items.clear();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RequestCodes.REQUEST_CODE_FOR_ADD_NOTE && resultCode == RequestCodes.RESULT_CODE_OK && data != null){
            String title = data.getStringExtra("note_title");
            String content = data.getStringExtra("note_content");
            String category = data.getStringExtra("note_category");
            int id = (int) data.getLongExtra("note_id", -1);

            if((items == null || items.size() == 0) && (category.equals(categoryFragment) || categoryFragment.equals("All notes"))){
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                return;
            }


            if(items != null && id != -1 && (category.equals(categoryFragment) || categoryFragment.equals("All notes"))){
                items.add(0, new NoteItem(id, title, content));
                mAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode == RequestCodes.REQUEST_CODE_FOR_DELETE_NOTE && resultCode == RequestCodes.RESULT_CODE_OK && data != null){
            String category = data.getStringExtra("note_category");
            int id = data.getIntExtra("note_id", -1);

            if(id != -1){
                for (int i = 0; i<items.size(); i++){
                    if(items.get(i).getID() == id){
                        items.remove(i); break;
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        } else if(requestCode == RequestCodes.REQUEST_CODE_FOR_DELETE_NOTE && resultCode == RequestCodes.RESULT_CODE_OK && data == null){
            if(categoryFragment.equals("All notes")){
                items = dbAdapter.getAllNotesForCategory(dbAdapter, null);
            } else{
                items = dbAdapter.getAllNotesForCategory(dbAdapter, categoryFragment);
            }
            mAdapter = new AllNoteAdapterRecyclerView(items, getContext());
            recyclerView.setAdapter(mAdapter);
        }

    }
}
