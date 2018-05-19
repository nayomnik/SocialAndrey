package andrey.ru.socialapplicationandrey.fragments.general.profile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

import andrey.ru.socialapplicationandrey.R;

/**
 * Created by User on 6/7/2017.
 */

public final class PictureViewActivity extends Activity {
    private String pathLcl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_view);
        Bundle extras = getIntent().getExtras();

        String homeDirectoryLcl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/CityPlus_cash/Photos/Me";

        WebView wv = (WebView) findViewById(R.id.picture_view);
        wv.getSettings().setBuiltInZoomControls(true);
        WebSettings webSettings=wv.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if(extras !=null) {
            String nameLcl = extras.getString("imageName");
            pathLcl = "file://"+homeDirectoryLcl+ File.separator+nameLcl;
            wv.loadUrl(pathLcl);
        }
    }
}

