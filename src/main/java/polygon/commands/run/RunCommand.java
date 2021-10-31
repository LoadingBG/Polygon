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
                new BotSubcommandGroup[0], // TODO: fix
                new BotSubcommand[] {
                        new Languages()
                },
                new CommandPrivilege[0]
        );
    }
}
