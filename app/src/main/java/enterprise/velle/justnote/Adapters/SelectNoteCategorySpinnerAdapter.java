package enterprise.velle.justnote.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import enterprise.velle.justnote.CustomItems.NavigationItem;
import enterprise.velle.justnote.R;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Adapter for spinner.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class SelectNoteCategorySpinnerAdapter extends BaseAdapter {

    private List<NavigationItem> items;
    private Context context;
    private Activity activity;

    public SelectNoteCategorySpinnerAdapter(Activity activity, List<NavigationItem> items){
        this.context = (Context) activity;
        this.items = items;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_select_category_layout, viewGroup, false);
        }

        TextView textView = (TextView) v.findViewById(R.id.selectCategorySpinnerTitle);
        textView.setText(items.get(i).getText());

        ImageView imageView = (ImageView) v.findViewById(R.id.selectCategorySpinnerImage);
        imageView.setImageResource(items.get(i).getIcon())
        ;
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);
        LinearLayout layout = (LinearLayout) v;
        TextView textView = (TextView) v.findViewById(R.id.selectCategorySpinnerTitle);
        textView.setGravity(Gravity.LEFT);
        ImageView imageView = (ImageView) v.findViewById(R.id.selectCategorySpinnerImage);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        return v;
    }
}
