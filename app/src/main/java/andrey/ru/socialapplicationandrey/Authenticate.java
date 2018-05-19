package andrey.ru.socialapplicationandrey;

/**
 * Created by User on 5/4/2017.
 */


import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public final class Authenticate {
    private final OkHttpClient client;

    public Authenticate() {
        client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        if (response.request().header("Authorization") != null) {
                            return null; // Give up, we've already attempted to authenticate.
                        }

                        System.out.println("Authenticating for response: " + response);
                        System.out.println("Challenges: " + response.challenges());
                        String credential = Credentials.basic("admin", "city123plus321");
                        return response.request().newBuilder()
                                .header("Authorization", credential)
                                .build();
                    }
                })
                .build();
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://95.183.8.181/admin/")
                .build();
        try {
            Response response = client.newCall(request).execute();


            if (!response.isSuccessful())
                l.a("Unexpected code " + response);

            l.a(response.body().string());
        } catch (Exception e) {
            l.a(9000);
            e.printStackTrace();
        }
    }


}