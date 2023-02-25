package games.negative.framework.db.structure;

import games.negative.framework.db.model.SQLColumn;
import games.negative.framework.db.model.SQLTable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class SQLTableImpl implements SQLTable {

    private final String key;
    private final LinkedList<SQLColumn> columns;
    private final SQLColumn primaryColumn;

    public SQLTableImpl(String key, LinkedList<SQLColumn> columns) {
        this.key = key;
        this.columns = columns;
        this.primaryColumn = columns.stream().filter(SQLColumn::primary).findFirst().orElse(null);
    }

    @Override
    public @NotNull SQLColumn getPrimaryColumn() {
        return primaryColumn;
    }

    @Override
    public @NotNull LinkedList<SQLColumn> getColumns() {
        return columns;
    }

    @Override
    public String getIdentifier() {
        return key;
    }
}
