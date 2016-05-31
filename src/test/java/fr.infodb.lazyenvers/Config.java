package fr.infodb.lazyenvers;

import fr.infodb.lazyenvers.entities.Professor;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class Config {

    @Bean
    public DataSource getDataSource() {
        final String datasourceUrl = "jdbc:h2:mem:test;";
        final String datasourceUsername = "sa";
        final String datasourcePassword = "";

        final DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(datasourceUrl, datasourceUsername, datasourcePassword);

        final PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

        final ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);

        return new PoolingDataSource<>(connectionPool);
    }

    private Properties getHibernateProperties() {
        final Properties hibernateProperties = new Properties();

        //Hibernate settings
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("javax.persistence.validation.mode", "none");

        //Prefix and suffix for envers table
        hibernateProperties.setProperty("org.hibernate.envers.audit_table_prefix", "ENVERS_");
        hibernateProperties.setProperty("org.hibernate.envers.audit_table_suffix", "");

        //Audit History flags
        hibernateProperties.setProperty("org.hibernate.envers.store_data_at_delete", "true");
        hibernateProperties.setProperty("org.hibernate.envers.global_with_modified_flag", "true");

        return hibernateProperties;
    }

    @Bean
    public EntityManagerFactory getEntityManagerFactory(DataSource dataSource) {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(Professor.class.getPackage().getName());
        factory.setDataSource(dataSource);

        factory.setJpaProperties(getHibernateProperties());
        factory.afterPropertiesSet();

        return factory.getObject();
    }
}
