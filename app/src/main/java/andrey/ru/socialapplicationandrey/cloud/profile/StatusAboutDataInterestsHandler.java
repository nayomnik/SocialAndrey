package andrey.ru.socialapplicationandrey.cloud.profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import andrey.ru.socialapplicationandrey.MainActivity;
import andrey.ru.socialapplicationandrey.fragments.general.profile.ProfileFragment;
import andrey.ru.socialapplicationandrey.l;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by User on 6/11/2017.
 */

public class StatusAboutDataInterestsHandler {
    private static final MediaType JSON= MediaType.parse("application/json");
    private final MainActivity activity;
    private final OkHttpClient client;
    private final String token;
    private int code;

    public StatusAboutDataInterestsHandler(MainActivity activityPrm, String tokenPrm,
                                           String statusStringPrm, String aboutStringPrm, String dataPrm, String interestStrings) {
        String dataLcl = "";
        if(!dataPrm.isEmpty()){
            dataLcl+=dataPrm.substring(6,10)+"-"+dataPrm.substring(3,5)+"-"+dataPrm.substring(0,2);
        }
        final String urlLcl = "http:/95.183.8.181/accounts/profile/";
        token=tokenPrm;
        client = new OkHttpClient();
        JSONObject jsonMainObjectLcl = new JSONObject();
        try {
            jsonMainObjectLcl.put("first_name", "test");
            jsonMainObjectLcl.put("last_name", "test");
            jsonMainObjectLcl.put("status", statusStringPrm);
            jsonMainObjectLcl.put("about", aboutStringPrm);
            jsonMainObjectLcl.put("birthday", dataLcl);
            jsonMainObjectLcl.put("interests", interestStrings);
            jsonMainObjectLcl.put("sex", "none");
            ArrayList<String> list = new ArrayList();
            list.add("2");
            jsonMainObjectLcl.put("favorite_places", new JSONArray(list));
        } catch (JSONException e) {
            l.a("JSONException");
            e.printStackTrace();
        }
        this.activity = activityPrm;

        final JSONObject json2Lcl = jsonMainObjectLcl;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sLcl = "";
                    sLcl = put(urlLcl, json2Lcl.toString());
                    l.a(sLcl + code);
                    if (code == 200 || code == 201) {
                        activity.stopWheel();
                        activity.message("Данные отправлены");
                    } else {
                        activity.stopWheel();
//                        sLcl = "Посылка данных не удалась ";
                        activity.messageWindow(sLcl, true);
                    }
                } catch (IOException e) {
                    activity.stopWheel();
                    activity.messageWindow("Нет связи", true);
                    l.a("IOException1");
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private String put(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token " + token)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        code = response.code();
        l.a(99);
        return response.body().string();
    }
}