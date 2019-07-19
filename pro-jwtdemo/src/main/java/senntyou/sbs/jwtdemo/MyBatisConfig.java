package senntyou.sbs.jwtdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"senntyou.sbs.gen.mapper", "senntyou.sbs.jwtdemo.dao"})
public class MyBatisConfig {
  //
}
