package enterprise.velle.justnote.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import enterprise.velle.justnote.CustomItems.NavigationItem;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.SqlLiteDB.SqlLiteDatabaseAdapter;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Adapter for add notes.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class AddDeleteNoteLabelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NavigationItem> items;
    private Context context;
    private SqlLiteDatabaseAdapter dbAdapter;
    public AddDeleteNoteLabelAdapter(List<NavigationItem> items, Context context, SqlLiteDatabaseAdapter dbAdapter){
        this.items = items;
        this.context = context;
        this.dbAdapter = dbAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new ViewHolderAddNote(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_add_note_label_row_layout, parent, false));
        return new ViewHolderEditNote(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_modified_note_label_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == 0){
           final ViewHolderAddNote addNote = (ViewHolderAddNote) holder;
            addNote.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNote.getLinearLayout().setEnabled(false);
                    String text = addNote.getEditText().getText().toString();
                    if(text.isEmpty()){
                        addNote.getEditText().setError("This field should not be empty!");
                    } else {
                        int id = (int) dbAdapter.putMenuItemIntoDb(dbAdapter, text, R.drawable.ic_label_black_24dp, true, 2);
                        items.add(0, new NavigationItem(text, R.drawable.ic_label_black_24dp, id));
                        notifyDataSetChanged();
                    }
                    addNote.getLinearLayout().setEnabled(true);
                }
            });

        } else {
           final ViewHolderEditNote editNote = (ViewHolderEditNote) holder;
           final NavigationItem item = items.get(position);
            editNote.getTextView().setText(item.getText());
            editNote.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editNote.getLinearLayout().setEnabled(false);
                    dbAdapter.deleteMenuItem(dbAdapter, item.getID());
                    items.remove(item);
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
//        return items != null ? items.size()+1 : 0;
        return items.size() + 1;
    }

    public class ViewHolderEditNote extends RecyclerView.ViewHolder{

        private TextView noteTitle;
        private LinearLayout noteBtnDelete;
        public ViewHolderEditNote(View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.tv_ModifiedNoteLabel_note_title);
            noteBtnDelete = (LinearLayout) itemView.findViewById(R.id.ll_btn_delete_note_label);
        }

        public TextView getTextView(){return noteTitle;}
        public LinearLayout getLinearLayout(){return noteBtnDelete;}
    }
    public class ViewHolderAddNote extends RecyclerView.ViewHolder{

        private EditText noteTitle;
        private LinearLayout noteBtnAdd;
        public ViewHolderAddNote(View itemView) {
            super(itemView);
            noteTitle = (EditText) itemView.findViewById(R.id.et_add_new_note_label);
            noteBtnAdd = (LinearLayout) itemView.findViewById(R.id.ll_btn_delete_note_label);
        }

        public EditText getEditText(){return noteTitle;}
        public LinearLayout getLinearLayout(){return noteBtnAdd;}
    }
}
