package polygon;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import polygon.commands.BotCommand;
import polygon.commands.ShutdownCommand;
import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.*;

public final class Bot {
    private static final List<Long> IDS = new ArrayList<>();
    static {
        IDS.add(616969228972458008L);
    }
    public static final List<Long> OWNER_IDS = Collections.unmodifiableList(IDS);
    private final JDA jda;
    private final BotCommand[] commands;

    Bot(final String token) throws LoginException {
        commands = new BotCommand[] {
                new ShutdownCommand(this)
        };

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

        try {
            final Guild guild = Objects.requireNonNull(jda.getGuildById("900269526426451988"));
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
