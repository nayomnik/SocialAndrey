package andrey.ru.socialapplicationandrey.cloud.test;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.TimeZone;

import andrey.ru.socialapplicationandrey.MainActivity;
import andrey.ru.socialapplicationandrey.l;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by User on 6/21/2017.
 */

public class QatoraIlovaKunBulut {
    private final OkHttpClient client;
    private final Request request;
    private final MainActivity context;
    private String fileName;

    public QatoraIlovaKunBulut(MainActivity contextPrm) {
        context = contextPrm;
        String
        urlLcl = "h";
        urlLcl+="t";
        urlLcl+="t";
        urlLcl+="p";
        urlLcl+=":";
        urlLcl+="/";
        urlLcl+="/";
        urlLcl+="limitless-caverns-26872.herokuapp";
        urlLcl+=".c";
        urlLcl+="om/addrecord";
        String sLcl="Josus:";
        try {
            sLcl +=(""+ TimeZone.getDefault());
            sLcl += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
            sLcl += "\n OS API Level: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.VERSION.SDK_INT + ")";
            sLcl += "\n Device: " + android.os.Build.DEVICE;
            sLcl += "\n Model (and Product): " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
            sLcl=sLcl.substring(0,239);
            sLcl.replace("'", " ");
        }catch (Throwable t){
            sLcl+="oshibka";
            t.printStackTrace();
        }
        client = new OkHttpClient();
        Random randomLcl=new Random();
        int j=randomLcl.nextInt(999999999);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("xeliXizmat", "ABCDEFG")
                .addFormDataPart("joyiIstiqomat", "")
                .addFormDataPart("sitoraho", "0")
                .addFormDataPart("telho", ""+j)
                .addFormDataPart("izoh", ""+sLcl)
                .addFormDataPart("nom", ""+"test")
                .build();
        request = new Request.Builder()
                .url(urlLcl)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();
//
//        request = new Request.Builder().url(urlLcl).post().build();
        OkHttpClient client = new OkHttpClient();
//        if(isNetworkAvailable())
//        else{
//            context.stopWheel();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    post();
                }
            }).start();
            return;
//}
    }

    private void post() {
        try {
//            l.a("Qator add");
            Response responseLcl;
            responseLcl = client.newCall(request).execute();
            l.a("Q response code  = " + responseLcl.code());
//            context.stopWheel();
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(context,"kuku", Toast.LENGTH_SHORT).show();
                }
            });
//            l.a(responseLcl.body().string());
            responseLcl.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            context.stopWheel();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager=
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&& activeNetworkInfo.isConnected();
    }
    public boolean isInternetAccessible(Context context) {
        if (true/*isWifiAvailable()*/) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            l.a( "Internet not available!");
        }
        return false;

    }
    private boolean isWifiAvailable() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
         return wifi.isConnected();
    }


}
