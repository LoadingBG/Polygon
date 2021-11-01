package polygon.commands.run;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import polygon.commands.BotSubcommand;
import polygon.utils.BotLogger;

import java.io.*;
import java.util.*;

final class Code extends BotSubcommand {
    Code() {
        super(
                "code",
                "Runs the snippet of code",
                new OptionData[] {
                        new OptionData(OptionType.STRING, "language", "The language this snippet is written in", true), // TODO: make choices for each language
                        new OptionData(OptionType.STRING, "code", "The code snippet", true),
                        new OptionData(OptionType.STRING, "stdin", "Any input for the program"),
                        //new OptionData(OptionType.STRING, "args", "Any arguments to pass to the executor")
                }
        );
    }

    @Override
    public void handle(SlashCommandEvent event, InteractionHook hook) {
        final String language = Objects.requireNonNull(event.getOption("language")).getAsString();
        if (!Languages.LANGUAGES_TABLE.containsKey(language)) {
            hook.sendMessage("The language `" + language + "` isn't supported.").setEphemeral(true).queue();
            return;
        }

        final String code = Objects.requireNonNull(event.getOption("code"))
                .getAsString()
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
        final OptionMapping stdin = event.getOption("stdin");

        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("language", language);
        data.put("version", Languages.LANGUAGES_TABLE.get(language));
        data.put("files", new JSONArray("[{\"content\":\"" + code + "\"}]"));
        data.put("log", 0);
        if (stdin != null) {
            data.put("stdin", stdin.getAsString());
        }
        final String payload = new JSONObject(data).toString();

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://emkc.org/api/v2/piston/execute")
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), payload))
                .build();
        try (final Response response = client.newCall(request).execute()) {
            final JSONObject result = new JSONObject(response.body().string()).getJSONObject("run");
            final String stdout = result.getString("stdout");
            final String stderr = result.getString("stderr");
            final int exitCode = result.getInt("code");
            hook.sendMessage(String.format("Exit code:%d%n%nStandard output:%n```%s```%nStandard error:%n```%s```", exitCode, stdout, stderr)).queue();
        } catch (final IOException e) {
            BotLogger.error(Code.class.getCanonicalName() + "#handle", e);
            hook.sendMessage("Something went wrong...").queue();
        }
    }
}