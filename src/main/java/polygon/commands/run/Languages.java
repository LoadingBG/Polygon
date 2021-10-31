package polygon.commands.run;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONArray;
import org.json.JSONObject;
import polygon.commands.BotSubcommand;

final class Languages extends BotSubcommand {
    Languages() {
        super(
                "languages",
                "Shows a list of available languages",
                new OptionData[0]
        );
    }

    @Override
    public void handle(SlashCommandEvent event, InteractionHook hook) {
        final JSONArray languages = Requester.getLanguages();
        if (languages == null) {
            hook.sendMessage("Something went wrong...").queue();
            return;
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < languages.length(); ++i) {
            final JSONObject langInfo = languages.getJSONObject(i);
            builder.append(langInfo.getString("language"))
                    .append(": ")
                    .append(langInfo.getString("version"))
                    .append("\n");
        }
        hook.sendMessage(builder.toString()).queue();
    }
}
