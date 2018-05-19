package andrey.ru.socialapplicationandrey;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import andrey.ru.socialapplicationandrey.fragments.general.profile.ProfileFragment;

/**
 * Created by User on 6/2/2017.
 */

public class StatusAboutMePopup extends Dialog implements DialogInterface.OnCancelListener {


    private final EditText editTextView;
    private final String titleString;
    private final SharedPreferences prefs;
    private String[] interestStringsArray;
    private MainActivity activity;
    private boolean doFinish;
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View vPrm) {
            switch (vPrm.getId()) {
                case R.id.save:
                    String sLcl = editTextView.getText().toString();
                    prefs.edit().putString(titleString, sLcl).commit();
                    activity.putToProfile(titleString, sLcl);
                    cancel();
                    break;
                case R.id.cancel:
                    cancel();
                    break;
            }
        }
    };

    private TextWatcher watcher = new TextWatcher() {
        private String current = "";
        private String ddmmyyyy = "ДДММГГГГ";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8) {
                    clean = clean + ddmmyyyy.substring(clean.length());
                } else {
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day = Integer.parseInt(clean.substring(0, 2));
                    int mon = Integer.parseInt(clean.substring(2, 4));
                    int year = Integer.parseInt(clean.substring(4, 8));

                    if (mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon - 1);
                    year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                    clean = String.format("%02d%02d%02d", day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                editTextView.setText(current);
                editTextView.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public StatusAboutMePopup(Context context, String titlePrm, String oldTextPrm) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity = (MainActivity) context;
        prefs = activity.getSharedPreferences("persons", activity.MODE_PRIVATE);
        if (titlePrm.equals(ProfileFragment.DATA_TITLE))
            setContentView(R.layout.edit_dialog_data);
        else
            setContentView(R.layout.edit_dialog);
//        this.setButton(BUTTON_POSITIVE, "Ok", (OnClickListener) this);
        TextView textLcl = (TextView) findViewById(R.id.title_of_edit);
        textLcl.setText(titlePrm);
        titleString = titlePrm;
        setOnCancelListener(this);
        Button okButtonLcl = (Button) findViewById(R.id.save);
        okButtonLcl.setOnClickListener(buttonListener);
        Button cancelButtonLcl = (Button) findViewById(R.id.cancel);
        cancelButtonLcl.setOnClickListener(buttonListener);
        editTextView = (EditText) findViewById(R.id.edit_text);
        editTextView.setText(oldTextPrm);
        editTextView.setSelection(editTextView.getText().length());
        if (titlePrm.equals(ProfileFragment.DATA_TITLE))
            editTextView.addTextChangedListener(watcher);
        else if (titlePrm.equals(ProfileFragment.INTERESTS_TITLE))
            interestStringsArray = oldTextPrm.split(",");

      /*  TextView numberImei = (TextView) xonaLcl.findViewById(R.id.phone);
        String s = (String) numberImei.getText();
        kotibMatn = (EditText)xonaLcl.findViewById(R.id.number);
        SharedPreferences xLcl = activity.getSharedPreferences("chanta_fruxt_xohish", Context.MODE_WORLD_READABLE
                & Context.MODE_WORLD_WRITEABLE);
        int i0 = xLcl.getInt("counter", 0);
        numberImei.setText(s + activity.getImei() + "v" + i0);*/
    }

    @Override
    public void onCancel(DialogInterface di) {
        cancel();
    }


}
//  switch (titleString){
//          case ProfileFragment.STATUS_TITLE:
//
//          }