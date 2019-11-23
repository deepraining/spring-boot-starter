package senntyou.sbs.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"senntyou.sbs.mbg.mapper", "senntyou.sbs.demo.dao"})
public class MyBatisConfig {
  //
}
