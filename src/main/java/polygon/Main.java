package polygon;

import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.Objects;
import java.util.Scanner;

/**
 * The entry point.
 *
 * <p>Token is expected to be in {@code /src/main/resources/token.txt} as plain text.</p>
 */
public final class Main {
    public static void main(final String[] args) {
        try (final Scanner tokenReader = new Scanner(Objects.requireNonNull(Main.class.getResourceAsStream("/token.txt")))) {
            new Bot(tokenReader.nextLine());
        } catch (final LoginException e) {
            BotLogger.error("Could not login.", e);
        }
    }
}
