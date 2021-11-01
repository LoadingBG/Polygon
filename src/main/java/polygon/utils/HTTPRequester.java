package polygon.utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Objects;

public final class HTTPRequester {
    private HTTPRequester() {}

    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static String get(final String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return Objects.requireNonNull(CLIENT.newCall(request).execute().body()).string();
    }

    public static String post(final String url, final String body) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, body))
                .build();
        return Objects.requireNonNull(CLIENT.newCall(request).execute().body()).string();
    }
}
