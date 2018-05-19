package andrey.ru.socialapplicationandrey;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Nayomnik on 11/17/2015.
 */
public class MyPopUp extends Dialog implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {


    //    private LoginActivity activity;
    private boolean doFinish;


    public MyPopUp(Context context, String sPrm) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        activity =  context;
        setContentView(R.layout.alert_dialog);
//        this.setButton(BUTTON_POSITIVE, "Ok", (OnClickListener) this);
        TextView textLcl = (TextView) findViewById(R.id.dialog_text);
        textLcl.setText(sPrm);
        setOnCancelListener(this);
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
       /* if(which==BUTTON_POSITIVE){

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Editable  eLcl = kotibMatn.getText();
                    String sLcl = null;
                    sLcl = eLcl.toString();
                    if(eLcl.length() != 0){
                        int i = 0;
                        i = Integer.parseInt(sLcl);
                        doFinish = activity.boZerkaaniDarvozaSo_hbat(i);
                    }


                    cancel();
                }
            });
            ;
        }
*/
        cancel();
    }


}
