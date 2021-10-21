package polygon;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polygon.commands.BotCommand;
import polygon.commands.TestCommand;
import polygon.commands.global.PingCommand;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class Bot {
    private static final String TEST_GUILD_ID = "695199180263653376";
    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

    private ShardManager shards;
    private final BotCommand[] commandsForTest = {
            new TestCommand(),

            new PingCommand(),
    };
    private final BotCommand[] commands = {};

    Bot(final String token) {
        try {
            shards = DefaultShardManagerBuilder
                    .createDefault(token)
                    .addEventListeners(new Listener(this))
                    .build();
            awaitReady();
            checkCommands();
            checkTestCommands();
        } catch (LoginException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void awaitReady() {
        for (final JDA shard : shards.getShards()) {
            try {
                shard.awaitReady();
            } catch (final InterruptedException e) {
                LOG.error("Shard was interrupted while getting ready.", e);
                Thread.currentThread().interrupt();
            }
        }
        LOG.info("Shards are ready!");
    }

    private void upsertCommand(final BotCommand command) {
        LOG.debug("Adding a global command: {}", command.data().getName());
        for (JDA shard : shards.getShards()) {
            shard.upsertCommand(command.data()).complete();
        }
        LOG.info("Added global command: {}", command.data().getName());
    }

    private void deleteCommand(final Command command) {
        LOG.debug("Deleting a global command: {}", command.getName());
        for (JDA shard : shards.getShards()) {
            shard.deleteCommandById(command.getId()).complete();
        }
        LOG.info("Deleted global command: {}", command.getName());
    }

    private void checkCommands() {
        final List<Command> discordCommands = shards
                .getShards()
                .stream()
                .flatMap(shard -> shard.retrieveCommands().complete().stream())
                .toList();
        // Globals
        for (BotCommand botCmd : commands) {
            if (discordCommands.stream().map(Command::getName).noneMatch(botCmd::shouldHandle)) {
                upsertCommand(botCmd);
            }
        }
        for (Command discordCmd : discordCommands) {
            if (Arrays.stream(commands).noneMatch(cmd -> cmd.shouldHandle(discordCmd.getName()))) {
                deleteCommand(discordCmd);
            }
        }
        // Testing
        final Guild testGuild = shards.getGuildById(TEST_GUILD_ID);
        if (testGuild == null) {
            LOG.error("Test guild cannot be found.");
            return;
        }
        Arrays.stream(commandsForTest).map(BotCommand::data).map(testGuild::upsertCommand).forEach(CommandCreateAction::complete);
        LOG.info("Commands are loaded.");
        //final List<Command> testCommands = testGuild.retrieveCommands().complete();
        //for (BotCommand botCmd : commandsForTest) {
        //    if (testCommands.stream().map(Command::getName).noneMatch(botCmd::shouldHandle)) {
        //        testGuild.upsertCommand(botCmd.data()).queue();
        //    }
        //}
        //for (Command discordCmd : testCommands) {
        //    if (Arrays.stream(commandsForTest).noneMatch(cmd -> cmd.shouldHandle(discordCmd.getName()))) {
        //        testGuild.deleteCommandById(discordCmd.getId()).queue();
        //    }
        //}
    }

    private void checkTestCommands() {
        final Guild guild = shards.getGuildById(TEST_GUILD_ID);
        if (guild == null) {
            LOG.error("Test guild is null.");
            System.exit(-1);
        }
        final List<String> registeredCommands = guild
                .retrieveCommands()
                .complete()
                .stream()
                .map(Command::getName)
                .toList();
        for (BotCommand command : commands) {
            if (!registeredCommands.contains(command.data().getName())) {
                guild.upsertCommand(command.data()).queue();
            }
        }
    }

    public Optional<BotCommand> commandToHandle(final String commandName) {
        return Arrays.stream(commands)
                .filter(cmd -> cmd.shouldHandle(commandName))
                .findFirst()
                .or(() -> Arrays.stream(commandsForTest).filter(cmd -> cmd.shouldHandle(commandName)).findFirst());
    }

    public BotCommand[] commands() {
        return commands;
    }

    public BotCommand[] testCommands() {
        return commandsForTest;
    }

    public void shutdown() {
        LOG.info("Shutting down bot...");
        shards.shutdown();
    }
}
