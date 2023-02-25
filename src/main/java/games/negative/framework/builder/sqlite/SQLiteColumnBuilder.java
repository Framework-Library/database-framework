package games.negative.framework.builder.sqlite;

import games.negative.framework.model.SQLColumn;
import games.negative.framework.model.SQLColumnType;
import games.negative.framework.structure.SQLColumnImpl;

public class SQLiteColumnBuilder {

    private final SQLiteTableBuilder parent;
    private final String key;
    private final SQLColumnType type;
    private final int length;
    private boolean primary;

    public SQLiteColumnBuilder(SQLiteTableBuilder parent, String key, SQLColumnType type) {
        this(parent, key, type, 0);
    }

    public SQLiteColumnBuilder(SQLiteTableBuilder parent, String key, SQLColumnType type, int length) {
        this.parent = parent;
        this.key = key;
        this.type = type;
        this.length = length;
    }

    public SQLiteColumnBuilder setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public void build() {
        SQLColumn column = new SQLColumnImpl(key, type, length, primary);
        parent.addColumn(column);
    }

}
