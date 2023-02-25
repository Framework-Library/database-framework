package games.negative.framework.model;

/**
 * Interface for objects that have a unique identifier.
 * @param <T> The type of the identifier.
 */
public interface Unique<T> {

    T getIdentifier();

}
