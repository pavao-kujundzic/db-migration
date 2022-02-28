/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.common;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by J0ka on 22/02/22
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class SqlTable implements SqlMethods {
	protected DatabaseType sourceDatabaseType;
	protected DatabaseType destinationDatabaseType;
	protected String tableName;
	protected String schemaName;
	protected List<SqlTableColumn> columns = new ArrayList<>();

}
