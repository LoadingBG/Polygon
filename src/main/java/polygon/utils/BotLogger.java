package polygon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: make better logger
public final class BotLogger {
    private BotLogger() {}

    private static final Logger LOG = LoggerFactory.getLogger("Polygon global logger");

    public static void info(final String message) {
        LOG.info(message);
    }

    public static void error(final String message, final Throwable error) {
        LOG.error(message, error);
    }

    public static void errorFormat(final String message, final Object... args) {
        LOG.error(message, args);
    }

    public static void terminate(final String message) {
        LOG.error("{} Terminating...", message);
        System.exit(-1);
    }

    public static void terminate(final String message, final Throwable error) {
        LOG.error(message, error);
        System.exit(-1);
    }
}
