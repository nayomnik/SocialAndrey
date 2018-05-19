package andrey.ru.socialapplicationandrey;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

import andrey.ru.socialapplicationandrey.cloud.registerlogin.ForgotPasswordHandler;
import andrey.ru.socialapplicationandrey.cloud.registerlogin.ForgotPasswordInstallHandler;
import andrey.ru.socialapplicationandrey.cloud.registerlogin.ForgotSMSCheckHandler;
import andrey.ru.socialapplicationandrey.cloud.registerlogin.LoginHandler;
import andrey.ru.socialapplicationandrey.cloud.registerlogin.RegistrationHandler;
import andrey.ru.socialapplicationandrey.cloud.registerlogin.RegistrationPasswordInstallHandler;
import andrey.ru.socialapplicationandrey.cloud.registerlogin.RegistrationSMSCheckHandler;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.ForgotPassword1Fragment;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.ForgotPassword2Fragment;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.LoginFragment;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.RegistrationFirstStepFragment;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.RegistrationForthStepFragment;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.RegistrationSecondStepFragment;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.RegistrationThirdStepFragment;
import andrey.ru.socialapplicationandrey.fragments.registerlogin.SmsEnterForgotFragment;

/**
 * Created by User on 5/3/2017.
 */

public class LoginActivity extends Activity {

    public static final int SMS_PICKUP_FORGOT_BUTTON = 10102;
    private static LoginActivity instance;
    private boolean message;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LoginFragment loginFragment;
    private RegistrationFirstStepFragment registration1Fragment;
    private RegistrationSecondStepFragment registration2Fragment;
    private RegistrationThirdStepFragment registration3Fragment;
    private RegistrationForthStepFragment registration4Fragment;
    private ForgotPassword1Fragment forgotPassword1Fragment;
    private ForgotPassword2Fragment forgotPassword2Fragment;
    /**
     * The List of cashed progress wheels.
     */
    private Vector<ProgressDialog> listOfWheels;
    private String phoneNumber;
    private SharedPreferences sharedPreferences;
    private SmsEnterForgotFragment smsEnterForgotFragment;

    public static LoginActivity getInstance() {
        return instance;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle bundlePrm) {
        super.onCreate(bundlePrm);
        setContentView(R.layout.login_activity);
        instance = this;
        sharedPreferences = getSharedPreferences("Andrey", Context.MODE_WORLD_READABLE
                & Context.MODE_WORLD_WRITEABLE);
//        TextView tLcl = (TextView)findViewById(R.id.splashscreentxt);
//
//        Typeface customFontLcl = Typeface.createFromAsset(getAssets(),  "fonts/HelveticaNeueCyr-Light.otf");
        listOfWheels = new Vector<ProgressDialog>();

        String sLcl = getToday();
        TextView tvLcl = (TextView) findViewById(R.id.sreda);
        tvLcl.setText(sLcl);
        loginFragment = new LoginFragment();
        registration1Fragment = new RegistrationFirstStepFragment();
        registration2Fragment = new RegistrationSecondStepFragment();
        registration3Fragment = new RegistrationThirdStepFragment();
        registration4Fragment = new RegistrationForthStepFragment();
        forgotPassword1Fragment = new ForgotPassword1Fragment();
        forgotPassword2Fragment = new ForgotPassword2Fragment();
        fragmentManager = getFragmentManager();
        smsEnterForgotFragment = new SmsEnterForgotFragment();
        setFragment(loginFragment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public String getToday() {
        SimpleDateFormat dayFormatLcl = new SimpleDateFormat("EEE, d MMM", new Locale("Ru"));
        String s0Lcl = dayFormatLcl.format(Calendar.getInstance().getTime());
        String sLcl = s0Lcl.substring(0, 1).toUpperCase() + s0Lcl.substring(1);
        return sLcl;
    }

    @Override
    public void onBackPressed() {

        if (registration1Fragment.isVisible()) {
            setFragment(loginFragment);
            return;
        } else if (registration2Fragment.isVisible()) {
            setFragment(registration1Fragment);
            return;
        } else if (registration3Fragment.isVisible()) {
            setFragment(registration2Fragment);
            return;
        } else if (registration4Fragment.isVisible()) {
            setFragment(registration3Fragment);
            return;
        }
        ;

        if (forgotPassword2Fragment.isVisible()) {
            setFragment(forgotPassword1Fragment);
            return;
        } else if (forgotPassword1Fragment.isVisible()) {
            setFragment(loginFragment);
            return;
        }

        super.onBackPressed();
    }

    public void message(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message)
                    return;
                else
                    message = true;
//                if(onPause)
//                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        l.pause(3000);
                        message = false;
                    }
                }).start();
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pickup_password_button:
                showWheel();
                phoneNumber = forgotPassword1Fragment.getPhone();
                new ForgotPasswordHandler(phoneNumber, this);
                break;
            case R.id.forgot_string:
                setFragment(forgotPassword1Fragment);
                return;
            case R.id.login_button:
                showWheel();
                phoneNumber = loginFragment.getPhone();
                String passwordLcl = loginFragment.getPassword();
                new LoginHandler(phoneNumber, passwordLcl, this);
                break;
            case R.id.register_0_button:
                setFragment(registration1Fragment);
                return;
            case R.id.register_1_button:
                showWheel();
                phoneNumber = registration1Fragment.getPhone();
                new RegistrationHandler(phoneNumber, this);
                break;
            case R.id.register_2_button:
                showWheel();
                String confirmationCodeLcl = registration2Fragment.getConfirmationCode();
                if (confirmationCodeLcl.isEmpty()) {
                    stopWheel();
                    return;
                }
                if (v.getTag().equals("registration")) {
                    new RegistrationSMSCheckHandler(phoneNumber, confirmationCodeLcl, this);
                } else {
                    new ForgotSMSCheckHandler(phoneNumber, confirmationCodeLcl, this);
                }
                ;
                break;
            case R.id.register_3_button:
                showWheel();
                String[] passwordsLcl = registration3Fragment.getPasswords();
                if (passwordsLcl[0].isEmpty() || passwordsLcl[1].isEmpty()) {
                    messageWindow("Вы не ввели пароль.", true);
                    stopWheel();
                    return;
                }
                new RegistrationPasswordInstallHandler(phoneNumber, passwordsLcl, this);
                break;
            case R.id.register_4_button:
                goToMain();
                return;
            case R.id.forgot_2_button:
                showWheel();
                String[] valuesLcl = forgotPassword2Fragment.getValuess();
                if (valuesLcl[0].isEmpty() || valuesLcl[1].isEmpty() || valuesLcl[2].isEmpty()) {
                    messageWindow("Вы не ввели что-то.", true);
                    stopWheel();
                    return;
                }
                new ForgotPasswordInstallHandler(phoneNumber, valuesLcl, this);
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                l.pause(3000);
                stopWheel();
            }
        }).start();
    }

    public void setFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout,
                fragment);
        fragmentTransaction.commit();

    }

    /**
     * Shows progress dialog wheel
     */
    public void showWheel() {
        final ProgressDialog progressLcl = new ProgressDialog(LoginActivity.this, R.style.CustomDialog);//ProgressDialog.show(LoginActivity.this, "", "", true);
        progressLcl.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressLcl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressLcl.show();
                listOfWheels.add(progressLcl);
            }
        });

    }

    /**
     * Stops progress dialog wheel
     */
    public void stopWheel() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!listOfWheels.isEmpty()) {
                    listOfWheels.lastElement().cancel();
                    listOfWheels.removeElement(listOfWheels.lastElement());
                }
            }
        });
    }

    public void messageWindow(final String sPrm, final boolean mustDiePrm) {
        stopWheel();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final MyPopUp dialog = new MyPopUp(LoginActivity.this, sPrm);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                if (mustDiePrm)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            l.pause(3000);
                            if (dialog != null)
                                dialog.cancel();
                            ;
                        }
                    }).start();
            }
        });
    }

    public void setRegistration2Fragment() {
        setFragment(registration2Fragment);
        ;
    }

    public void setRegistration3Fragment() {
        setFragment(registration3Fragment);
        ;
    }

    public void setRegistration4Fragment() {
        setFragment(registration4Fragment);
        ;
    }

    public void goToMain(String key) {
        SharedPreferences.Editor editorLcl = sharedPreferences.edit();
        editorLcl.putString("token_key", key).commit();
        goToMain();
    }

    private void goToMain() {
        stopWheel();
        message(" Вы вошли в систему.");
        Intent intentLcl = new Intent(this, MainActivity.class);
        startActivity(intentLcl);
        finish();

    }

    public void setForgotPassword2Fragment() {
        setFragment(forgotPassword2Fragment);
    }

    public void goToMainForgot() {
        goToMain();
    }
}