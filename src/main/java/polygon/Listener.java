package polygon;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

/**
 * The event listener. Dispatches the command received
 * based on the bot commands in {@link Bot#commands()}.
 */
public final class Listener extends ListenerAdapter {
    private final Bot bot;

    Listener(final Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onSlashCommand(final SlashCommandEvent event) {
        event.deferReply().queue(hook ->
            Arrays.stream(bot.commands())
                  .filter(cmd -> cmd.name().equals(event.getName()))
                  .findFirst()
                  .ifPresent(cmd -> cmd.handle(event, hook))
        );
    }
}
