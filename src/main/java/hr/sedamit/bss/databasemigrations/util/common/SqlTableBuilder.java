/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.sedamit.bss.databasemigrations.util.mssql.MsSqlTable;
import hr.sedamit.bss.databasemigrations.util.postgresql.PostgreSqlTable;
import hr.sedamit.bss.databasemigrations.util.postgresql.PostgreSqlTableMetadata;

/**
 * Created by J0ka on 23/02/22
 *
 */
public class SqlTableBuilder {
	private DatabaseType sourceDatabaseType;
	private DatabaseType destinationDatabaseType;
	private String tableName;
	private String schemaName;
	private List<SqlTableColumn> columns = new ArrayList<>();

	public SqlTableBuilder() {
	}

	public SqlTableBuilder sourceDatabaseType(DatabaseType sourceDatabaseType) {
		this.sourceDatabaseType = sourceDatabaseType;
		return this;
	}

	public SqlTableBuilder destinationDatabaseType(DatabaseType destinationDatabaseType) {
		this.destinationDatabaseType = destinationDatabaseType;
		return this;
	}

	public SqlTableBuilder tableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public SqlTableBuilder schemaName(String schemaName) {
		this.schemaName = schemaName;
		return this;
	}

	public SqlTableBuilder column(SqlTableColumn column) {
		columns.add(column);
		return this;
	}

	public SqlTableBuilder column(Map<String, Object> column) {
		SqlTableColumn sqlTableColumn = new SqlTableColumn();
		sqlTableColumn.setOrdinalPosition(
				column.get(SqlTableMetadata.getOrdinalPosition(sourceDatabaseType)) != null
						? (Integer) column.get(SqlTableMetadata.getOrdinalPosition(sourceDatabaseType))
						: null);
		sqlTableColumn.setNumericScale(
				column.get(SqlTableMetadata.getNumericScale(sourceDatabaseType)) != null
						? (Integer) column.get(SqlTableMetadata.getNumericScale(sourceDatabaseType))
						: null);
		sqlTableColumn.setNumericPrecision(
				column.get(SqlTableMetadata.getNumericPrecision(sourceDatabaseType)) != null
						? column.get(SqlTableMetadata.getNumericPrecision(sourceDatabaseType))
						: null);

		if (column.get(SqlTableMetadata.getIsNullable(sourceDatabaseType)) != null) {
			if (((String) column.get(SqlTableMetadata.getIsNullable(sourceDatabaseType))).equals("YES")) {
				sqlTableColumn.setIsNullable(true);
			} else {
				sqlTableColumn.setIsNullable(false);
			}
		} else {
			sqlTableColumn.setIsNullable(true);
		}

		sqlTableColumn.setCharacterMaximumLength(
				column.get(SqlTableMetadata.getCharacterMaximumLength(sourceDatabaseType)) != null
						? (Integer) column.get(SqlTableMetadata.getCharacterMaximumLength(sourceDatabaseType))
						: null);
		sqlTableColumn.setName(column.get(SqlTableMetadata.getColumnName(sourceDatabaseType)) != null
				? (String) column.get(SqlTableMetadata.getColumnName(sourceDatabaseType))
				: null);

		if (column.get(SqlTableMetadata.getDataType(sourceDatabaseType)) != null) {
			sqlTableColumn.setDataType(SqlDataTypes
					.convert((String) column.get(SqlTableMetadata.getDataType(sourceDatabaseType)),
							this.sourceDatabaseType,
							this.destinationDatabaseType));
		}

		sqlTableColumn.setDataType(column.get(PostgreSqlTableMetadata.DATA_TYPE) != null
				? (String) column.get(PostgreSqlTableMetadata.DATA_TYPE)
				: null);

		this.columns.add(sqlTableColumn);

		return this;
	}

	public SqlTableBuilder column(List<Map<String, Object>> columns) {
		if (columns != null && columns.size() > 0) {
			for (Map<String, Object> map : columns) {
				column(map);
			}
		}

		return this;
	}

	public SqlTable build(Class<?> clazz) {
		if (clazz.equals(PostgreSqlTable.class)) {
			PostgreSqlTable table = new PostgreSqlTable();
			table.setColumns(this.columns);
			table.setTableName(this.tableName);
			table.setSchemaName(this.schemaName);
			table.setSourceDatabaseType(this.sourceDatabaseType);
			table.setDestinationDatabaseType(this.destinationDatabaseType);

			return table;
		} else if (clazz.equals(MsSqlTable.class)) {
			MsSqlTable table = new MsSqlTable();
			table.setColumns(this.columns);
			table.setTableName(this.tableName);
			table.setSchemaName(this.schemaName);
			table.setSourceDatabaseType(this.sourceDatabaseType);
			table.setDestinationDatabaseType(this.destinationDatabaseType);

			return table;
		}
		return null;
	}

}
