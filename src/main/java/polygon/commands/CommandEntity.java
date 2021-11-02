package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

/**
 * Common properties of Discord commands;
 */
public abstract class CommandEntity {
    protected final String name;
    protected final String description;

    protected CommandEntity(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return The name of this entity.
     */
    public final String name() {
        return name;
    }

    /**
     * @return The description of this entity.
     */
    public final String description() {
        return description;
    }

    /**
     * Handles a Discord interaction.
     *
     * @param event The event to handle.
     * @param hook The hook to which to send responses.
     */
    public abstract void handle(final SlashCommandEvent event, final InteractionHook hook);
}
