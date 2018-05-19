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
 * Created by User on 5/13/2017.
 */

public class ForgotSMSCheckHandler {
    public static final MediaType JSON = MediaType.parse("application/json");
    private final LoginActivity activity;
    OkHttpClient client = new OkHttpClient();
    private int code;

    //    {
//        "phone": "",
//            "code": null
//    }
    public ForgotSMSCheckHandler(final String phonePrm, String smsCodePrm, final LoginActivity activity) {
        this.activity = activity;
        JSONObject jsonMainObjectLcl = new JSONObject();
        try {
            jsonMainObjectLcl.put("phone", phonePrm);
            jsonMainObjectLcl.put("code", smsCodePrm);
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
                    sLcl = post("http://95.183.8.181/accounts/profile/confirm_sms/", json2Lcl.toString());
                    l.a(sLcl + code);
                    if (code == 200) {
                        sLcl = "Код подтвержден. ";
                        activity.setRegistration3Fragment();
                    } else
                        sLcl = "Неверный код";
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