package dr.sbs.front.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"dr.sbs.mbg.mapper", "dr.sbs.front.dao"})
public class MyBatisConfig {
  //
}
