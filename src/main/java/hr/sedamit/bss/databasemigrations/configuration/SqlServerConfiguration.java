package hr.sedamit.bss.databasemigrations.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"hr.sedamit.bss.databasemigrations.sqlserver.repository"},
        entityManagerFactoryRef = "sqlServerEntityManagerFactory")
public class SqlServerConfiguration {

    @Autowired
    Environment env;


    @Bean(name = "sqlServerEntityManagerFactory")
    @Autowired
    public LocalContainerEntityManagerFactoryBean sqlServerEntityManagerFactory(@Qualifier("sqlServerDataSource") DataSource sqlServerDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(sqlServerDataSource);
        em.setPackagesToScan("hr.sedamit.bss.databasemigrations.sqlserver.entity");

        HibernateJpaVendorAdapter vendorAdapter= new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", env.getProperty("spring.datasource.sqlserver.hibernate.show_sql"));
        properties.put("hibernate.dialect", env.getProperty("spring.datasource.sqlserver.hibernate.dialect"));
        em.setJpaPropertyMap(properties);


        return em;
    }

    @Bean(name = "sqlServerDataSource")
    public DataSource sqlServerDataSource() throws SQLException {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("spring.datasource.sqlserver.driver"));
        driver.setUrl(env.getProperty("spring.datasource.sqlserver.jdbcUrl"));
        driver.setUsername(env.getProperty("spring.datasource.sqlserver.username"));
        driver.setPassword(env.getProperty("spring.datasource.sqlserver.password"));
        return driver;
    }

    @Bean(name = "sqlServerJdbcTemplate")
    public JdbcTemplate sqlServerJdbcTemplate(@Qualifier("sqlServerDataSource") DataSource sqlServerDataSource) {
        return new JdbcTemplate(sqlServerDataSource);
    }


}