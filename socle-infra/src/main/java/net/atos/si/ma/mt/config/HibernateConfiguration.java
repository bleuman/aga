package net.atos.si.ma.mt.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

	@Value("${hibernate.dialect}")
	private String dialect;
	@Value("${hibernate.show_sql}")
	private String showSql;
	@Value("${hibernate.format_sql}")
	private String formatSql;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddl;
	@Value("${hibernate.cache.region.factory_class}")
	private String cacheRegionFactory;
	@Value("${hibernate.cache.use_second_level_cache}")
	private String useSecondLevelCache;
	@Value("${hibernate.cache.use_query_cache}")
	private String useQueryCache;
	@Value("${net.sf.ehcache.configurationResourceName}")
	private String ehcacheFile;

	@Autowired
	public DataSource dataSource;
	
	
	@Value("${modelPackage}")
	private String modelPackage;

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager htm = new JpaTransactionManager();
		htm.setEntityManagerFactory(entityManagerFactory());
		return htm;
	}

	@Bean
	public Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", dialect);
		properties.put("hibernate.show_sql", showSql);
		properties.put("hibernate.format_sql", formatSql);
		properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		properties.put("hibernate.cache.region.factory_class",
				cacheRegionFactory);
		properties.put("hibernate.cache.use_second_level_cache",
				useSecondLevelCache);
		properties.put("hibernate.cache.use_query_cache", useQueryCache);
		properties.put("net.sf.ehcache.configurationResourceName", ehcacheFile);
		
		System.out.println("Loding Hibernate Config " + properties.toString());

		return properties;
	}

	@Bean
	HibernateExceptionTranslator hibernateExceptionTranslator() {
		HibernateExceptionTranslator bean = new HibernateExceptionTranslator();
		return bean;
	}

	/*
	 * @Bean DefaultPersistenceUnitManager persistenceUnitManager() {
	 * DefaultPersistenceUnitManager unitManager = new
	 * DefaultPersistenceUnitManager();
	 * 
	 * unitManager
	 * .setPersistenceXmlLocations("classpath:META-INF/persistence.xml");
	 * unitManager.setDefaultDataSource(dataSource); return unitManager; }
	 */
	/*@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitManager(persistenceUnitManager());
		factory.setP
		factory.setPackagesToScan("net.atos.si.ma.mt.astreinte.model");
		factory.setJpaProperties(getHibernateProperties());
		return factory;
	}*/
	@Bean
	public EntityManagerFactory entityManagerFactory(  ){
	    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	    em.setDataSource( dataSource );
	    em.setPackagesToScan( modelPackage );
	    em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
	    em.setJpaProperties( getHibernateProperties() );
	    em.setPersistenceUnitName( "ApplicationEntityManager" );
	    //em.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
	    em.afterPropertiesSet();

	    return em.getObject();
	}

	/*
	 * 
	 * 
	 * 
	 * <bean id="transactionManager"
	 * class="org.springframework.orm.jpa.JpaTransactionManager"> <property
	 * name="entityManagerFactory" ref="entityManagerFactory" /> </bean>
	 */

}
