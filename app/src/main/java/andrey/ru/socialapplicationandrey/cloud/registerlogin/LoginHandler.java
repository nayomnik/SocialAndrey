package andrey.ru.socialapplicationandrey.cloud.registerlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import andrey.ru.socialapplicationandrey.LoginActivity;
import andrey.ru.socialapplicationandrey.l;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by User on 5/9/2017.
 */

public class LoginHandler {
//    {
//        "username": "",
//            "email": "",
//            "password": ""
//    }
//    http://95.183.8.181/accounts/login/

    public static final MediaType JSON = MediaType.parse("application/json");
    private final LoginActivity activity;
    OkHttpClient client = new OkHttpClient();
    private int code;

    //    {
//        "phone": "",
//            "code": null
//    }
    public LoginHandler(final String phonePrm, String passwordPrm, final LoginActivity activity) {
        this.activity = activity;
        JSONObject jsonMainObjectLcl = new JSONObject();
        try {
            jsonMainObjectLcl.put("username", phonePrm);
            jsonMainObjectLcl.put("password", passwordPrm);
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
                    sLcl = post(" http://95.183.8.181/accounts/login/", json2Lcl.toString());
                    l.a(sLcl + code);
                    if (code == 200 || code == 201) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(sLcl);

                            activity.goToMain(obj.getString("key"));
                            l.a("key = " + obj.getString("key"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return;
                    } else
                        sLcl = "Телефон или пароль введены неверно";
                    activity.messageWindow(sLcl, true);
                } catch (IOException e) {
                    activity.messageWindow("Нет связи", true);
                    l.a("IOException1");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
//        l.a(bodyToString(request));
        Response response = client.newCall(request).execute();
        code = response.code();
        l.a(99);
        return response.body().string();
    }
}
