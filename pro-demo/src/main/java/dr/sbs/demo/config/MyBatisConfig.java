package dr.sbs.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"dr.sbs.mbg.mapper", "dr.sbs.demo.dao"})
public class MyBatisConfig {
  //
}
