package polygon.commands.run;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONArray;
import org.json.JSONObject;
import polygon.commands.BotSubcommand;
import polygon.utils.BotLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

final class Languages extends BotSubcommand {
    public static final Map<String, String> LANGUAGES_TABLE;
    static {
        final Map<String, String> langs = new HashMap<>();
        try {
            final URL url = new URL("https://emkc.org/api/v2/piston/runtimes");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final JSONArray langInfos = new JSONArray(reader.lines().collect(Collectors.joining()));
            reader.close();

            for (int i = 0; i < langInfos.length(); ++i) {
                final JSONObject langInfo = langInfos.getJSONObject(i);
                langs.put(langInfo.getString("language"), langInfo.getString("version"));
            }
        } catch (final IOException e) {
            BotLogger.error(Languages.class.getCanonicalName(), e);
        }
        LANGUAGES_TABLE = Collections.unmodifiableMap(langs);
    }

    Languages() {
        super("languages", "Shows a list of available languages", new OptionData[0]);
    }

    @Override
    public void handle(SlashCommandEvent event, InteractionHook hook) {
        final String languages = LANGUAGES_TABLE
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
        hook.sendMessage(languages).setEphemeral(true).queue();
    }
}
