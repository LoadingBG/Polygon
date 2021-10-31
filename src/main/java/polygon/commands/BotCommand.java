package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface BotCommand {
    String name();
    String description();
    CommandData assembleData();
    void handle(final SlashCommandEvent event, final InteractionHook hook);
}
