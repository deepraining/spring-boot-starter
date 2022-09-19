package dr.sbs.rws.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"dr.sbs.mbg.mapper", "dr.sbs.rws.dao"})
public class MyBatisConfig {
  //
}
