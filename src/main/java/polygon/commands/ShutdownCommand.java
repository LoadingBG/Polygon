package polygon.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import polygon.Bot;

public final class ShutdownCommand extends ExecutableBotCommand {
    private final Bot bot;

    public ShutdownCommand(final Bot bot) {
        super(
                "shutdown",
                "Shuts down this bot.",
                new OptionData[0],
                Bot.OWNER_IDS.stream()
                        .map(id -> new CommandPrivilege(CommandPrivilege.Type.USER, true, id))
                        .toArray(CommandPrivilege[]::new)
        );
        this.bot = bot;
    }

    @Override
    public void handle(SlashCommandEvent event, InteractionHook hook) {
        hook.sendMessage("Bot is shutting down...").queue();
        bot.shutdown();
    }
}
