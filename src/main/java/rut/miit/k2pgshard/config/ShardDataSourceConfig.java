package rut.miit.k2pgshard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class ShardDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.shard1")
    public DataSource shard1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.shard2")
    public DataSource shard2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.shard3")
    public DataSource shard3DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public List<DataSource> dataSources() {
        return List.of(shard1DataSource(), shard2DataSource(), shard3DataSource());
    }

}
