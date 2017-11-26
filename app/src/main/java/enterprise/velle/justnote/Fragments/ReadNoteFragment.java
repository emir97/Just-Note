package enterprise.velle.justnote.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import enterprise.velle.justnote.Activities.AddEditReadNoteActivity;
import enterprise.velle.justnote.CustomItems.NoteItem;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.RequestCodes;
import enterprise.velle.justnote.SqlLiteDB.SqlLiteDatabaseAdapter;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Read note fragment.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class ReadNoteFragment extends Fragment {

    private SqlLiteDatabaseAdapter mAdapterDb;
    private TextView textViewTitleNote, textViewContentNote, textViewCategoryNote;
    private int noteID;
    private NoteItem noteItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapterDb = new SqlLiteDatabaseAdapter(getContext());
        noteID = getArguments().getInt("note_id", -1);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.read_note_fragment, container, false);

        textViewTitleNote = (TextView) view.findViewById(R.id.textViewTitleReadNoteAddEditReadNoteActivity);
        textViewContentNote = (TextView) view.findViewById(R.id.textViewContentReadNoteAddEditReadNoteActivity);
        textViewCategoryNote = (TextView) view.findViewById(R.id.textViewCategoryReadNoteActivity);

        noteItem = mAdapterDb.getNoteItem(mAdapterDb, noteID);
        if(noteItem == null) return inflater.inflate(R.layout.there_is_nothing_here, container, false);
        if(getActivity() != null && ((AddEditReadNoteActivity) getActivity()).getSupportActionBar() != null){
            ((AddEditReadNoteActivity) getActivity()).getSupportActionBar().setTitle(noteItem.getTitle());
        }
        textViewTitleNote.setText(noteItem.getTitle());
        textViewContentNote.setText(noteItem.getContent());
        textViewCategoryNote.setText(noteItem.getCategory());
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_read_note, menu);
        if(noteItem.getCategory().equals("Trash")){
            inflater.inflate(R.menu.menu_read_activity_trash, menu);
        }
        if(!noteItem.getCategory().equals("Trash")){
            inflater.inflate(R.menu.edit_note, menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuActionDeleteNoteReadNoteActivivity && noteItem.getCategory().equals("Trash")) {
            mAdapterDb.deleteNoteForeverFromDb(mAdapterDb, noteID);
            Intent intent = new Intent();
            intent.putExtra("note_id", noteID);
            intent.putExtra("note_category", noteItem.getCategory());

            getActivity().setResult(RequestCodes.RESULT_CODE_OK, intent);
            Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
            if (getActivity() != null)
                getActivity().finish();
            return true;
        }
        else if(item.getItemId() == R.id.menuActionDeleteNoteReadNoteActivivity){
            mAdapterDb.deleteNoteFromDb(mAdapterDb, noteID, noteItem.getCategory());
            Intent intent = new Intent();
            intent.putExtra("note_id", noteID);
            intent.putExtra("note_category", noteItem.getCategory());

            getActivity().setResult(RequestCodes.RESULT_CODE_OK, intent);
            Toast.makeText(getContext(), "Moved to Trash", Toast.LENGTH_SHORT).show();
            if(getActivity() != null)
                getActivity().finish();
            return true;
        }else if(item.getItemId() == R.id.restoreNoteActionReadActivity){
            if(mAdapterDb.restoreNote(mAdapterDb, noteItem.getID())){
                Intent intent = new Intent();
                intent.putExtra("note_id", noteID);
                intent.putExtra("note_category", noteItem.getCategory());

                getActivity().setResult(RequestCodes.RESULT_CODE_OK, intent);
                Toast.makeText(getContext(), "Restored", Toast.LENGTH_SHORT).show();
                if(getActivity() != null)
                    getActivity().finish();
                return true;
            }
            Toast.makeText(getContext(), "Not restored", Toast.LENGTH_SHORT).show();
            return true;
        } else if(item.getItemId() == R.id.menuActionEditeNoteReadNoteActivivity){
            Bundle bundle = new Bundle();
            bundle.putInt("noteID", noteID);
            Fragment fragment = new EditNoteFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(
                    R.id.AddEditNoteactivtyFragment,
                    fragment,
                    "Edit note"
            ).commit();
        }
        return false;
    }
}
