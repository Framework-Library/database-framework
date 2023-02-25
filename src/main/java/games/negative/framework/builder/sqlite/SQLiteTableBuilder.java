package games.negative.framework.builder.sqlite;

import games.negative.framework.model.SQLColumn;
import games.negative.framework.model.SQLColumnType;
import games.negative.framework.model.SQLTable;
import games.negative.framework.structure.SQLTableImpl;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class SQLiteTableBuilder {

    private final SQLiteDatabaseBuilder parent;
    private final String key;
    private final LinkedList<SQLColumn> columns;
    public SQLiteTableBuilder(SQLiteDatabaseBuilder parent, String key) {
        this.parent = parent;
        this.key = key;
        this.columns = new LinkedList<>();
    }

    public SQLiteColumnBuilder withColumn(@NotNull String key, @NotNull SQLColumnType type) {
        return new SQLiteColumnBuilder(this, key, type);
    }

    public SQLiteColumnBuilder withColumn(@NotNull String key, @NotNull SQLColumnType type, int length) {
        return new SQLiteColumnBuilder(this, key, type, length);
    }

    public SQLiteTableBuilder addColumn(@NotNull SQLColumn column) {
        columns.add(column);
        return this;
    }

    public void build() {
        SQLTable table = new SQLTableImpl(key, columns);
        parent.addTable(table);
    }

}
