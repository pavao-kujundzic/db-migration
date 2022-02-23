/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.mssql;

/**
 * Created by J0ka on 18/02/22
 *
 */
public class MsSqlTableMetadata {
	public static final String TABLE_NAME = "TABLE_NAME";
	public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
	public static final String ORDINAL_POSITION = "ORDINAL_POSITION";
	public static final String NUMERIC_SCALE = "NUMERIC_SCALE";
	public static final String NUMERIC_PRECISION = "NUMERIC_PRECISION";
	public static final String IS_NULLABLE = "IS_NULLABLE";
	public static final String CHARACTER_MAXIMUM_LENGTH = "CHARACTER_MAXIMUM_LENGTH";
	public static final String COLUMN_NAME = "COLUMN_NAME";
	public static final String DATA_TYPE = "DATA_TYPE";

	public static String metadataSQLQuery(String schemaName, String tableName) {
		return "SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, IS_NULLABLE, ORDINAL_POSITION FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA  = '"
				+ schemaName + "' AND TABLE_NAME = '" + tableName + "'";
	}
}
