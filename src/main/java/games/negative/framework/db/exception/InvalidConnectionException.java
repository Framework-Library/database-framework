package games.negative.framework.db.exception;

import java.sql.SQLException;

/**
 * Exception thrown when a connection is invalid.
 */
public class InvalidConnectionException extends SQLException {

    public InvalidConnectionException(String reason) {
        super(reason);
    }
}
