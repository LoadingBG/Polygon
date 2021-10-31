package polygon.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class ExecutableBotCommand extends BotCommand {
    protected final OptionData[] options;

    protected ExecutableBotCommand(final String name, final String description, final OptionData[] options) {
        super(name, description);
        this.options = options;
    }

    @Override
    public final CommandData assembleData() {
        return new CommandData(name, description).addOptions(options);
    }
}
