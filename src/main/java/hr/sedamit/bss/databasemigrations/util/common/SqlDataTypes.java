/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.common;

/**
 * Created by J0ka on 18/02/22
 *
 */
public class SqlDataTypes {

	/**
	 * MSSQL: Datatypes
	 */
	public static final String mssql_BIGINT = "BIGINT";
	public static final String mssql_BINARY_n = "BINARY";
	public static final String mssql_BIT = "BIT";
	public static final String mssql_CHAR_n = "CHAR";
	public static final String mssql_CHARACTER_n = "CHARACTER";
	public static final String mssql_DATE = "DATE";
	public static final String mssql_DATETIME = "DATETIME";
	public static final String mssql_DATETIME2_p = "DATETIME2";
	public static final String mssql_DATETIMEOFFSET = "DATETIMEOFFSET";
	public static final String mssql_DECIMAL_p_s = "DECIMAL";
	public static final String mssql_DEC_p_s = "DEC";
	public static final String mssql_DOUBLE_PRECISION = "DOUBLE PRECISION";
	public static final String mssql_FLOAT_p = "FLOAT";
	public static final String mssql_IMAGE = "IMAGE";
	public static final String mssql_INT = "INT";
	public static final String mssql_INTEGER = "INTEGER";
	public static final String mssql_MONEY = "MONEY";
	public static final String mssql_NCHAR_n = "NCHAR";
	public static final String mssql_NTEXT = "NTEXT";
	public static final String mssql_NUMERIC_p_s = "NUMERIC";
	public static final String mssql_NVARCHAR_n = "NVARCHAR";
	public static final String mssql_NVARCHAR_max = "NVARCHAR";
	public static final String mssql_REAL = "REAL";
	public static final String mssql_ROWVERSION = "ROWVERSION";
	public static final String mssql_SMALLDATETIME = "SMALLDATETIME";
	public static final String mssql_SMALLINT = "SMALLINT";
	public static final String mssql_SMALLMONEY = "SMALLMONEY";
	public static final String mssql_TEXT = "TEXT";
	public static final String mssql_TIME_p = "TIME";
	public static final String mssql_TIMESTAMP = "TIMESTAMP";
	public static final String mssql_TINYINT = "TINYINT";
	public static final String mssql_UNIQUEIDENTIFIER = "UNIQUEIDENTIFIER";
	public static final String mssql_VARBINARY_n = "VARBINARY";
	public static final String mssql_VARBINARY_max = "VARBINARY";
	public static final String mssql_VARCHAR_n = "VARCHAR";
	public static final String mssql_VARCHAR_max = "VARCHAR";
	public static final String mssql_XML = "XML";

	/**
	 * POSTGRESQL: Datatypes
	 */
	// String Datatypes
	public static final String postgre_CHAR_n = "CHAR";
	public static final String postgre_CHARACTER_n = "CHARACTER";
	public static final String postgre_VARCHAR_n = "VARCHAR";
	public static final String postgre_CHARACTER_VARYING = "CHARACTER VARYING";
	public static final String postgre_TEXT = "TEXT";

	// Numeric Datatypes
	public static final String postgre_BIT = "BIT";
	public static final String postgre_BYTEA = "BYTEA";
	public static final String postgre_VARBIT = "VARBIT";
	public static final String postgre_BIT_VARYING = "BIT VARYING";
	public static final String postgre_SMALLINT = "SMALLINT";
	public static final String postgre_FLOAT_n = "FLOAT";
	public static final String postgre_INT = "INT";
	public static final String postgre_INTEGER = "INTEGER";
	public static final String postgre_BIGINT = "BIGINT";
	public static final String postgre_SMALLSERIAL = "SMALLSERIAL";
	public static final String postgre_SERIAL = "SERIAL";
	public static final String postgre_BIGSERIAL = "BIGSERIAL";
	public static final String postgre_NUMERIC_p_s = "NUMERIC";
	public static final String postgre_DOUBLE_PRECISION = "DOUBLE PRECISION";
	public static final String postgre_REAL = "REAL";
	public static final String postgre_MONEY = "MONEY";
	public static final String postgre_BOOL = "BOOL";
	public static final String postgre_BOOLEAN = "BOOLEAN";
	public static final String postgre_DECIMAL_p_s = "DECIMAL";
	public static final String postgre_DEC_p_s = "DEC";

	// Date/Time Datatypes
	public static final String postgre_DATE = "DATE";
	public static final String postgre_TIMESTAMP_p = "TIMESTAMP";
	public static final String postgre_TIMESTAMP_WITHOUT_TIME_ZONE = "TIMESTAMP WITHOUT TIME ZONE";
	public static final String postgre_TIMESTAMP_WITH_TIME_ZONE = "TIMESTAMP WITH TIME ZONE";
	public static final String postgre_TIME_p = "TIME";
	public static final String postgre_TIME_WITHOUT_TIME_ZONE = "TIME WITHOUT TIME ZONE";
	public static final String postgre_TIME_WITH_TIME_ZONE = "TIME WITH TIME ZONE";

	public static final String postgre_XML = "XML";

	public static String convert(String datatype, DatabaseType source, DatabaseType destination) {
		if (source.equals(DatabaseType.MSSQL) && destination.equals(DatabaseType.POSTGRE)) {
			return convertMsSqlToPostgreSql(datatype);
		} else if (source.equals(DatabaseType.POSTGRE) && destination.equals(DatabaseType.MSSQL)) {
			return convertPostgreSqlToMsSql(datatype);
		} else {
			return datatype;
		}
	}

	public static String convertMsSqlToPostgreSql(String mssql) {
		if (mssql.toUpperCase().equals(mssql_BIGINT)) {
			return postgre_BIGINT;
		}
		if (mssql.toUpperCase().equals(mssql_BINARY_n)) {
			return postgre_BYTEA;
		}
		if (mssql.toUpperCase().equals(mssql_BIT)) {
			return postgre_BOOLEAN;
		}
		if (mssql.toUpperCase().equals(mssql_CHAR_n)) {
			return postgre_CHAR_n;
		}
		if (mssql.toUpperCase().equals(mssql_CHARACTER_n)) {
			return postgre_CHARACTER_n;
		}
		if (mssql.toUpperCase().equals(mssql_DATE)) {
			return postgre_DATE;
		}
		if (mssql.toUpperCase().equals(mssql_DATETIME)) {
			return postgre_TIMESTAMP_p;
		}
		if (mssql.toUpperCase().equals(mssql_DATETIME2_p)) {
			return postgre_TIMESTAMP_p;
		}
		if (mssql.toUpperCase().equals(mssql_DATETIMEOFFSET)) {
			return postgre_TIMESTAMP_WITH_TIME_ZONE;
		}
		if (mssql.toUpperCase().equals(mssql_DECIMAL_p_s)) {
			return postgre_DECIMAL_p_s;
		}
		if (mssql.toUpperCase().equals(mssql_DEC_p_s)) {
			return postgre_DEC_p_s;
		}
		if (mssql.toUpperCase().equals(mssql_DOUBLE_PRECISION)) {
			return postgre_DOUBLE_PRECISION;
		}
		if (mssql.toUpperCase().equals(mssql_FLOAT_p)) {
			return postgre_DOUBLE_PRECISION;
		}
		if (mssql.toUpperCase().equals(mssql_IMAGE)) {
			return postgre_BYTEA;
		}
		if (mssql.toUpperCase().equals(mssql_INT)) {
			return postgre_INT;
		}
		if (mssql.toUpperCase().equals(mssql_INTEGER)) {
			return postgre_INTEGER;
		}
		if (mssql.toUpperCase().equals(mssql_MONEY)) {
			return postgre_MONEY;
		}
		if (mssql.toUpperCase().equals(mssql_NCHAR_n)) {
			return postgre_CHAR_n;
		}
		if (mssql.toUpperCase().equals(mssql_NTEXT)) {
			return postgre_TEXT;
		}
		if (mssql.toUpperCase().equals(mssql_NUMERIC_p_s)) {
			return postgre_NUMERIC_p_s;
		}
		if (mssql.toUpperCase().equals(mssql_NVARCHAR_n)) {
			return postgre_VARCHAR_n;
		}
		if (mssql.toUpperCase().equals(mssql_NVARCHAR_max)) {
			return postgre_TEXT;
		}
		if (mssql.toUpperCase().equals(mssql_REAL)) {
			return postgre_REAL;
		}
		if (mssql.toUpperCase().equals(mssql_ROWVERSION)) {
			return postgre_BYTEA;
		}
		if (mssql.toUpperCase().equals(mssql_SMALLDATETIME)) {
			return postgre_TIMESTAMP_p;
		}
		if (mssql.toUpperCase().equals(mssql_SMALLINT)) {
			return postgre_SMALLINT;
		}
		if (mssql.toUpperCase().equals(mssql_SMALLMONEY)) {
			return postgre_MONEY;
		}
		if (mssql.toUpperCase().equals(mssql_TEXT)) {
			return postgre_TEXT;
		}
		if (mssql.toUpperCase().equals(mssql_TIME_p)) {
			return postgre_TIME_p;
		}
		if (mssql.toUpperCase().equals(mssql_TIMESTAMP)) {
			return postgre_BYTEA;
		}
		if (mssql.toUpperCase().equals(mssql_TINYINT)) {
			return postgre_SMALLINT;
		}
		if (mssql.toUpperCase().equals(mssql_UNIQUEIDENTIFIER)) {
			return postgre_CHAR_n;
		}
		if (mssql.toUpperCase().equals(mssql_VARBINARY_n)) {
			return postgre_BYTEA;
		}
		if (mssql.toUpperCase().equals(mssql_VARBINARY_max)) {
			return postgre_BYTEA;
		}
		if (mssql.toUpperCase().equals(mssql_VARCHAR_n)) {
			return postgre_VARCHAR_n;
		}
		if (mssql.toUpperCase().equals(mssql_VARCHAR_max)) {
			return postgre_TEXT;
		}
		if (mssql.toUpperCase().equals(mssql_XML)) {
			return postgre_XML;
		}

		return "";
	}

	public static String convertPostgreSqlToMsSql(String postgre) {
		if (postgre.toUpperCase().equals(postgre_BOOL)) {
			return mssql_BIT;
		}
		if (postgre.toUpperCase().equals(postgre_BIT)) {
			return mssql_BIT;
		}
		if (postgre.toUpperCase().equals(postgre_INT)) {
			return mssql_BIGINT;
		}
		if (postgre.toUpperCase().equals(postgre_BIGSERIAL)) {
			return mssql_BIGINT;
		}
		if (postgre.toUpperCase().equals(postgre_BYTEA)) {
			return mssql_BINARY_n;
		}
		if (postgre.toUpperCase().equals(postgre_CHAR_n)) {
			return mssql_CHAR_n;
		}
		if (postgre.toUpperCase().equals(postgre_NUMERIC_p_s)) {
			return mssql_NUMERIC_p_s;
		}
		if (postgre.toUpperCase().equals(postgre_SERIAL)) {
			return mssql_INTEGER;
		}
		if (postgre.toUpperCase().equals(postgre_SMALLSERIAL)) {
			return mssql_SMALLINT;
		}
		if (postgre.toUpperCase().equals(postgre_FLOAT_n)) {
			return mssql_DOUBLE_PRECISION;
		}
		if (postgre.toUpperCase().equals(postgre_MONEY)) {
			return mssql_DOUBLE_PRECISION;
		}
		if (postgre.toUpperCase().equals(postgre_TEXT)) {
			return mssql_VARCHAR_max;
		}
		if (postgre.toUpperCase().equals(postgre_DATE)) {
			return mssql_DATE;
		}
		if (postgre.toUpperCase().equals(postgre_TIME_p)) {
			return mssql_TIME_p;
		}
		if (postgre.toUpperCase().equals(postgre_TIMESTAMP_p)) {
			return mssql_TIMESTAMP;
		}
		if (postgre.toUpperCase().equals(postgre_XML)) {
			return mssql_XML;
		}

		return "";
	}
}
