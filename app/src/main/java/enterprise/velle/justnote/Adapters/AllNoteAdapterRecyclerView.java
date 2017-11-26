package enterprise.velle.justnote.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import enterprise.velle.justnote.Activities.AddEditReadNoteActivity;
import enterprise.velle.justnote.CustomItems.NoteItem;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.RequestCodes;
import enterprise.velle.justnote.SharedPreferencesSettings.SettingsSharedPreferencesAdapter;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Adapter for view notes.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class AllNoteAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NoteItem> items;
    private Context context;

    public AllNoteAdapterRecyclerView(List<NoteItem> items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String colorIndex = preferences.getString(SettingsSharedPreferencesAdapter.APPLICATION_ACTIONBAR_COLOR, "0");

        NoteViewHolder viewHolder = (NoteViewHolder) holder;
        viewHolder.getTextTitle().setText(items.get(position).getTitle());
        viewHolder.getTextContent().setText(items.get(position).getContent());
        viewHolder.getTextFirstLetter().setText(String.valueOf(items.get(position).getTitle().charAt(0)).toUpperCase());
        viewHolder.getLayout().setBackground(ContextCompat.getDrawable(context, SettingsSharedPreferencesAdapter.circle_background[Integer.parseInt(colorIndex)]));
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("typeOfCustomization", "READ_NOTE");
                bundle.putInt("note_id", items.get(position).getID());

                Intent intent = new Intent(context, AddEditReadNoteActivity.class);
                intent.putExtras(bundle);

                ((Activity) context).startActivityForResult(intent, RequestCodes.REQUEST_CODE_FOR_DELETE_NOTE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle, textContent, textFirstLetter;
        private RelativeLayout layout;
        public LinearLayout linearLayout;

        public NoteViewHolder(View v){
            super(v);

            textTitle = (TextView) v.findViewById(R.id.noteTitle);
            textContent = (TextView) v.findViewById(R.id.noteContent);
            textFirstLetter = (TextView) v.findViewById(R.id.noteFirstLetter);
            layout = (RelativeLayout) v.findViewById(R.id.circleNoteImage);
            linearLayout = (LinearLayout) v.findViewById(R.id.recyclerViewNoteRow);
        }

        public TextView getTextTitle() {
            return textTitle;
        }

        public TextView getTextContent() {
            return textContent;
        }

        public TextView getTextFirstLetter() {
            return textFirstLetter;
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public View getView() {
            return linearLayout;
        }
    }
}
