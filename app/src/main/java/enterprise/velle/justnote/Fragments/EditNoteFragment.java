package enterprise.velle.justnote.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import enterprise.velle.justnote.Adapters.SelectNoteCategorySpinnerAdapter;
import enterprise.velle.justnote.CustomItems.NavigationItem;
import enterprise.velle.justnote.CustomItems.NoteItem;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.RequestCodes;
import enterprise.velle.justnote.SqlLiteDB.SqlLiteDatabaseAdapter;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Edit note fragment.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class EditNoteFragment extends Fragment {

    private EditText title, content;
    private Spinner category;
    private SqlLiteDatabaseAdapter dbAdapter;
    private int noteID;
    private NoteItem n_item;
    private List<NavigationItem> spinnerItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new SqlLiteDatabaseAdapter(getContext());
        setHasOptionsMenu(true);
        noteID = getArguments().getInt("noteID", -1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_edit_note_layout_activity, container, false);

        n_item = dbAdapter.getNoteItem(dbAdapter, noteID);
        spinnerItems = dbAdapter.getMenusForSpinner(dbAdapter);

        int index = 0;
        if(n_item.getCategory().equals("Work notes")) index = 1;
        else if(n_item.getCategory().equals("Travel notes")) index = 2;
        else if(n_item.getCategory().equals("Quotes")) index = 3;

        title = (EditText) v.findViewById(R.id.editTextTitleAddEditActivity);
        content = (EditText) v.findViewById(R.id.editTextContentAddEditActivity);
        category = (Spinner) v.findViewById(R.id.spinnerAddEditActivity);
        SelectNoteCategorySpinnerAdapter spinnerAdapter = new SelectNoteCategorySpinnerAdapter(getActivity(), spinnerItems);
        category.setAdapter(spinnerAdapter);
        category.setSelection(index);
        title.setText(n_item.getTitle());
        content.setText(n_item.getContent());

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_note, menu);
        inflater.inflate(R.menu.menu_read_note, menu);
        inflater.inflate(R.menu.menu_main_add_create_read_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuActionDeleteNoteReadNoteActivivity && noteID != -1){
            dbAdapter.deleteNoteFromDb(dbAdapter, noteID, n_item.getCategory());
            Toast.makeText(getContext(), "Moved to Trash", Toast.LENGTH_SHORT).show();

            if (getActivity() != null) {
                getActivity().setResult(RequestCodes.RESULT_CODE_OK, null);
                getActivity().finish();
            }
            return true;
        } else if(item.getItemId() == R.id.actionSaveCreatedNoteMenuActivity){

            if(TitleValidation()) {
                NavigationItem selectedItem = (NavigationItem) category.getSelectedItem();
                long id = dbAdapter.updateNoteItem(dbAdapter, noteID, title.getText().toString(), content.getText().toString(), selectedItem.getText().toString());
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                if (getActivity() != null) {
                    getActivity().setResult(RequestCodes.RESULT_CODE_OK, null);
                    getActivity().finish();
                }
            }
            return true;
        } else if(item.getItemId() == android.R.id.home){
            getActivity().finish();
            return true;
        }
        return false;
    }
    private boolean TitleValidation(){
        if(title.getText().toString().isEmpty()) {
            title.setError("Title field should not be empty.");
            return false;
        }
        if(content.getText().length() <= 0){
            content.setError("Content field should not be empty. ");
            return false;
        }
        return true;
    }
}
