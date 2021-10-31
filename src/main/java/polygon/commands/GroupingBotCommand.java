package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import polygon.utils.BotLogger;
import polygon.utils.DiscordUtils;

import java.util.List;

public record GroupingBotCommand(String name,
                                 String description,
                                 List<BotSubcommandGroup> groups,
                                 List<BotSubcommand> subcommands) implements BotCommand {
    @Override
    public CommandData assembleData() {
        final CommandData data = new CommandData(name, description);
        groups.stream()
                .map(BotSubcommandGroup::assembleData)
                .forEach(data::addSubcommandGroups);
        subcommands.stream()
                .map(BotSubcommand::assembleData)
                .forEach(data::addSubcommands);
        return data;
    }

    @Override
    public void handle(final SlashCommandEvent event, final InteractionHook hook) {
        groups.stream()
                .filter(group -> group.name().equals(event.getSubcommandGroup()))
                .findFirst()
                .ifPresentOrElse(
                        group -> group.handle(event, hook),
                        () -> subcommands.stream()
                                .filter(cmd -> cmd.name.equals(event.getSubcommandName()))
                                .findFirst()
                                .ifPresentOrElse(
                                        cmd -> cmd.handle(event, hook),
                                        () -> BotLogger.errorFormat("Unknown command: {}", DiscordUtils.eventToData(event))
                                )
                );
    }
}
