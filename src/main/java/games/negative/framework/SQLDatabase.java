package games.negative.framework;

import games.negative.framework.exception.InvalidConnectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This interface is used to represent a database.
 */
public interface SQLDatabase {

    /**
     * This method is used to get the connection to the database.
     * @return The connection to the database.
     * @throws NullPointerException If the connection is null.
     */
    @Nullable
    Connection getConnection();

    /**
     * This method is used to close the connection to the database.
     * @throws InvalidConnectionException If the connection is null.
     * @throws SQLException If the connection could not be closed.
     */
    default void close() throws InvalidConnectionException, SQLException {
        Connection connection = getConnection();
        if (connection == null)
            throw new InvalidConnectionException("Could not close the connection because the connection is (already) null.");

        connection.close();
    }

    /**
     * This method is used to create a statement.
     * @param query The query to execute.
     * @return The statement object.
     * @throws InvalidConnectionException If the connection is null.
     * @throws SQLException If the statement could not be created.
     */
    default PreparedStatement statement(@NotNull String query) throws InvalidConnectionException, SQLException {
        Connection connection = getConnection();
        if (connection == null)
            throw new InvalidConnectionException("Could not create a statement because of an invalid connection. Check the validity of your database connection!");

        return connection.prepareStatement(query);
    }
}
