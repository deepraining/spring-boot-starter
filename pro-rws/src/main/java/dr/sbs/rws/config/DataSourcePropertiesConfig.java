package dr.sbs.rws.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourcePropertiesConfig {
  @Primary
  @Bean
  @ConfigurationProperties("datasource.write")
  public DataSourceProperties writeDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("datasource.read")
  public DataSourceProperties readDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("datasource.read2")
  public DataSourceProperties readDataSourceProperties2() {
    return new DataSourceProperties();
  }
}
