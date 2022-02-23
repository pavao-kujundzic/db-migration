/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.common;

import java.util.List;

/**
 * Created by J0ka on 23/02/22
 *
 */
public interface SqlMethods {

	public String generateCreateSchemaQuery(String schemaName);

	public String generateCreateTableQuery();

	public List<String> generateListOfTableInsertQuery(List<Object[]> inserDatas);

	public String generateTableInsertQuery(Object[] inserDatas);
}
