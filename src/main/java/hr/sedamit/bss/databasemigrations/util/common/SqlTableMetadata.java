/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.common;

import hr.sedamit.bss.databasemigrations.util.mssql.MsSqlTableMetadata;
import hr.sedamit.bss.databasemigrations.util.postgresql.PostgreSqlTableMetadata;

/**
 * Created by J0ka on 22/02/22
 *
 */
public class SqlTableMetadata {
	public static String getTableInformationQuery(String schema, String table, DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.metadataSQLQuery(schema, table);
		case POSTGRE:
			return PostgreSqlTableMetadata.metadataSQLQuery(schema, table);
		default:
			return "";
		}
	}

	public static String getTableName(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.TABLE_NAME;
		case POSTGRE:
			return PostgreSqlTableMetadata.TABLE_NAME;
		default:
			return "";
		}
	}

	public static String getTableSchema(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.TABLE_SCHEMA;
		case POSTGRE:
			return PostgreSqlTableMetadata.TABLE_SCHEMA;
		default:
			return "";
		}
	}

	public static String getOrdinalPosition(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.ORDINAL_POSITION;
		case POSTGRE:
			return PostgreSqlTableMetadata.ORDINAL_POSITION;
		default:
			return "";
		}
	}

	public static String getNumericScale(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.NUMERIC_SCALE;
		case POSTGRE:
			return PostgreSqlTableMetadata.NUMERIC_SCALE;
		default:
			return "";
		}
	}

	public static String getNumericPrecision(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.NUMERIC_PRECISION;
		case POSTGRE:
			return PostgreSqlTableMetadata.NUMERIC_PRECISION;
		default:
			return "";
		}
	}

	public static String getIsNullable(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.IS_NULLABLE;
		case POSTGRE:
			return PostgreSqlTableMetadata.IS_NULLABLE;
		default:
			return "";
		}
	}

	public static String getCharacterMaximumLength(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.CHARACTER_MAXIMUM_LENGTH;
		case POSTGRE:
			return PostgreSqlTableMetadata.CHARACTER_MAXIMUM_LENGTH;
		default:
			return "";
		}
	}

	public static String getColumnName(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.COLUMN_NAME;
		case POSTGRE:
			return PostgreSqlTableMetadata.COLUMN_NAME;
		default:
			return "";
		}
	}

	public static String getDataType(DatabaseType databaseType) {
		switch (databaseType) {
		case MSSQL:
			return MsSqlTableMetadata.DATA_TYPE;
		case POSTGRE:
			return PostgreSqlTableMetadata.DATA_TYPE;
		default:
			return "";
		}
	}
}
