package games.negative.framework.db.builder.mysql;

import games.negative.framework.db.model.SQLColumn;
import games.negative.framework.db.model.SQLColumnType;
import games.negative.framework.db.structure.SQLColumnImpl;

public class MySQLColumnBuilder {

    private final MySQLTableBuilder parent;
    private final String key;
    private final SQLColumnType type;
    private final int length;
    private boolean primary;

    public MySQLColumnBuilder(MySQLTableBuilder parent, String key, SQLColumnType type) {
        this(parent, key, type, 0);
    }

    public MySQLColumnBuilder(MySQLTableBuilder parent, String key, SQLColumnType type, int length) {
        this.parent = parent;
        this.key = key;
        this.type = type;
        this.length = length;
    }

    public MySQLColumnBuilder setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public void build() {
        SQLColumn column = new SQLColumnImpl(key, type, length, primary);
        parent.addColumn(column);
    }
}
