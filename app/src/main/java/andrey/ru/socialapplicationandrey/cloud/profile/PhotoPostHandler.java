package andrey.ru.socialapplicationandrey.cloud.profile;

import android.net.Uri;

import java.io.File;
import java.io.IOException;

import andrey.ru.socialapplicationandrey.MainActivity;
import andrey.ru.socialapplicationandrey.SplashActivity;
import andrey.ru.socialapplicationandrey.l;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by User on 6/10/2017.
 */
public class PhotoPostHandler {
    private final String token;
    private final MainActivity activity;
    private OkHttpClient client;

    public PhotoPostHandler(String tokenPrm, final MainActivity activityPrm, File filePrm) {
        client = new OkHttpClient();
        token = tokenPrm;
        this.activity = activityPrm;
        RequestBody formBodyLcl = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", filePrm.getName(),
                        RequestBody.create(MediaType.parse("image/*"), filePrm))
                .build();
        String urlLcl = "http://95.183.8.181/accounts/profile/add_photo/";
        Request requestLcl = new Request.Builder().url(urlLcl).header("Authorization", "Token " + token).post(formBodyLcl).build();
        Response responseLcl;
        try {
            OkHttpClient clientLcl = new OkHttpClient();
            responseLcl = clientLcl.newCall(requestLcl).execute();
            l.a("response code  = " + responseLcl.code());
            l.a("response code  = " + responseLcl.body().string());
            activityPrm.stopWheel();
            activityPrm.message("Изображение успешно \n размещено на сервере.");
        } catch (IOException e) {
            e.printStackTrace();
            activity.stopWheel();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.message("No connection");
                }
            });
//            l.a("response code error = "+responseLcl.code());
        } finally {
            activityPrm.stopWheel();;
//            activityPrm.enableNewPosts();

        }
    }

    }
