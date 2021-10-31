package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public abstract class CommandEntity {
    protected final String name;
    protected final String description;

    protected CommandEntity(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public final String name() {
        return name;
    }

    public final String description() {
        return description;
    }

    public abstract void handle(final SlashCommandEvent event, final InteractionHook hook);
}
