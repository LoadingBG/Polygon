package polygon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        try {
            final String token = new String(Main.class.getResourceAsStream("/token.txt").readAllBytes());
            final Bot bot = new Bot(token);
            Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
        } catch (final IOException e) {
            LOG.error("Could not read token.", e);
        }
    }
}
