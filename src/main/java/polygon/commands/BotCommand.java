package polygon.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

/**
 * Represents a top-level command like {@code /ping}.
 * @see ExecutableBotCommand
 * @see GroupingBotCommand
 */
public abstract class BotCommand extends CommandEntity implements DataAssembler<CommandData> {
    protected final CommandPrivilege[] privileges;

    protected BotCommand(final String name, final String description, final CommandPrivilege[] privileges) {
        super(name, description);
        this.privileges = privileges;
    }

    public final void register(final Guild guild) {
        final Command discordCommand = guild.upsertCommand(assembleData()).complete();
        guild.updateCommandPrivilegesById(discordCommand.getIdLong(), privileges).complete();
    }
}
