/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.postgresql;

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
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by J0ka on 18/02/22
 *
 */
@Data
@NoArgsConstructor
public class PostgreSqlTable extends SqlTable {
	private final static Logger LOGGER = LoggerFactory.getLogger(PostgreSqlTable.class);

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

		LOGGER.debug("Generated table insert query:: " + query);

		return query;
	}

	@Override
	public String generateSelectQuery(String schema, String table, TableBatchConfiguration tableBatchConfiguration) {
		return null;
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
			result = result.concat(SqlDataTypes.postgre_VARCHAR_n)
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

		String query = "CREATE TABLE";
		query = query.concat(" IF NOT EXISTS ");
		query = query.concat(
				this.schemaName
						+ "."
						+ this.tableName
						+ " (");
		for (SqlTableColumn column : this.columns) {
			query = query.concat(generateColumnCreateString(column));
			query = query.concat(", ");
		}

		query = query.substring(0, query.length() - 2);
		query = query.concat(")");

		LOGGER.debug("Generated table create query:: " + query);
		return query;
	}

	@Override
	public String generateCreateSchemaQuery(String schemaName) {
		String query = "CREATE SCHEMA ";
		query = query.concat("IF NOT EXISTS ");
		query = query.concat(schemaName);
		LOGGER.debug("Generated table create query:: " + query);
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
	public String generateLastRowQuery(String schema, String table, String idColumnName) {
		return "SELECT * FROM " + schema + "." + table + " ORDER BY "
				+ idColumnName + " DESC LIMIT 1";
	}

}
