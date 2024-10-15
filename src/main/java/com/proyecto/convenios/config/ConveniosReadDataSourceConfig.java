package com.proyecto.convenios.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyecto.convenios.constants.DataSourceConstants;
import com.proyecto.convenios.dto.AwsSecrets;
import com.zaxxer.hikari.HikariDataSource;

import co.com.ath.commons.dto.enums.CodigosRespuestaEspecificosApi;
import co.com.ath.commons.dto.exceptions.AthException;
import lombok.Generated;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Generated
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ConveniosReadDataSourceConfig.UBICACION_REPOSITORIOS_LECTURA, entityManagerFactoryRef = ConveniosReadDataSourceConfig.REFERENCIA_ENTITY_MANAGER_FABRICA, transactionManagerRef = ConveniosReadDataSourceConfig.REFERENCIA_TRANSACTION_MANAGER)
public class ConveniosReadDataSourceConfig {

	final SecretConfig secretConfig;

	final Environment environment;

	private static final String JDBC_URL_FORMAT = "jdbc:mysql://%s:%s/%s";

	@Value("${aws.secret-name-read}")
	private String secretName;

	@Value("${aws.db-name}")
	private String dbName;

	/**
	 * nombre completo del paquete donde estan los JPARepository a configurar
	 */
	public static final String UBICACION_REPOSITORIOS_LECTURA = "com.proyecto.convenios.repository.read";

	/**
	 * nombre del metodo @Bean de EntityManagerFactory
	 */
	public static final String REFERENCIA_ENTITY_MANAGER_FABRICA = "lecturaEntityManagerFactory";

	/**
	 * nombre del metodo @Bean de transactionManager
	 */
	public static final String REFERENCIA_TRANSACTION_MANAGER = "lecturaTransactionManager";

	/**
	 * Variables para configurar las propiedades internas del datasouurce
	 */
	private static final String PERSISTENCE_UNIT_NAME_LECTURA = "datasource-lectura";

	/**
	 * grupo de propiedades a leer del archivo properties del proyecto
	 */
	public static final String UBICACION_PROPIEDADES_LECTURA = "spring.datasource.lectura";

	ConveniosReadDataSourceConfig(SecretConfig secretConfig, Environment environment) {
		this.secretConfig = secretConfig;
		this.environment = environment;
	}

	/**
	 * Este metodo es el encargado de obtener del archivo de 'application.yaml' o
	 * 'application.properties' el conjunto de propiedades del datasource a
	 * configurar
	 * 
	 * @param prefix atributo donde se encuentran las propiedades de configuracion
	 * 
	 * @return DataSourceProperties propiedades de datasource
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = UBICACION_PROPIEDADES_LECTURA)
	public DataSourceProperties lecturaDataSourceProperties() {
		return new DataSourceProperties();
	}

	/**
	 * Este metodo es el encargado de crear el datasource a partir de las
	 * propiedades obtenidas en el metodo escrituraDataSourceProperties
	 * 
	 * @return DataSource
	 */
	@Bean
	public DataSource lecturaDataSource(SecretsManagerClient client) {
		HikariDataSource datasource = new HikariDataSource();
		AwsSecrets secrets = secretConfig.getSecret(client, secretName);

		datasource.setDataSource(DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver")
				.url(String.format(JDBC_URL_FORMAT, secrets.getHost(), secrets.getPort(), dbName))
				.username(secrets.getUser()).password(secrets.getPassword()).build());

		datasource.setPoolName(PERSISTENCE_UNIT_NAME_LECTURA);
		datasource.setMaximumPoolSize(
				Integer.parseInt(environment.getProperty(DataSourceConstants.PROPIEDAD_MAXIMO_CONEXIONES)));
		datasource.setMinimumIdle(
				Integer.parseInt(environment.getProperty(DataSourceConstants.PROPIEDAD_MAXIMO_CONEXIONES_INICIALES)));
		datasource.setConnectionTimeout(
				Long.parseLong(environment.getProperty(DataSourceConstants.TIME_OUT_CONEXION_BASE_DATOS)));
		datasource.setValidationTimeout(
				Long.parseLong(environment.getProperty(DataSourceConstants.TIME_OUT_LECTURA_BASE_DATOS)));

		return datasource;
	}

	/**
	 * Este metodo es el encargado de crear la plataforma transaccion manager a
	 * partir del metodo de escrituraEntityManagerFactory
	 * 
	 * @return PlatformTransactionManager
	 */
	@Bean
	public PlatformTransactionManager lecturaTransactionManager(SecretsManagerClient client) {
		EntityManagerFactory fabricaManejadora = lecturaEntityManagerFactory(client).getObject();
		if (fabricaManejadora != null)
			return new JpaTransactionManager(fabricaManejadora);
		throw new AthException(CodigosRespuestaEspecificosApi.ERROR_LECTURA_TRANSACCION_MANAGER.getCodigo(),
				CodigosRespuestaEspecificosApi.ERROR_LECTURA_TRANSACCION_MANAGER.getMensaje());
	}

	/**
	 * Este metodo es el encargado de configurar las propiedades de jpa el nombre
	 * del persistence y las entidades a escanear para el datasource
	 * 
	 * @return LocalContainerEntityManagerFactoryBean
	 */
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean lecturaEntityManagerFactory(SecretsManagerClient client) {
		LocalContainerEntityManagerFactoryBean fabricaManejadora = new LocalContainerEntityManagerFactoryBean();
		fabricaManejadora.setDataSource(lecturaDataSource(client));
		fabricaManejadora.setPackagesToScan(DataSourceConstants.UBICACION_ENTIDADES);
		fabricaManejadora.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		fabricaManejadora.setPersistenceUnitName(PERSISTENCE_UNIT_NAME_LECTURA);
		Properties propiedadesJpa = new Properties();
		propiedadesJpa.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
		propiedadesJpa.put("hibernate.format_sql", "true");
		propiedadesJpa.put("hibernate.ddl-auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
		propiedadesJpa.put("hibernate.open-in-view", environment.getProperty("spring.jpa.open-in-view"));
		fabricaManejadora.setJpaProperties(propiedadesJpa);

		return fabricaManejadora;
	}

	/**
	 * Este metodo es el encargado de inicializar el datasource
	 * 
	 * @return DataSourceInitializer
	 */
	@Bean
	public DataSourceInitializer lecturaDataSourceInitializer(SecretsManagerClient client) {
		DataSourceInitializer inicializadorDatasource = new DataSourceInitializer();
		inicializadorDatasource.setDataSource(lecturaDataSource(client));
		inicializadorDatasource.setDatabasePopulator(new ResourceDatabasePopulator());
		inicializadorDatasource.setEnabled(true);
		return inicializadorDatasource;
	}

}
