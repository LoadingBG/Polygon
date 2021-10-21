package polygon.commands.global;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import polygon.commands.BotCommand;

public final class PingCommand extends BotCommand {
    public PingCommand() {
        super(new CommandData("ping", "Information about my ping"));
    }

    @Override
    public void handle(final SlashCommandEvent event, final InteractionHook hook) {
        event.getJDA().getRestPing().queue(restPing ->
                hook.sendMessageFormat("REST Ping: %d, Gateway Ping: %d", restPing, event.getJDA().getGatewayPing()).queue());
    }
}
