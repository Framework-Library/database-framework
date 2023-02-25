package games.negative.framework.model;

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
