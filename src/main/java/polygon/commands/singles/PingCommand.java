package polygon.commands.singles;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import polygon.commands.ExecutableBotCommand;
import polygon.utils.EmbedUtils;

public final class PingCommand extends ExecutableBotCommand {
    public PingCommand() {
        super("ping", "Calculates this bot's ping.", new OptionData[0], new CommandPrivilege[0]);
    }

    @Override
    public void handle(SlashCommandEvent event, InteractionHook hook) {
        event.getJDA().getRestPing().queue(rest -> hook.sendMessageEmbeds(EmbedUtils.genericWithFields(
                "Latency",
                new MessageEmbed.Field("REST Ping", "REST Ping is " + rest + " ms", false),
                new MessageEmbed.Field("Gateway Ping", "Gateway Ping is " + event.getJDA().getGatewayPing() + " ms", false)
        )).queue());
    }
}
