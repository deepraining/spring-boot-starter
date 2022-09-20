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
    basePackages = {"dr.sbs.mdbmbg.mapper", "dr.sbs.mdb.dao.article"},
    sqlSessionTemplateRef = "articleSqlSessionTemplate")
public class MyBatisConfigArticle {
  @Bean(name = "articleDataSource")
  @ConfigurationProperties(prefix = "datasource.article")
  public DataSource articleDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "articleSqlSessionFactory")
  public SqlSessionFactory articleSqlSessionFactory(
      @Qualifier("articleDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean.getObject();
  }

  @Bean(name = "articleSqlSessionTemplate")
  public SqlSessionTemplate articleSqlSessionTemplate(
      @Qualifier("articleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
