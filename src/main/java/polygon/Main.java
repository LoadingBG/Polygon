package polygon;

import polygon.utils.BotLogger;

import javax.security.auth.login.LoginException;
import java.util.Objects;
import java.util.Scanner;

public final class Main {
    public static void main(final String[] args) {
        try (final Scanner tokenReader = new Scanner(Objects.requireNonNull(Main.class.getResourceAsStream("/token.txt")))) {
            new Bot(tokenReader.nextLine());
        } catch (final LoginException e) {
            BotLogger.terminate("Could not login.", e);
        }
    }
}
