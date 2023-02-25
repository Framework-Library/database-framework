package games.negative.framework.db;

import games.negative.framework.db.exception.DriverNotFoundException;
import games.negative.framework.db.exception.InvalidConnectionException;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabase implements SQLDatabase {

    private final Connection connection;
    public SQLiteDatabase(File location) throws DriverNotFoundException, InvalidConnectionException, IOException {
        if (!location.exists()) {
            File parent = location.getParentFile();
            if (parent != null)
                parent.mkdirs();

            location.createNewFile();
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new DriverNotFoundException("Could not find driver 'org.sqlite.JDBC'!");
        }

        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + location.getAbsolutePath());
        } catch (SQLException e) {
            throw new InvalidConnectionException("Could not connect to database '" + location.getAbsolutePath() + "'!");
        }

    }

    @Override
    public @Nullable Connection getConnection() {
        return connection;
    }
}
