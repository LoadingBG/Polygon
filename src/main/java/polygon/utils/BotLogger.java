package polygon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: make better logger

/**
 * The global logger for the bot.
 */
public final class BotLogger {
    private BotLogger() {}

    private static final Logger LOG = LoggerFactory.getLogger("Polygon global logger");

    /**
     * Logs the message as info.
     *
     * @param message The message to log.
     */
    public static void info(final String message) {
        LOG.info(message);
    }

    /**
     * Logs the exception along with a message.
     *
     * @param message The message to log.
     * @param error The error to log.
     */
    public static void error(final String message, final Throwable error) {
        LOG.error(message, error);
    }
}
