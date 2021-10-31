package polygon.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class BotCommand extends CommandEntity implements DataAssembler<CommandData> {
    protected BotCommand(final String name, final String description) {
        super(name, description);
    }
}
