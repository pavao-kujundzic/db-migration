/**
 *
 */
package hr.sedamit.bss.databasemigrations.util.mssql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import hr.sedamit.bss.databasemigrations.util.common.SqlDataTypes;
import hr.sedamit.bss.databasemigrations.util.common.SqlTable;
import hr.sedamit.bss.databasemigrations.util.common.SqlTableColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by J0ka on 18/02/22
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class MsSqlTable extends SqlTable {
	private final static Logger LOGGER = LoggerFactory.getLogger(MsSqlTable.class);

	public Integer getNumberOfColumns() {
		return this.columns.size();
	}

	@Override
	public String generateTableInsertQuery(Object[] insertData) {

		String values = "";
		for (Object str : insertData) {
			if (str == null) {
				values = values.concat("null").concat(", ");
			} else if (str instanceof Number) {
				values = values.concat(str.toString()).concat(", ");
			} else {
				values = values.concat("'" + str.toString() + "'").concat(", ");
			}
		}

		values = values.substring(0, values.length() - 2);

		String query = "insert into " + this.schemaName + "." + this.tableName + " values (";

		query = query.concat(values);
		query = query.concat(") ");

		return query;
	}

	public static String generateSelectQueries(String schema, String tableName,
			TableBatchConfiguration tableBatchConfiguration) {
		Integer batchSize = tableBatchConfiguration.getBatchSize();
		String numberOfRows = (batchSize.equals(null) || batchSize <= 0) ? " " : "TOP " + batchSize;
		String value = tableBatchConfiguration.getKeyColumnValue();
		StringBuilder query = new StringBuilder();
		query.append("SELECT ")
				.append(numberOfRows)
				.append(" * ")
				.append("FROM ")
				.append(schema + "." + tableName);
		if (value != null && value != "") {
			query.append(" WHERE ")
					.append(tableBatchConfiguration.getKeyColumnName())
					.append(" > ");
			if (value.matches("[0-9]+\\.[0-9]+") || value.matches("[0-9]+")) {
				query.append(value);
			} else {
				query.append("'" + value + "'");
			}
		}
		query.append(";");
		LOGGER.info(query.toString());
		return query.toString();
	}

	@Override
	public List<String> generateListOfTableInsertQuery(List<Object[]> inserDatas) {
		List<String> inserts = new ArrayList<>();
		for (Object[] data : inserDatas) {
			inserts.add(generateTableInsertQuery(data));
		}
		return inserts;
	}

	private static final String primary_key = " PRIMARY KEY";
	private static final String default_n = "(255)";
	private static final String max_n = "(max)";
	private static final String default_p = "(10)";
	private static final String default_s = "(2)";
	private static final String default_p_s = "(10, 2)";
	private static final String nullable = " NULL";
	private static final String not_nullable = " NOT NULL";
	private static final String ID = "ID";

	/**
	 * @param column
	 * @return
	 */
	private String generateColumnCreateString(SqlTableColumn column) {
		String result = column.getName();

		String dataType = SqlDataTypes.convert(column.getDataType(), this.sourceDatabaseType,
				this.destinationDatabaseType);

		if (dataType == null || dataType.equals("")) {
			result = result.concat(SqlDataTypes.mssql_VARCHAR_n)
					.concat(max_n)
					.concat(column.getIsNullable() ? nullable : not_nullable);

			return result;
		}

		result = result.concat(" ").concat(dataType);

		if (column.getCharacterMaximumLength() == null && column.getNumericPrecision() != null
				&& column.getNumericScale() != null && column.getNumericScale() > 0) {
			result = result.concat("(" + column.getNumericPrecision() + ", " + column.getNumericScale() + ")");
		}

		if (column.getCharacterMaximumLength() != null
				&& (column.getNumericPrecision() == null && column.getNumericScale() == null)) {
			result = result.concat("(" + column.getCharacterMaximumLength() + ")");
		}

		if (column.getOrdinalPosition().equals(1) || column.getName().toUpperCase().equals(ID)) {
			result = result.concat(primary_key);
		}

		result = result.concat(column.getIsNullable() ? nullable : not_nullable);

		return result;
	}

	@Override
	public String generateCreateTableQuery() {
		if (this.columns.size() < 1) {
			return "";
		}

		String createSQL = "CREATE TABLE";
		createSQL = createSQL.concat(" IF NOT EXISTS ");
		createSQL = createSQL.concat(
				this.schemaName
						+ "."
						+ this.tableName
						+ " (");
		for (SqlTableColumn column : this.columns) {
			createSQL = createSQL.concat(generateColumnCreateString(column));
			createSQL = createSQL.concat(", ");
		}

		createSQL = createSQL.substring(0, createSQL.length() - 2);
		createSQL = createSQL.concat(")");

		return createSQL;
	}

	@Override
	public String generateCreateSchemaQuery(String schemaName) {
		String query = "CREATE SCHEMA ";
		query = query.concat("IF NOT EXISTS ");
		query = query.concat(schemaName);
		return query;
	}

	@Override
	public List<String> generateInsertDataMap(List<Map<String, Object>> value) {
		List<String> listOfQuery = new ArrayList<>();

		for (Map<String, Object> map : value) {
			String fieldSqlString = "(";
			String dataSqlString = "(";
			for (Entry<String, Object> entry : map.entrySet()) {
				fieldSqlString = fieldSqlString.concat(entry.getKey()).concat(", ");
				Object str = entry.getValue();
				if (str == null) {
					dataSqlString = dataSqlString.concat("null").concat(", ");
				} else if (str instanceof Number) {
					dataSqlString = dataSqlString.concat(str.toString()).concat(", ");
				} else {
					dataSqlString = dataSqlString.concat("'" + str.toString() + "'").concat(", ");
				}
			}
			fieldSqlString = fieldSqlString.substring(0, fieldSqlString.length() - 2);
			dataSqlString = dataSqlString.substring(0, dataSqlString.length() - 2);
			fieldSqlString = fieldSqlString.concat(") ");
			dataSqlString = dataSqlString.concat("); ");

			String query = "insert into " + this.schemaName + "." + this.tableName + fieldSqlString + "values "
					+ dataSqlString;

			listOfQuery.add(query);
		}

		return listOfQuery;
	}

	@Override
	public String generateSelectQuery(String schema, String table, TableBatchConfiguration tableBatchConfiguration) {
		return null;
	}

	@Override
	public String generateLastRowQuery(String schema, String table, String idColumnName) {
		return "SELECT TOP 1 * FROM " + schema + "." + table + " ORDER BY "
				+ idColumnName + " DESC";
	}

}
