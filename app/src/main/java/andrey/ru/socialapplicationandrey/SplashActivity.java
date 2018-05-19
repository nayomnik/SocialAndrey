package andrey.ru.socialapplicationandrey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import andrey.ru.socialapplicationandrey.cloud.registerlogin.AuthorizationCheckHandler;

/**
 * Created by User on 5/2/2017.
 */

public class SplashActivity extends Activity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    private static SplashActivity instance;
    private boolean toMain;
    private boolean processing;

    public static SplashActivity getInstance() {
        return instance;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle bundlePrm) {
        super.onCreate(bundlePrm);
        instance = this;
        processing = true;
        setContentView(R.layout.splash);
        TextView tLcl = (TextView) findViewById(R.id.splashscreentxt);
        SharedPreferences sharedPreferencesLcl = getSharedPreferences("Andrey", Context.MODE_WORLD_READABLE
                & Context.MODE_WORLD_WRITEABLE);
        String tokenLcl = sharedPreferencesLcl.getString("token_key", "");
        Typeface customFontLcl = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueCyr-Light.otf");

        tLcl.setTypeface(customFontLcl);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        new AuthorizationCheckHandler(tokenLcl,this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                l.pause(SPLASH_DISPLAY_LENGTH);
                while (processing)
                    Thread.yield();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
//                        toMain = true;
                        if (!toMain) {
                            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                            SplashActivity.this.startActivity(mainIntent);
                        } else {
                            Intent intentLcl = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intentLcl);
                        }
                        finish();
                    }
                });
            }
        }).start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void onBackPressed() {

    }

    public void goToMain(boolean b) {
        onBackPressed();
        toMain = b;
        processing = false;
    }

}
