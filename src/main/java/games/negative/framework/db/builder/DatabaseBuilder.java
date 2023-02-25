package games.negative.framework.db.builder;

import games.negative.framework.db.builder.maria.MariaDatabaseBuilder;
import games.negative.framework.db.builder.mysql.MySQLDatabaseBuilder;
import games.negative.framework.db.builder.sqlite.SQLiteDatabaseBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DatabaseBuilder {

    public static MySQLDatabaseBuilder mysql(@NotNull String host, int port, @NotNull String database, @NotNull String username, @NotNull String password) {
        return new MySQLDatabaseBuilder(host, port, database, username, password);
    }

    public static MariaDatabaseBuilder maria(@NotNull String host, int port, @NotNull String database, @NotNull String username, @NotNull String password) {
        return new MariaDatabaseBuilder(host, port, database, username, password);
    }

    public static SQLiteDatabaseBuilder sqlite(@NotNull File file) {
        return new SQLiteDatabaseBuilder(file);
    }
}
