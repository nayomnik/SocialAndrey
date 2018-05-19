package andrey.ru.socialapplicationandrey.cloud.registerlogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import andrey.ru.socialapplicationandrey.SplashActivity;
import andrey.ru.socialapplicationandrey.l;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by User on 5/10/2017.
 */

public class AuthorizationCheckHandler {//    {
//    {
//        "photos": [],
//        "first_name": "",
//            "last_name": "",
//            "birthday": null,
//            "about": "",
//            "status": "",
//            "interests": "",
//            "sex": "none",
//            "private": false,
//            "user": 22
//    }
//    http://95.183.8.181/accounts/profile/

    public static final MediaType JSON = MediaType.parse("application/json");
    private final OkHttpClient client;
    private SplashActivity activity;
    private int code;
    private String token;

    public AuthorizationCheckHandler(String tokenPrm, final SplashActivity activityPrm) {
        client = new OkHttpClient();
//                 .Builder()
//                .addInterceptor(new BasicAuthInterceptor("998937216227", "zaglushka"))
//                .build();
        token = tokenPrm;
        this.activity = activityPrm;
        JSONObject jsonMainObjectLcl = new JSONObject();
        try {
            jsonMainObjectLcl.put("photos", new JSONArray());
            jsonMainObjectLcl.put("first_name", "");
            jsonMainObjectLcl.put("last_name", "");
            jsonMainObjectLcl.put("birthday", null);
            jsonMainObjectLcl.put("about", "");
            jsonMainObjectLcl.put("status", "");
            jsonMainObjectLcl.put("interests", "");
            jsonMainObjectLcl.put("sex", "none");
            jsonMainObjectLcl.put("private", false);
            jsonMainObjectLcl.put("user", 22);
        } catch (JSONException e) {
            l.a("JSONException");
            e.printStackTrace();
        }
        final JSONObject json2Lcl = jsonMainObjectLcl;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sLcl = "";
                    sLcl = put("http://95.183.8.181/accounts/profile/", json2Lcl.toString());
                    l.a(sLcl + code);
                    if (code != 401) {

                        activity.goToMain(true);
                        return;
                    } else
                        activity.goToMain(false);
                } catch (IOException e) {
                    l.a("IOException1");
                    activity.goToMain(false);
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private String put(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        l.a("token= " + token);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token " + token)//Authorization
                .put(body)
                .build();
//        l.a(bodyToString(request));
        Response response = client.newCall(request).execute();
        code = response.code();
        l.a(99);
        return response.body().string();
    }

}
