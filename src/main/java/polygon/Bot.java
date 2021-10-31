package polygon;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import polygon.commands.BotCommand;
import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Optional;

public final class Bot {
    private static final String GUILD_ID = "695199180263653376";

    private final ShardManager shards;
    private final BotCommand[] commands = {};

    Bot(final String token) throws LoginException {
        shards = DefaultShardManagerBuilder
                .createDefault(token)
                .addEventListeners(new Listener(this))
                .build();
        shards.getShards().forEach(shard -> {
            try {
                shard.awaitReady();
            } catch (final InterruptedException e) {
                BotLogger.error("Shard was interrupted while getting ready.", e);
                Thread.currentThread().interrupt();
            }
        });
        BotLogger.info("Shards are ready!");

        Optional.ofNullable(shards.getGuildById(GUILD_ID))
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
        shards.shutdown();
    }
}
