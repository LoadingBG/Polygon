package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class BotCommand {
    private final CommandData data;

    protected BotCommand(final CommandData data) {
        this.data = data;
    }

    public final CommandData data() {
        return data;
    }

    public final boolean shouldHandle(final String commandName) {
        return data.getName().equals(commandName);
    }

    public abstract void handle(final SlashCommandEvent event, final InteractionHook hook);
}
