package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface BotCommand {
    CommandData assembleData();
    String name();
    String description();
    void handle(final SlashCommandEvent event, final InteractionHook hook);
}
