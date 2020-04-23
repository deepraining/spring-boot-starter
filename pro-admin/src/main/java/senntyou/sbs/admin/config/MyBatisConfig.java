package senntyou.sbs.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"senntyou.sbs.mbg.mapper", "senntyou.sbs.admin.dao"})
public class MyBatisConfig {
  //
}
