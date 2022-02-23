package hr.sedamit.bss.databasemigrations.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"hr.sedamit.bss.databasemigrations.postgre.repository"},
        entityManagerFactoryRef = "postgreEntityManagerFactory")
public class DestinationDatabaseConfiguration {
    @Autowired
    Environment env;

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean postgreEntityManagerFactory(@Qualifier("postgreSqlDataSource") DataSource postgreDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(postgreDataSource);
        em.setPackagesToScan("hr.sedamit.bss.databasemigrations.postgre");


        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.dialect", env.getProperty("batch.destination.datasource.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @Qualifier("postgreSqlDataSource")
    public DataSource postgreSqlDataSource() throws SQLException {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("batch.destination.datasource.driver"));
        driver.setUrl(env.getProperty("batch.destination.datasource.jdbcUrl"));
        driver.setUsername(env.getProperty("batch.destination.datasource.username"));
        driver.setPassword(env.getProperty("batch.destination.datasource.password"));
        return driver;
    }

    @Bean(name = "postgreSqlJdbcTemplate")
    public JdbcTemplate postgreSqlJdbcTemplate(@Qualifier("postgreSqlDataSource") DataSource postgreSqlDataSource) {
        return new JdbcTemplate(postgreSqlDataSource);
    }


}
