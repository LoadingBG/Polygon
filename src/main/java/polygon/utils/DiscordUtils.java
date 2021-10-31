package polygon.utils;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public final class DiscordUtils {
    private DiscordUtils() {}

    public static String eventToData(final SlashCommandEvent event) {
        final String command = event.getCommandString();
        final String name = event.getName();
        final String subcmdGroup = event.getSubcommandGroup();
        final String subcmd = event.getSubcommandName();
        final String cmdId = event.getCommandId();
        final String options = event.getOptions().toString();
        final String userName = event.getUser().getAsTag();
        final String userId = event.getUser().getId();
        final String guildName = event.getGuild() == null ? "null" : event.getGuild().getName();
        final String guildId = event.getGuild() == null ? "null" : event.getGuild().getId();
        final String channelName = event.getChannel().getName();
        final String channelId = event.getChannel().getId();
        return """
                Command: %s
                
                Name: %s
                Subcommand Group Name: %s
                Subcommand Name: %s
                Command ID: %s
                Options: %s
                
                User: %s
                User ID: %s
                
                Guild: %s
                Guild ID: %s
                Channel: %s
                Channel ID: %s
                """.formatted(
                command, name, subcmdGroup, subcmd, cmdId, options,
                userName, userId,
                guildName, guildId, channelName, channelId
        );
    }
}
