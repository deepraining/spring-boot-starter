package senntyou.sbs.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:/cached-uid-generator.xml"})
public class UidConfig {
  //
}
