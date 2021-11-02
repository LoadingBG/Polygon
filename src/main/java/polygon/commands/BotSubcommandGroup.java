package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.Arrays;

/**
 * Represents a subcommand group.
 */
public final class BotSubcommandGroup extends CommandEntity implements DataAssembler<SubcommandGroupData> {
    private final BotSubcommand[] subcommands;

    public BotSubcommandGroup(final String name, final String description, final BotSubcommand[] subcommands) {
        super(name, description);
        this.subcommands = subcommands;
    }

    @Override
    public SubcommandGroupData assembleData() {
        return Arrays.stream(subcommands)
                     .map(BotSubcommand::assembleData)
                     .reduce(
                             new SubcommandGroupData(name, description),
                             SubcommandGroupData::addSubcommands,
                             (a, b) -> a
                     );
    }

    public void handle(final SlashCommandEvent event, final InteractionHook hook) {
         Arrays.stream(subcommands)
               .filter(cmd -> cmd.name().equals(event.getSubcommandName()))
               .findFirst()
               .ifPresent(subcmd -> subcmd.handle(event, hook));
    }
}
