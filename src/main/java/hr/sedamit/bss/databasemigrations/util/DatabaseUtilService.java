/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import hr.sedamit.bss.databasemigrations.util.common.DatabaseType;
import hr.sedamit.bss.databasemigrations.util.common.SqlTable;
import hr.sedamit.bss.databasemigrations.util.common.SqlTableBuilder;
import hr.sedamit.bss.databasemigrations.util.common.SqlTableMetadata;
import hr.sedamit.bss.databasemigrations.util.mssql.MsSqlTable;
import hr.sedamit.bss.databasemigrations.util.postgresql.PostgreSqlTable;
import lombok.Data;

/**
 * Created by J0ka on 17/02/22
 *
 */
@Data
public class DatabaseUtilService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtilService.class);

	private EntityManager sourcEntityManager;
	private JdbcTemplate sourceJdbcTemplate;
	private JdbcTemplate destinationJdbcTemplate;

	private String sourceSchema;
	private String sourceTable;
	private DatabaseType sourceDatabaseType;
	private DatabaseType destinationDatabaseType;
	private SqlTable destinationTable;

	/**
	 * @param destinationJdbcTemplate
	 * @param sourceJdbcTemplate
	 * @param sourcEntityManager
	 * @param sourceSchema
	 * @param sourceTable
	 * @param sourceDatabase
	 * @param destinationDatabase
	 * @throws Exception
	 */
	public DatabaseUtilService(EntityManager sourcEntityManager, JdbcTemplate sourceJdbcTemplate,
			JdbcTemplate destinationJdbcTemplate, String sourceSchema, String sourceTable, DatabaseType sourceDatabase,
			DatabaseType destinationDatabase) throws Exception {
		super();
		this.sourcEntityManager = sourcEntityManager;
		this.sourceJdbcTemplate = sourceJdbcTemplate;
		this.destinationJdbcTemplate = destinationJdbcTemplate;
		this.sourceSchema = sourceSchema;
		this.sourceTable = sourceTable;
		this.sourceDatabaseType = sourceDatabase;
		this.destinationDatabaseType = destinationDatabase;
		this.destinationTable = init();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private SqlTable init() {
		if (this.destinationDatabaseType.equals(DatabaseType.POSTGRE)) {
			return new SqlTableBuilder()
					.destinationDatabaseType(this.destinationDatabaseType)
					.sourceDatabaseType(this.sourceDatabaseType)
					.tableName(this.sourceTable)
					.schemaName(this.sourceSchema)
					.column(getTableDetails(this.sourceSchema, this.sourceTable, this.sourceDatabaseType))
					.build(PostgreSqlTable.class);
		} else if (this.destinationDatabaseType.equals(DatabaseType.MSSQL)) {
			return new SqlTableBuilder()
					.destinationDatabaseType(this.destinationDatabaseType)
					.sourceDatabaseType(this.sourceDatabaseType)
					.tableName(this.sourceTable)
					.schemaName(this.sourceSchema)
					.column(getTableDetails(this.sourceSchema, this.sourceTable, this.sourceDatabaseType))
					.build(MsSqlTable.class);
		} else {
			return null;
		}
	}

	public boolean createTable(boolean failIfExist) {
		LOGGER.info("Create table start...");
		LOGGER.info("From " + this.sourceDatabaseType + " to " + this.destinationDatabaseType + ":: schema= "
				+ this.sourceSchema + ", table=" + this.sourceTable);

		destinationJdbcTemplate.execute(this.destinationTable.generateCreateSchemaQuery(this.sourceSchema));
		destinationJdbcTemplate.execute(this.destinationTable.generateCreateTableQuery());

		LOGGER.info("Create table end...");

		return true;
	}

	public boolean insertData(List<Object[]> datas) {
		List<String> insertStrings = this.destinationTable.generateListOfTableInsertQuery(datas);
		for (String insertString : insertStrings) {
			try {
				destinationJdbcTemplate.execute(insertString);
			} catch (DataAccessException e) {
				return false;
			}
		}

		return true;
	}

	public boolean insertData(Object[] data) {

		try {
			String insertString = this.destinationTable.generateTableInsertQuery(data);
			destinationJdbcTemplate.execute(insertString);
		} catch (DataAccessException e) {
			return false;
		}

		return true;
	}

	/**
	 * Dohvati informacije o stupcima
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> getTableDetails(String shemaName, String tableName,
			DatabaseType sourceDatabaseType) {
		Query datatypes = sourcEntityManager
				.createNativeQuery(SqlTableMetadata.getTableInformationQuery(shemaName, tableName, sourceDatabaseType));

		NativeQueryImpl nativedatatypeQuery = (NativeQueryImpl) datatypes;
		nativedatatypeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		List<Map<String, Object>> result = nativedatatypeQuery.getResultList();

		if (result == null || result.size() < 1) {
			LOGGER.info("Table = " + shemaName + "." + tableName + " not exists or error occured. Check sql query");
			return null;
		}

		return result;
	}
}
