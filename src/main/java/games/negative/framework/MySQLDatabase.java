package games.negative.framework;

import games.negative.framework.exception.DriverNotFoundException;
import games.negative.framework.exception.InvalidConnectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to represent a MySQL database.
 */
public class MySQLDatabase implements SQLDatabase {

    private final Connection connection;

    public MySQLDatabase(@NotNull String host, int port, @NotNull String database, @NotNull String username, @NotNull String password) throws DriverNotFoundException, InvalidConnectionException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DriverNotFoundException("Could not find driver 'com.mysql.jdbc.Driver'!");
        }

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException e) {
            throw new InvalidConnectionException("Could not connect to database '" + database + "' on host '" + host + "' with username '" + username + "'!");
        }
    }

    @Override
    public @Nullable Connection getConnection() {
        return connection;
    }
}
