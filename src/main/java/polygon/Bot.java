package polygon;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import polygon.commands.BotCommand;
import polygon.commands.run.RunCommand;
import polygon.commands.singles.PingCommand;
import polygon.commands.singles.ShutdownCommand;
import polygon.commands.singles.TestCommand;
import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The bot itself.
 */
public final class Bot {
    /**
     * A list of IDs of users who are considered
     * owners of this bot.
     */
    public static final List<Long> OWNER_IDS;
    static {
        final List<Long> ids = new ArrayList<>();
        // Add owner IDs below
        ids.add(616969228972458008L);
        // Add owner IDs above
        OWNER_IDS = Collections.unmodifiableList(ids);
    }

    /**
     * The connection.
     */
    private final JDA jda;

    /**
     * The available commands.
     * @see BotCommand
     */
    private final BotCommand[] commands;

    /**
     * Creates a bot.
     *
     * @param token The token for the bot.
     * @throws LoginException If the token is invalid.
     */
    Bot(final String token) throws LoginException {
        jda = JDABuilder
                .createDefault(token)
                .addEventListeners(new Listener(this))
                .build();
        try {
            jda.awaitReady();
        } catch (final InterruptedException e) {
            BotLogger.error("Connection was interrupted while getting ready.", e);
            Thread.currentThread().interrupt();
        }
        BotLogger.info("Bot is online!");

        commands = new BotCommand[] {
                new PingCommand(),
                new ShutdownCommand(this),
                new TestCommand(jda),

                new RunCommand()
        };
        try {
            // Change ID of the server here                              vvvvvvvvvvvvvvvvvv
            final Guild guild = Objects.requireNonNull(jda.getGuildById("904753787888087080"));
            guild.updateCommands().complete();
            Arrays.stream(commands).forEach(cmd -> cmd.register(guild));
            BotLogger.info("Commands are loaded.");
        } catch (final NullPointerException e) {
            BotLogger.error("Guild cannot be found.", e);
        }
    }

    /**
     * @return The supported commands.
     */
    public BotCommand[] commands() {
        return commands;
    }

    /**
     * Shuts the bot down.
     * <p>This is the preffered way to stop the program. Any
     * "shutdown handlers" should be invoked here.</p>
     */
    public void shutdown() {
        BotLogger.info("Shutting down bot...");
        jda.shutdown();
        // Try to wait for the connection to close
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            BotLogger.error("Bot was interrupted while shutting down.", e);
            Thread.currentThread().interrupt();
        }
        // Add any "shutdown handlers" below
        // Add any "shutdown handlers" above
        System.exit(0);
    }
}
