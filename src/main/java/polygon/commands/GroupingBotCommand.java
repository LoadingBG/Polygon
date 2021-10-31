package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Arrays;
import java.util.Optional;

public class GroupingBotCommand extends BotCommand {
    protected final BotSubcommandGroup[] groups;
    protected final BotSubcommand[] subcommands;

    public GroupingBotCommand(final String name,
                              final String description,
                              final BotSubcommandGroup[] groups,
                              final BotSubcommand[] subcommands) {
        super(name, description);
        this.groups = groups;
        this.subcommands = subcommands;
    }

    @Override
    public CommandData assembleData() {
        final CommandData data = new CommandData(name, description);
        Arrays.stream(groups)
              .map(BotSubcommandGroup::assembleData)
              .forEach(data::addSubcommandGroups);
        Arrays.stream(subcommands)
              .map(BotSubcommand::assembleData)
              .forEach(data::addSubcommands);
        return data;
    }

    @Override
    public void handle(final SlashCommandEvent event, final InteractionHook hook) {
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
