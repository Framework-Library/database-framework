package games.negative.framework.db.builder.maria;

import games.negative.framework.db.model.SQLColumn;
import games.negative.framework.db.model.SQLColumnType;
import games.negative.framework.db.structure.SQLColumnImpl;

public class MariaColumnBuilder {

    private final MariaTableBuilder parent;
    private final String key;
    private final SQLColumnType type;
    private final int length;
    private boolean primary;

    public MariaColumnBuilder(MariaTableBuilder parent, String key, SQLColumnType type) {
        this(parent, key, type, 0);
    }

    public MariaColumnBuilder(MariaTableBuilder parent, String key, SQLColumnType type, int length) {
        this.parent = parent;
        this.key = key;
        this.type = type;
        this.length = length;
    }

    public MariaColumnBuilder setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public void build() {
        SQLColumn column = new SQLColumnImpl(key, type, length, primary);
        parent.addColumn(column);
    }

}
