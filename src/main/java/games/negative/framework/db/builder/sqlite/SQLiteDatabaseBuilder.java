package games.negative.framework.db.builder.sqlite;

import com.google.common.collect.Lists;
import games.negative.framework.db.SQLDatabase;
import games.negative.framework.db.SQLiteDatabase;
import games.negative.framework.db.exception.DriverNotFoundException;
import games.negative.framework.db.exception.InvalidConnectionException;
import games.negative.framework.db.model.SQLColumn;
import games.negative.framework.db.model.SQLColumnType;
import games.negative.framework.db.model.SQLTable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class SQLiteDatabaseBuilder {

    private final File file;
    private final List<SQLTable> tables;

    public SQLiteDatabaseBuilder(File file) {
        this.file = file;
        this.tables = Lists.newArrayList();
    }

    public SQLiteTableBuilder withTable(@NotNull String key) {
        return new SQLiteTableBuilder(this, key);
    }

    public SQLiteDatabaseBuilder addTable(@NotNull SQLTable table) {
        tables.add(table);
        return this;
    }


    // todo: Make this a lot cleaner
    public SQLDatabase complete() throws DriverNotFoundException, InvalidConnectionException, IOException, SQLException {
        SQLDatabase db = new SQLiteDatabase(file);

        List<String> tableNames = Lists.newArrayList();

        try (PreparedStatement statement = db.statement("SELECT name FROM sqlite_master WHERE type='table'")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("name"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Logic for generating tables & columns
        for (SQLTable table : tables) {

            if (tableNames.contains(table.getIdentifier())) {
                // Table exists, scale columns
                Connection connection = db.getConnection();
                if (connection == null)
                    throw new InvalidConnectionException("Connection is null");

                DatabaseMetaData metaData = connection.getMetaData();
                SQLColumn parent = null;
                for (SQLColumn column : table.getColumns()) {
                    ResultSet statusResult = metaData.getColumns(null, null, table.getIdentifier(), column.getIdentifier());
                    if (!statusResult.next()) {
                        // Column does not exist, we will add it
                        String alterQuery = "ALTER TABLE " + table.getIdentifier() +
                                " ADD COLUMN " + column.getIdentifier() + " " +
                                column.type().name() + (column.length() != 0 ? "(" + column.length() + ")" : "");

                        alterQuery += (parent == null ? " FIRST" : " AFTER " + parent.getIdentifier());

                        parent = column;
                        try (PreparedStatement alterStatement = db.statement(alterQuery)) {
                            alterStatement.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("Could not alter table " + table.getIdentifier() + " to add column " + column.getIdentifier());
                            e.printStackTrace();
                        }

                    }
                    statusResult.close();
                }
            } else {
                // Generate table
                // Create an SQL table with the table identifier if the table does not exist using an SQL query
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("CREATE TABLE IF NOT EXISTS ").append(table.getIdentifier())
                        .append(" (");

                // Loop through the columns and append them to the query
                for (SQLColumn column : table.getColumns()) {
                    String identifier = column.getIdentifier();
                    SQLColumnType type = column.type();
                    boolean primary = column.primary();
                    int length = column.length();

                    // Append the column identifier
                    queryBuilder.append(identifier).append(" ");

                    // Append the column type
                    queryBuilder.append(type.name());

                    // Append the column length if it is not 0
                    if (length != 0) {
                        queryBuilder.append("(").append(length).append(")");
                    }

                    // Append the primary key if it is true
                    if (primary) {
                        queryBuilder.append(" PRIMARY KEY");
                    }

                    // Append a comma if it is not the last column
                    if (table.getColumns().indexOf(column) != table.getColumns().size() - 1) {
                        queryBuilder.append(", ");
                    }
                }

                // Close the query
                queryBuilder.append(");");

                // Execute the query
                try (PreparedStatement statement = db.statement(queryBuilder.toString())) {
                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Could not create table " + table.getIdentifier());
                    e.printStackTrace();
                }

            }

        }
        return db;
    }
}
