package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Optional;

public interface BotCommandManager {
    BotCommand[] commands();

    default Optional<BotCommand> getCommand(final SlashCommandEvent event) {
        throw new UnsupportedOperationException("TODO: BotCommandManager#getCommand");
    }

    default CommandData assembleDiscordCommand() {
        throw new UnsupportedOperationException("TODO: BotCommandManager#assembleDiscordCommand");
    }
}
