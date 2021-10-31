package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public abstract class BotSubcommand {
    protected final String name;
    protected final String description;
    protected final OptionData[] options;

    protected BotSubcommand(final String name, final String description, final OptionData[] options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }

    public final SubcommandData assembleData() {
        return new SubcommandData(name, description).addOptions(options);
    }

    public abstract void handle(final SlashCommandEvent event, final InteractionHook hook);
}
