package andrey.ru.socialapplicationandrey.cloud.profile;

import java.io.IOException;

import andrey.ru.socialapplicationandrey.MainActivity;
import andrey.ru.socialapplicationandrey.l;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 6/27/2017.
 */

public class PrivatePublicHandler {
    private final String token;
    private final MainActivity activity;
    private final OkHttpClient client;
    private final Request request;
    private String fileName;

    public PrivatePublicHandler(MainActivity activityPrm, String fileNamePrm, String tokenPrm) {
        String urlLcl = "http://95.183.8.181/accounts/profile/del_photo/1/";
        fileName=fileNamePrm;
        client = new OkHttpClient();
        token = tokenPrm;
        this.activity = activityPrm;
        request = new Request.Builder().url(urlLcl).header("Authorization", "Token " + token).delete().build();
        OkHttpClient client = new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                delete();
            }
        }).start();

    }

    private void delete() {
        try {
            Response responseLcl;
            responseLcl = client.newCall(request).execute();
            l.a("response code  = " + responseLcl.code());
            l.a("response code  = " + responseLcl.body().string());
            activity.deleteImage(fileName);
            activity.stopWheel();
            activity.message("Изображение успешно \n удалено с сервера.");
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
            activity.stopWheel();
            ;
//            activityPrm.enableNewPosts();

        }
    }
}
