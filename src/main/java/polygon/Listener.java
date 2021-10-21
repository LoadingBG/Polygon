package polygon;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import polygon.commands.BotCommand;

import java.util.Optional;

public final class Listener extends ListenerAdapter {
    private final Bot bot;

    Listener(final Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onSlashCommand(final SlashCommandEvent event) {
        event.deferReply().queue(hook -> {
            final Optional<BotCommand> handler = bot.commandToHandle(event.getName());
            if (handler.isPresent()) {
                handler.get().handle(event, hook);
            } else {
                hook.sendMessage("HOW?").queue();
            }
        });
    }
}
