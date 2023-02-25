package games.negative.framework.db.exception;

/**
 * Exception thrown when a driver is not found.
 */
public class DriverNotFoundException extends RuntimeException {

    public DriverNotFoundException(String message) {
        super(message);
    }
}
