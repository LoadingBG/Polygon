package polygon.commands.run;

import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import polygon.commands.BotSubcommand;
import polygon.commands.BotSubcommandGroup;
import polygon.commands.GroupingBotCommand;

public final class RunCommand extends GroupingBotCommand {
    public RunCommand() {
        super(
                "run",
                "Runs a snippet of code",
                new BotSubcommandGroup[0],
                new BotSubcommand[] {
                        new Languages(),
                        new Code()
                },
                new CommandPrivilege[0]
        );
    }
}
