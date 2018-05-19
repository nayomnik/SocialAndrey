package andrey.ru.socialapplicationandrey;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;

/**
 * Created by User on 4/30/2017.
 */

public class MyNavigationView extends NavigationView {
    public MyNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setRight(150);

    }
}
