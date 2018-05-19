package andrey.ru.socialapplicationandrey.cloud.registerlogin;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 5/10/2017.
 */

public class BasicAuthInterceptor implements Interceptor {

    private String credentials;

    public BasicAuthInterceptor(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("ApiKey", "cba131cc4e113890c1c311fbefc0a54296b03242").build();//credentials  Authorization
        return chain.proceed(authenticatedRequest);
    }

}