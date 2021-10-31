package polygon.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

public abstract class ExecutableBotCommand extends BotCommand {
    protected final OptionData[] options;

    protected ExecutableBotCommand(final String name,
                                   final String description,
                                   final OptionData[] options,
                                   final CommandPrivilege[] privileges) {
        super(name, description, privileges);
        this.options = options;
    }

    @Override
    public final CommandData assembleData() {
        final CommandData data = new CommandData(name, description).addOptions(options);
        if (privileges.length > 0) {
            data.setDefaultEnabled(false);
        }
        return data;
    }
}
