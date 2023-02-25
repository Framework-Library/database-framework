package games.negative.framework.db.builder.mysql;

import games.negative.framework.db.model.SQLColumn;
import games.negative.framework.db.model.SQLColumnType;
import games.negative.framework.db.model.SQLTable;
import games.negative.framework.db.structure.SQLTableImpl;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class MySQLTableBuilder {

    private final MySQLDatabaseBuilder parent;
    private final String key;
    private final LinkedList<SQLColumn> columns;
    public MySQLTableBuilder(MySQLDatabaseBuilder parent, String key) {
        this.parent = parent;
        this.key = key;
        this.columns = new LinkedList<>();
    }

    public MySQLColumnBuilder withColumn(@NotNull String key, @NotNull SQLColumnType type) {
        return new MySQLColumnBuilder(this, key, type);
    }

    public MySQLColumnBuilder withColumn(@NotNull String key, @NotNull SQLColumnType type, int length) {
        return new MySQLColumnBuilder(this, key, type, length);
    }

    public MySQLTableBuilder addColumn(@NotNull SQLColumn column) {
        columns.add(column);
        return this;
    }

    public void build() {
        SQLTable table = new SQLTableImpl(key, columns);
        parent.addTable(table);
    }

}
