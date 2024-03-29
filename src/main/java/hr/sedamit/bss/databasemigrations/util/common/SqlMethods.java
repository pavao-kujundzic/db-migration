/**
 *
 */
package hr.sedamit.bss.databasemigrations.util.common;

import java.util.List;
import java.util.Map;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;

/**
 * Created by J0ka on 23/02/22
 *
 */
public interface SqlMethods {

	public String generateCreateSchemaQuery(String schemaName);

	public String generateCreateTableQuery();

	public List<String> generateListOfTableInsertQuery(List<Object[]> inserDatas);

	public String generateTableInsertQuery(Object[] inserDatas);

	public List<String> generateInsertDataMap(List<Map<String, Object>> value);

	public String generateSelectQuery(String schema, String table, TableBatchConfiguration tableBatchConfiguration);

	public String generateLastRowQuery(String schema, String table, String idColumnName);
}
