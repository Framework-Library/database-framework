package games.negative.framework.model;

/**
 * Interface for objects that represent a column in a database.
 */
public interface SQLColumn extends Unique<String> {

    /**
     * The length of the data type. For example, a VARCHAR(255) would return 255.
     * @return The length of the data type.
     */
    int length();

    /**
     * The type of the column.
     * @return  The type of the column.
     */
    SQLColumnType type();

    /**
     * Represents if the column is a primary key.
     * @return True if the column is a primary key, false otherwise.
     */
    boolean primary();

}
