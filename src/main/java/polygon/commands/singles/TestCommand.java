package polygon.commands.singles;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import polygon.Bot;
import polygon.commands.ExecutableBotCommand;

public final class TestCommand extends ExecutableBotCommand {
    public TestCommand(final JDA jda) {
        super(
                "test",
                "A command to test the limits of slash commands.",
                new OptionData[] { new OptionData(OptionType.STRING, "str", "Test string option.") },
                Bot.OWNER_IDS.stream().map(id -> new CommandPrivilege(CommandPrivilege.Type.USER, true, id)).toArray(CommandPrivilege[]::new)
        );
    }

    @Override
    public void handle(SlashCommandEvent event, InteractionHook hook) {
        hook.sendMessage(event.getCommandString()).queue();
    }
}
