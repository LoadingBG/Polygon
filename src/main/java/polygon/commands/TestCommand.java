package polygon.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Objects;

public final class TestCommand extends BotCommand {
    public TestCommand() {
        super(new CommandData("test", "a test command")
                .addSubcommands(new SubcommandData("sub", "subcommand test")
                        .addOption(OptionType.BOOLEAN, "bool", "bool option test", true)
                        .addOption(OptionType.INTEGER, "int", "int option test")
                        .addOption(OptionType.STRING, "str", "str option test")));
    }

    @Override
    public void handle(final SlashCommandEvent event, final InteractionHook hook) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Slash command")
                .addField("Channel:",          event.getChannel().toString(), true)
                .addField("Command ID:",       event.getCommandId(), true)
                .addField("Name:",             event.getName(), true)
                .addField("Subcommand group:", Objects.toString(event.getSubcommandGroup()), true)
                .addField("Subcommand name:",  Objects.toString(event.getSubcommandName()), true)
                .addField("Options:",          event.getOptions().toString(), false);
        hook.sendMessageEmbeds(embed.build()).queue();
    }
}
