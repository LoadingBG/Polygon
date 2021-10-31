package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

import java.util.Arrays;
import java.util.Optional;

public class GroupingBotCommand extends BotCommand {
    private final BotSubcommandGroup[] groups;
    private final BotSubcommand[] subcommands;

    public GroupingBotCommand(final String name,
                              final String description,
                              final BotSubcommandGroup[] groups,
                              final BotSubcommand[] subcommands,
                              final CommandPrivilege[] privileges) {
        super(name, description, privileges);
        this.groups = groups;
        this.subcommands = subcommands;
    }

    @Override
    public final CommandData assembleData() {
        final CommandData data = new CommandData(name, description);
        Arrays.stream(groups)
              .map(BotSubcommandGroup::assembleData)
              .forEach(data::addSubcommandGroups);
        Arrays.stream(subcommands)
              .map(BotSubcommand::assembleData)
              .forEach(data::addSubcommands);
        if (privileges.length > 0) {
            data.setDefaultEnabled(false);
        }
        return data;
    }

    @Override
    public final void handle(final SlashCommandEvent event, final InteractionHook hook) {
        final Optional<BotSubcommandGroup> group =
                Arrays.stream(groups)
                      .filter(g -> g.name().equals(event.getSubcommandGroup()))
                      .findFirst();
        if (group.isPresent()) {
            group.get().handle(event, hook);
        } else {
            Arrays.stream(subcommands)
                  .filter(s -> s.name().equals(event.getSubcommandName()))
                  .findFirst()
                  .ifPresent(subcmd -> subcmd.handle(event, hook));
        }
    }
}
