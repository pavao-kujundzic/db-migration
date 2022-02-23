package hr.sedamit.bss.databasemigrations.configuration;

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = {
		"hr.sedamit.bss.databasemigrations.destination.repository" }, entityManagerFactoryRef = "destinationEntityManagerFactory")
public class DestinationDatabaseConfiguration {
	@Autowired
	Environment env;

	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean destinationEntityManagerFactory(
			@Qualifier("destinationDataSource") DataSource destinationDataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setJtaDataSource(destinationDataSource);
		em.setPackagesToScan("hr.sedamit.bss.databasemigrations.destination");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.dialect", env.getProperty("batch.destination.datasource.hibernate.dialect"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	@Qualifier("destinationDataSource")
	public DataSource destinationDataSource() throws SQLException {
		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setDriverClassName(env.getProperty("batch.destination.datasource.driver"));
		driver.setUrl(env.getProperty("batch.destination.datasource.jdbcUrl"));
		driver.setUsername(env.getProperty("batch.destination.datasource.username"));
		driver.setPassword(env.getProperty("batch.destination.datasource.password"));
		return driver;
	}

	@Bean
	@Qualifier("destinationJdbcTemplate")
	public JdbcTemplate destinationJdbcTemplate(@Qualifier("destinationDataSource") DataSource destinationDataSource) {
		return new JdbcTemplate(destinationDataSource);
	}

}
