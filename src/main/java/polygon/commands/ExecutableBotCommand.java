package polygon.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class ExecutableBotCommand implements BotCommand {
    protected final String name;
    protected final String description;
    protected final OptionData[] options;

    protected ExecutableBotCommand(final String name, final String description, final OptionData[] options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }

    @Override
    public final CommandData assembleData() {
        return new CommandData(name, description).addOptions(options);
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final String description() {
        return description;
    }
}
