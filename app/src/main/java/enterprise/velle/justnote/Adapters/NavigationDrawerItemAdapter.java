package enterprise.velle.justnote.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import enterprise.velle.justnote.CustomItems.NavigationItem;
import enterprise.velle.justnote.Interfaces.OnDrawerClickListener;
import enterprise.velle.justnote.R;
import enterprise.velle.justnote.SharedPreferencesSettings.SettingsSharedPreferencesAdapter;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Adapter for navigation drawer.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class NavigationDrawerItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NavigationItem> nav_items;
    private OnDrawerClickListener listener;
    private ImageView lastChackedImage;
    private TextView lastCheckedTextView;
    private Context context;

    public NavigationDrawerItemAdapter(List<NavigationItem> items, OnDrawerClickListener listener, Context context){
        nav_items = items;
        //this.manager = manager;
        this.listener = listener;
        this.context = context;
        lastChackedImage = null;
        lastCheckedTextView = null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new SectionAboutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_about_item, parent, false));
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_custom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String colorIndex = preferences.getString(SettingsSharedPreferencesAdapter.APPLICATION_ACTIONBAR_COLOR, "0");
        int color = ContextCompat.getColor(context, SettingsSharedPreferencesAdapter.actionBarColors[Integer.parseInt(colorIndex)]);

        if(holder.getItemViewType() != 0){
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.setItemText(nav_items.get(position - 1).getText());
            itemHolder.setItemImage(nav_items.get(position - 1).getIcon());
            if(position == 1 && lastChackedImage == null && lastCheckedTextView == null){
                itemHolder.getImageView().setColorFilter(color);
                itemHolder.getItemText().setTextColor(color);

                lastCheckedTextView = itemHolder.getItemText();
                lastChackedImage = itemHolder.getImageView();
            } else if(lastChackedImage != null && lastCheckedTextView != null){
                lastCheckedTextView.setTextColor(color);
                lastChackedImage.setColorFilter(color);
            }

            itemHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    String colorIndex = preferences.getString(SettingsSharedPreferencesAdapter.APPLICATION_ACTIONBAR_COLOR, "0");
                    int color = ContextCompat.getColor(context, SettingsSharedPreferencesAdapter.actionBarColors[Integer.parseInt(colorIndex)]);
                    listener.onDrawerItemSelected(view, position, nav_items.get(position-1).getText());
                    if(lastChackedImage != null){
                        if(!nav_items.get(position-1).getText().equals("Settings") && !nav_items.get(position-1).getText().equals("About App")){
                            lastChackedImage.setColorFilter(ContextCompat.getColor(context, R.color.defaultNavigationItemsTextColor));
                            lastCheckedTextView.setTextColor(ContextCompat.getColor(context, R.color.defaultNavigationItemsTextColor));
                        }
                    }
                    if(!nav_items.get(position-1).getText().equals("Settings") && !nav_items.get(position-1).getText().equals("About App")){
                        itemHolder.getImageView().setColorFilter(color);
                        itemHolder.getItemText().setTextColor(color);
                        lastChackedImage = itemHolder.getImageView();
                        lastCheckedTextView = itemHolder.getItemText();
                    }


                }
            });
        } else {
            SectionAboutViewHolder itemHolder = (SectionAboutViewHolder) holder;
            itemHolder.getLayout().setBackgroundColor(color);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return nav_items.size() + 1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView itemText;
        private ImageView imageView;
        private View v;
        public ItemViewHolder(View view){
            super(view);
            v = view;
            itemText = (TextView) view.findViewById(R.id.itemText);
            imageView = (ImageView) view.findViewById(R.id.logoTitle);
        }
        public View getView(){
            return v;
        }
        public void setItemText(String text){
            itemText.setText(text);
        }
        public void setItemImage(int drawable){
            imageView.setImageResource(drawable);
        }

        public TextView getItemText() {
            return itemText;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    class SectionAboutViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout layout;
        public SectionAboutViewHolder(View view){
            super(view);
            layout = (RelativeLayout) view.findViewById(R.id.nav_header_container);
        }
        public RelativeLayout getLayout(){
            return layout;
        }
    }

}
