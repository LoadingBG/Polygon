package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public abstract class BotCommand {
    protected final String name;
    protected final String subcommandGroupName;
    protected final String subcommandName;
    protected final OptionMapping[] options;

    protected BotCommand(final String name, final String subcommandGroupName, final String subcommandName, final OptionMapping[] options) {
        this.name = name;
        this.subcommandGroupName = subcommandGroupName;
        this.subcommandName = subcommandName;
        this.options = options;
    }

    public abstract void handle(final SlashCommandEvent event, final InteractionHook hook);
}
