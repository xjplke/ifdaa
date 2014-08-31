package cn.adfi.radius.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {
	
	
	@Bean
	@ConfigurationProperties(prefix="datasource.druid")
    public DataSource createDruidDatasource(){
    	return new com.alibaba.druid.pool.DruidDataSource();
    	
    }
}
