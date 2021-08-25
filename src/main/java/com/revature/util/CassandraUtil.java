package com.revature.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.QueryOptions;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = {"com.revature.data"})
public class CassandraUtil {
	
	@Bean
	public CqlSessionFactoryBean session() {
		
		CqlSessionFactoryBean factory = new CqlSessionFactoryBean();
		DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
		factory.setSessionBuilderConfigurer(builder -> builder.withConfigLoader(loader).withKeyspace("team3_project2"));
		factory.setKeyspaceName("team3_project2");
		
		return factory;
	}
	
	@Bean
	public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {
		SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
		((MappingCassandraConverter) converter).setUserTypeResolver(new SimpleUserTypeResolver(session));
		sessionFactory.setSession(session);
		sessionFactory.setConverter(converter);
		// Please do not drop all my tables
		sessionFactory.setSchemaAction(SchemaAction.NONE);
		
		return sessionFactory;
	}
	
	@Bean
	public CassandraConverter converter(CassandraMappingContext mappingContext) {
		return new MappingCassandraConverter(mappingContext);
	}
	
	@Bean
	public CassandraMappingContext mappingContext() {
		CassandraMappingContext mappingContext = new CassandraMappingContext();
		return mappingContext;
	}
	
	@Bean
	public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
		return new CassandraTemplate(sessionFactory, converter);
	}
	protected QueryOptions queryOptions() {
		 return QueryOptions.builder().consistencyLevel(ConsistencyLevel.LOCAL_QUORUM).build();
		}
}
