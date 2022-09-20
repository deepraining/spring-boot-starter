package dr.sbs.mdb.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(
    basePackages = {"dr.sbs.mbg.mapper", "dr.sbs.mdb.dao.user"},
    sqlSessionTemplateRef = "userSqlSessionTemplate")
public class MyBatisConfigUser {
  @Bean(name = "userDataSource")
  @ConfigurationProperties(prefix = "datasource.user")
  public DataSource userDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "userSqlSessionFactory")
  public SqlSessionFactory userSqlSessionFactory(@Qualifier("userDataSource") DataSource dataSource)
      throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean.getObject();
  }

  @Bean(name = "userSqlSessionTemplate")
  public SqlSessionTemplate userSqlSessionTemplate(
      @Qualifier("userSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
