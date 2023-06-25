package games.negative.framework.db;

import games.negative.framework.db.exception.DriverNotFoundException;
import games.negative.framework.db.exception.InvalidConnectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDatabase implements SQLDatabase {

    private final Connection connection;
    public MariaDatabase(@NotNull String host, int port, @NotNull String database, @NotNull String username, @NotNull String password) throws DriverNotFoundException, InvalidConnectionException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DriverNotFoundException("Could not find driver 'org.mariadb.jdbc.Driver'!");
        }

        try {
            String url = "jdbc:mariadb://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password + "&useSSL=false" + "&autoReconnect=true";
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new InvalidConnectionException("Could not connect to database '" + database + "' on host '" + host + "' with username '" + username + "'!");
        }
    }

    @Override
    public @Nullable Connection getConnection() {
        return connection;
    }
}
