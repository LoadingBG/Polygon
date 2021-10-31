package polygon;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import polygon.commands.BotCommand;
import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Optional;

public final class Bot {
    private static final String GUILD_ID = "695199180263653376";

    private final JDA jda;
    private final BotCommand[] commands = {};

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

        Optional.ofNullable(jda.getGuildById(GUILD_ID))
                .ifPresentOrElse(this::loadCommands, () -> BotLogger.terminate("Guild cannot be found."));
    }

    private void loadCommands(final Guild guild) {
        guild.updateCommands().queue();
        Arrays.stream(commands)
              .map(BotCommand::assembleData)
              .map(guild::upsertCommand)
              .forEach(CommandCreateAction::complete);
        BotLogger.info("Commands are loaded.");
    }

    public BotCommand[] commands() {
        return commands;
    }

    public void shutdown() {
        BotLogger.info("Shutting down bot...");
        jda.shutdown();
    }
}
