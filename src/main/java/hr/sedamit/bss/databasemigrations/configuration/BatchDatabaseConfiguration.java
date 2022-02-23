package hr.sedamit.bss.databasemigrations.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"hr.sedamit.bss.databasemigrations.batch.repository"},
        entityManagerFactoryRef = "batchEntityManagerFactory")
public class BatchDatabaseConfiguration {
    @Autowired
    Environment env;

    @Bean
    @Autowired
    @Primary
    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(@Qualifier("batchDataSource") DataSource postgreDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(postgreDataSource);
        em.setPackagesToScan("hr.sedamit.bss.databasemigrations.batch");


        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.dialect", env.getProperty("batch.datasource.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @Primary
    @Qualifier("batchDataSource")
    public DataSource batchDataSource() throws SQLException {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("batch.datasource.driver"));
        driver.setUrl(env.getProperty("batch.datasource.jdbcUrl"));
        driver.setUsername(env.getProperty("batch.datasource.username"));
        driver.setPassword(env.getProperty("batch.datasource.password"));
        return driver;
    }

}
