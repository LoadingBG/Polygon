package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import polygon.utils.BotLogger;
import polygon.utils.DiscordUtils;

import java.util.List;

public record BotSubcommandGroup(String name, String description, List<BotSubcommand> subcommands) {
    public SubcommandGroupData assembleData() {
        return null;
    }

    public void handle(final SlashCommandEvent event, final InteractionHook hook) {
        subcommands.stream()
                .filter(cmd -> cmd.name.equals(event.getSubcommandName()))
                .findFirst()
                .ifPresentOrElse(
                        cmd -> cmd.handle(event, hook),
                        () -> BotLogger.errorFormat("Unknown command: {}", DiscordUtils.eventToData(event))
                );
    }
}
