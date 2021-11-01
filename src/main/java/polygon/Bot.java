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

public final class Bot {
    public static final List<Long> OWNER_IDS;
    static {
        final List<Long> ids = new ArrayList<>();
        ids.add(616969228972458008L);
        OWNER_IDS = Collections.unmodifiableList(ids);
    }
    private final JDA jda;
    private final BotCommand[] commands;

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
            final Guild guild = Objects.requireNonNull(jda.getGuildById("904753787888087080"));
            guild.updateCommands().complete();
            Arrays.stream(commands).forEach(cmd -> cmd.register(guild));
            BotLogger.info("Commands are loaded.");
        } catch (final NullPointerException e) {
            BotLogger.error("Guild cannot be found.", e);
        }
    }

    public BotCommand[] commands() {
        return commands;
    }

    public void shutdown() {
        BotLogger.info("Shutting down bot...");
        jda.shutdown();
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            BotLogger.terminate("Bot was interrupted while shutting down.", e);
            Thread.currentThread().interrupt();
        }
        System.exit(0);
    }
}
