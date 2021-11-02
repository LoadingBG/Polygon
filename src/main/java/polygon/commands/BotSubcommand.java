package polygon.commands;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

/**
 * Represents a subcommand.
 */
public abstract class BotSubcommand extends CommandEntity implements DataAssembler<SubcommandData> {
    protected final OptionData[] options;

    protected BotSubcommand(final String name, final String description, final OptionData[] options) {
        super(name, description);
        this.options = options;
    }

    @Override
    public final SubcommandData assembleData() {
        return new SubcommandData(name, description).addOptions(options);
    }
}
