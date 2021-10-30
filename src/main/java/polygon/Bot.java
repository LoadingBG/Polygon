package polygon;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import polygon.commands.BotCommandManager;
import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Optional;

public final class Bot {
    private static final String GUILD_ID = "695199180263653376";

    private ShardManager shards;
    private final BotCommandManager[] commandManagers = {};

    Bot(final String token) {
        try {
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
        } catch (final LoginException e) {
            BotLogger.terminate("Could not login.", e);
        }
    }

    private void loadCommands(final Guild guild) {
        guild.updateCommands().queue();
        Arrays.stream(commandManagers)
              .map(BotCommandManager::assembleDiscordCommand)
              .map(guild::upsertCommand)
              .forEach(CommandCreateAction::complete);
        BotLogger.info("Commands are loaded.");
    }

    public BotCommandManager[] commandManagers() {
        return commandManagers;
    }

    public void shutdown() {
        BotLogger.info("Shutting down bot...");
        shards.shutdown();
    }
}
