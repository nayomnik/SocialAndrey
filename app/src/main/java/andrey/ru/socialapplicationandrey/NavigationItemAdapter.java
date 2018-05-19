package andrey.ru.socialapplicationandrey;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by User on 4/30/2017.
 */

public class NavigationItemAdapter extends ArrayAdapter {

    private final Resources resources;
    private final float itemHeight;
    private final Typeface typeface;
    Context context;
    Vector<MyRowInNavigationMenu> data;
    private static LayoutInflater inflater = null;

    public NavigationItemAdapter(Context context, Vector<MyRowInNavigationMenu> data, float menuHeightPrm, Typeface typeface) {
        super(context, R.layout.navigation_list_s_item);
        this.typeface = typeface;
        this.context = context;
        resources = context.getResources();
        this.data = data;
        itemHeight = menuHeightPrm / 8;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int positionPrm, View convertView, ViewGroup parentPrm) {
        final FrameLayout vLcl;
        final ViewGroup parent = parentPrm;
        final int position = positionPrm;
        if (convertView == null) {
//            l.a(666);
            vLcl = (FrameLayout) inflater.inflate(R.layout.navigation_list_s_item, null);
        } else
            vLcl = (FrameLayout) convertView;
        vLcl.post(new Runnable() {
            @Override
            public void run() {

                ViewGroup.LayoutParams layoutParamsLcl = vLcl.getLayoutParams();
                layoutParamsLcl.height = (int) (itemHeight * 1.3f);
                vLcl.setLayoutParams(layoutParamsLcl);
//        vLcl.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) itemHeight));
                ImageView iconLcl = (ImageView) vLcl.findViewById(R.id.item_s_icon);
                TextView nameLcl = (TextView) vLcl.findViewById(R.id.item_s_name);
                TextView numberLcl = (TextView) vLcl.findViewById(R.id.item_s_number);
                MyRowInNavigationMenu recordLcl = data.get(position);
                iconLcl.setImageResource(recordLcl.iconId);
//                if(recordLcl.name.length()>11){
//                    nameLcl.setScaleX(nameLcl.getScaleX()*0.75f);
//                    nameLcl.setScaleY(nameLcl.getScaleY()*0.75f);
//                }
                nameLcl.setTypeface(typeface);
                nameLcl.setText(recordLcl.name);
                nameLcl.setBackgroundColor(Color.WHITE);
                numberLcl.setText(recordLcl.value);
                numberLcl.setTypeface(typeface);
                numberLcl.setBackgroundColor(Color.WHITE);
            }
        });
//        l.a(888);
        return vLcl;
    }
}


