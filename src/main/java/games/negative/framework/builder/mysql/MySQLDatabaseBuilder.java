package games.negative.framework.builder.mysql;

import com.google.common.collect.Lists;
import games.negative.framework.MySQLDatabase;
import games.negative.framework.SQLDatabase;
import games.negative.framework.exception.DriverNotFoundException;
import games.negative.framework.exception.InvalidConnectionException;
import games.negative.framework.model.SQLColumn;
import games.negative.framework.model.SQLColumnType;
import games.negative.framework.model.SQLTable;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MySQLDatabaseBuilder {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final List<SQLTable> tables;

    public MySQLDatabaseBuilder(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.tables = Lists.newArrayList();
    }

    public MySQLTableBuilder withTable(@NotNull String key) {
        return new MySQLTableBuilder(this, key);
    }

    public MySQLDatabaseBuilder addTable(@NotNull SQLTable table) {
        tables.add(table);
        return this;
    }


    public SQLDatabase complete() throws DriverNotFoundException, InvalidConnectionException {
        SQLDatabase mysql = new MySQLDatabase(host, port, database, username, password);

        // Logic for generating tables & columns
        for (SQLTable table : tables) {

            // Check if the table doesn't already exist in the database
            try (PreparedStatement statement = mysql.statement("SHOW TABLES LIKE ?")) {
                statement.setString(1, table.getIdentifier());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Table exists, we will look through all the columns and make sure they exist
                    statement.close();
                    resultSet.close();

                    // Loop through the columns and check if they exist in the database
                    // if they do not we will alter the database to include the new columns
                    Connection connection = mysql.getConnection();
                    if (connection == null)
                        throw new InvalidConnectionException("Connection is null");

                    DatabaseMetaData metaData = connection.getMetaData();

                    SQLColumn parent = null;
                    for (SQLColumn column : table.getColumns()) {
                        ResultSet statusResult = metaData.getColumns(null, null, table.getIdentifier(), column.getIdentifier());
                        if (!statusResult.next()) {
                            // Column does not exist, we will add it
                            // Column does not exist, we will add it
                            String alterQuery = "ALTER TABLE " + table.getIdentifier() +
                                    " ADD COLUMN " + column.getIdentifier() + " " +
                                    column.type().name() + (column.length() != 0 ? "(" + column.length() + ")" : "");

                            alterQuery += (parent == null ? " FIRST" : " AFTER " + parent.getIdentifier());

                            parent = column;

                            try (PreparedStatement alterStatement = mysql.statement(alterQuery)) {
                                alterStatement.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Could not alter table " + table.getIdentifier() + " to add column " + column.getIdentifier());
                                e.printStackTrace();
                            }
                        }
                        statusResult.close();
                    }
                }
            } catch (SQLException e) {
                System.out.println("Could not check if table " + table.getIdentifier() + " exists");
                e.printStackTrace();
            }

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
            try (PreparedStatement statement = mysql.statement(queryBuilder.toString())) {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Could not create table " + table.getIdentifier());
                e.printStackTrace();
            }

        }

        return mysql;
    }

}
