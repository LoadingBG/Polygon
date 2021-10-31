package polygon;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import polygon.commands.BotCommand;
import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Objects;

public final class Bot {
    public static final long[] OWNER_IDS = {
            616969228972458008L
    };
    private final JDA jda;
    private final BotCommand[] commands;

    Bot(final String token) throws LoginException {
        commands = new BotCommand[] {

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
            final Guild guild = Objects.requireNonNull(jda.getGuildById("695199180263653376"));
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
        System.exit(0);
    }
}
