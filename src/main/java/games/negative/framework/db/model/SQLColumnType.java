package games.negative.framework.db.model;

/**
 * Enum for the different types of columns in a SQL table.
 */
public enum SQLColumnType {

    // Numeric
    TINYINT,
    SMALLINT,
    MEDIUMINT,
    INT,
    BIGINT,
    FLOAT,
    DOUBLE,
    DECIMAL,
    LONG,

    // Date and Time
    DATE,
    TIME,
    DATETIME,
    TIMESTAMP,
    YEAR,

    // String
    CHAR,
    VARCHAR,
    TINYTEXT,
    TEXT,
    BOOLEAN,
    MEDIUMTEXT,
    LONGTEXT,
    BINARY,
    VARBINARY,
    TINYBLOB,
    MEDIUMBLOB,
    BLOB,
    LONGBLOB,
    ENUM,
    SET,

    // JSON
    JSON

}
