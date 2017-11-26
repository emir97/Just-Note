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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import enterprise.velle.justnote.Adapters.SelectNoteCategorySpinnerAdapter;
import enterprise.velle.justnote.CustomItems.NavigationItem;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.RequestCodes;
import enterprise.velle.justnote.SqlLiteDB.SqlLiteDatabaseAdapter;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Create note fragment.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class CreateEditNoteFragment extends Fragment {

    private Spinner spinner;
    private EditText titleEditText, contentEditText;
    private SqlLiteDatabaseAdapter dbAdapter;
    private List<NavigationItem> spinnerItems;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dbAdapter = new SqlLiteDatabaseAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_edit_note_layout_activity, container, false);

        titleEditText = (EditText) v.findViewById(R.id.editTextTitleAddEditActivity);
        contentEditText = (EditText) v.findViewById(R.id.editTextContentAddEditActivity);
        spinnerItems = dbAdapter.getMenusForSpinner(dbAdapter);
        spinner = (Spinner) v.findViewById(R.id.spinnerAddEditActivity);
        SelectNoteCategorySpinnerAdapter spinnerAdapter = new SelectNoteCategorySpinnerAdapter(getActivity(), spinnerItems);
        spinner.setAdapter(spinnerAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.actionSaveCreatedNoteMenuActivity){
            if(TitleValidation()){
                    NavigationItem selectedItem = (NavigationItem) spinner.getSelectedItem();
                    long id = dbAdapter.putNoteInDb(dbAdapter, titleEditText.getText().toString(), contentEditText.getText().toString(), selectedItem.getText());
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("note_id", id);
                    intent.putExtra("note_title", titleEditText.getText().toString());
                    intent.putExtra("note_content", contentEditText.getText().toString());
                    intent.putExtra("note_category", selectedItem.getText());

                    if(getActivity() != null){
                        getActivity().setResult(RequestCodes.RESULT_CODE_OK, intent);
                        getActivity().finish();
                    }

                return true;
            }
        }
        return false;
    }

    private boolean TitleValidation(){
        if(titleEditText.getText().toString().isEmpty()) {
            titleEditText.setError("Title field should not be empty.");
            return false;
        }
        if(contentEditText.getText().length() <= 0){
            contentEditText.setError("Content field should not be empty. ");
            return false;
        }
        return true;
    }
}
