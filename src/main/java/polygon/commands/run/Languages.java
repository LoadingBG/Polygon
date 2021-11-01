package polygon.commands.run;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONArray;
import org.json.JSONObject;
import polygon.commands.BotSubcommand;
import polygon.utils.BotLogger;
import polygon.utils.EmbedUtils;
import polygon.utils.HTTPRequester;
import polygon.utils.TableFormatter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

final class Languages extends BotSubcommand {
    public static final Map<String, String> LANGUAGES_TABLE;
    static {
        final Map<String, String> langs = new LinkedHashMap<>();
        try {
            StreamSupport.stream(
                            new JSONArray(HTTPRequester.get("https://emkc.org/api/v2/piston/runtimes")).spliterator(),
                            false
                    )
                    .map(JSONObject.class::cast)
                    .forEach(info -> langs.put(info.getString("language"), info.getString("version")));
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
        final String table = TableFormatter.languagesTable(LANGUAGES_TABLE);
        hook.sendMessageEmbeds(EmbedUtils.generic("Supported languages", "```\n" + table + "\n```")).setEphemeral(true).queue();
    }
}
