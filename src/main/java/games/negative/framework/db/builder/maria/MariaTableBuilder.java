package games.negative.framework.db.builder.maria;

import games.negative.framework.db.model.SQLColumn;
import games.negative.framework.db.model.SQLColumnType;
import games.negative.framework.db.model.SQLTable;
import games.negative.framework.db.structure.SQLTableImpl;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class MariaTableBuilder {

    private final MariaDatabaseBuilder parent;
    private final String key;
    private final LinkedList<SQLColumn> columns;
    public MariaTableBuilder(MariaDatabaseBuilder parent, String key) {
        this.parent = parent;
        this.key = key;
        this.columns = new LinkedList<>();
    }

    public MariaColumnBuilder withColumn(@NotNull String key, @NotNull SQLColumnType type) {
        return new MariaColumnBuilder(this, key, type);
    }

    public MariaColumnBuilder withColumn(@NotNull String key, @NotNull SQLColumnType type, int length) {
        return new MariaColumnBuilder(this, key, type, length);
    }

    public MariaTableBuilder addColumn(@NotNull SQLColumn column) {
        columns.add(column);
        return this;
    }

    public void build() {
        SQLTable table = new SQLTableImpl(key, columns);
        parent.addTable(table);
    }

}
