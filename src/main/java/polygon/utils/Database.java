package polygon.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A utility class for working with a database.
 */
public final class Database {
    private Database() {}

    private static final HikariDataSource SOURCE;
    static {
        try {
            final File dbFile = new File(DBConstants.DB_FILENAME);
            if (!dbFile.exists()) {
                if (dbFile.createNewFile()) {
                    BotLogger.info("Database created");
                } else {
                    BotLogger.error("Failed to create database.");
                    System.exit(-1);
                }
            }
        } catch (final IOException e) {
            BotLogger.error("Failed to create database.", e);
        }

        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + DBConstants.DB_FILENAME);
        config.setConnectionTestQuery("SELECT 1");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(20);
        SOURCE = new HikariDataSource(config);
    }

    /**
     * Prepares a query to execute. The prepared statement must be closed after use.
     *
     * @param query The query template.
     * @param params Any values to be escaped.
     * @return The prepared statement to execute.
     * @throws SQLException If something goes wrong.
     */
    private static PreparedStatement prepareStatement(final String query, final Object... params) throws SQLException {
        final PreparedStatement statement = SOURCE.getConnection().prepareStatement(query);
        for (int i = 0; i < params.length; ++i) {
            final Object curr = params[i];
            if (curr instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) curr);
            } else if (curr instanceof Long) {
                statement.setLong(i + 1, (Long) curr);
            } else if (curr instanceof String) {
                statement.setString(i + 1, (String) curr);
            }
        }
        return statement;
    }
}
