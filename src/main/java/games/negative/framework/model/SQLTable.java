package games.negative.framework.model;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Interface for objects that represent a table in a database.
 */
public interface SQLTable extends Unique<String> {

    /**
     * Get the primary column of the table.
     * @return The primary column.
     */
    @NotNull
    SQLColumn getPrimaryColumn();

    /**
     * Get the columns of the table.
     * @return The columns.
     */
    @NotNull
    LinkedList<SQLColumn> getColumns();

}
