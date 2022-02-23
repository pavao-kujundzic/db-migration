/**
 * 
 */
package hr.sedamit.bss.databasemigrations.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import hr.sedamit.bss.databasemigrations.util.DatabaseUtilService;
import hr.sedamit.bss.databasemigrations.util.common.DatabaseType;

/**
 * Created by J0ka on 22/02/22
 *
 */
@Service
public class DatabaseService {

	@PersistenceContext
	private EntityManager sourceEntityManager;

	@Autowired
	@Qualifier("sourceJdbcTemplate")
	JdbcTemplate sourceJdbcTemplate;

	@Autowired
	@Qualifier("destinationJdbcTemplate")
	JdbcTemplate destinationJdbcTemplate;

	public void createTable() throws Exception {
		DatabaseUtilService service = new DatabaseUtilService(sourceEntityManager, sourceJdbcTemplate,
				destinationJdbcTemplate, "billing", "payment_order", DatabaseType.MSSQL,
				DatabaseType.POSTGRE);

		service.createTable(false);

	}

}
