package games.negative.framework.db.structure;

import games.negative.framework.db.model.SQLColumn;
import games.negative.framework.db.model.SQLColumnType;

public class SQLColumnImpl implements SQLColumn {

    private final String key;
    private final SQLColumnType type;
    private final int length;
    private final boolean primary;

    public SQLColumnImpl(String key, SQLColumnType type, int length, boolean primary) {
        this.key = key;
        this.type = type;
        this.length = length;
        this.primary = primary;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public SQLColumnType type() {
        return type;
    }

    @Override
    public boolean primary() {
        return primary;
    }

    @Override
    public String getIdentifier() {
        return key;
    }
}
