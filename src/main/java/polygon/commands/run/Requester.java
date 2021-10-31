package polygon.commands.run;

import org.json.JSONArray;
import org.json.JSONObject;
import polygon.utils.BotLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

final class Requester {
    private Requester() {}

    private static final String LANGUAGES_LIST_URL = "https://emkc.org/api/v2/piston/runtimes";
    private static final String EXECUTE_URL = "https://emkc.org/api/v2/piston/execute";

    static JSONArray getLanguages() {
        try {
            final URL url = new URL(LANGUAGES_LIST_URL);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            final InputStream response = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            return new JSONArray(reader.lines().collect(Collectors.joining()));
        } catch (final IOException e) {
            BotLogger.error(Requester.class.getCanonicalName() + "#getLanguages: exception", e);
            return null;
        }
    }
}
