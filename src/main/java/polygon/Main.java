package polygon;

import polygon.utils.BotLogger;

import java.io.IOException;
import java.util.Objects;

public final class Main {
    public static void main(final String[] args) {
        try {
            final String token = new String(Objects.requireNonNull(Main.class.getResourceAsStream("/token.txt")).readAllBytes());
            final Bot bot = new Bot(token);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    bot.shutdown();
                    Thread.sleep(1000);
                } catch (final InterruptedException e) {
                    BotLogger.error("Interrupted while shutting down.", e);
                    Thread.currentThread().interrupt();
                }
            }));
        } catch (final IOException | NullPointerException e) {
            BotLogger.error("Could not read token.", e);
        }
    }
}
