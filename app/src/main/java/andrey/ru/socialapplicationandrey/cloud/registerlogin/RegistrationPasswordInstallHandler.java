package andrey.ru.socialapplicationandrey.cloud.registerlogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

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

public class RegistrationPasswordInstallHandler {

    public static final MediaType JSON = MediaType.parse("application/json");
    private final LoginActivity activity;
    OkHttpClient client = new OkHttpClient();
    private int code;

    //    {
//        "phone": "",
//            "code": null
//    }
    public RegistrationPasswordInstallHandler(final String phonePrm, String[] passwords, final LoginActivity activity) {
        this.activity = activity;
        JSONObject jsonMainObjectLcl = new JSONObject();
//        {
//            "username": "",
//                "email": "",
//                "password1": "",
//                "password2": ""
//        }
        try {
            jsonMainObjectLcl.put("username", phonePrm);
//            jsonMainObjectLcl.put("email", "");
            jsonMainObjectLcl.put("password1", passwords[0]);
            jsonMainObjectLcl.put("password2", passwords[1]);
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
                    sLcl = post("http://95.183.8.181/accounts/registration/", json2Lcl.toString());
                    l.a(sLcl + code);
                    if (code == 200 || code == 201) {
                        String s2Lcl = "";
                        try {
                            JSONObject obj = new JSONObject(sLcl);
                            activity.goToMain(obj.getString("key"));
                            l.a("key in passwords set = " + obj.getString("key"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sLcl = "Ваш пароль принят.";
                        activity.setRegistration4Fragment();
                        activity.message(sLcl);
                    } else {
                        if (null == sLcl || sLcl.isEmpty())
                            sLcl = "Ошибка сервера " + code + ".";
                        else {

                            try {
                                String s2Lcl = "";
                                JSONObject obj = new JSONObject(sLcl);
                                Iterator<String> keysLcl = obj.keys();
                                while (keysLcl.hasNext()) {
                                    String keyLcl = keysLcl.next();
                                    JSONArray arrayLcl = obj.getJSONArray(keyLcl);
                                    s2Lcl += keyLcl + " --> ";
                                    for (int i = 0; i < arrayLcl.length(); i++) {
                                        s2Lcl += arrayLcl.getString(i);
                                    }
                                    s2Lcl += "\n";
                                }
                                l.a(s2Lcl);
                                sLcl = s2Lcl;
                            } catch (Throwable t) {
                                l.a("Errror");
                            }

                            activity.messageWindow(sLcl, false);
                            return;
                        }
                        activity.messageWindow(sLcl, true);
                    }

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