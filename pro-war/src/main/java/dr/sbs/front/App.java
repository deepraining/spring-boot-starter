package dr.sbs.front;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Slf4j
@SpringBootApplication
@EnableRedisHttpSession
public class App extends SpringBootServletInitializer {

  public static void main(String[] args) {
    // Split config files into outer of jar file
    System.setProperty("spring.config.additional-location", "file:${HOME}/.sbs-front/");

    ApplicationContext context = SpringApplication.run(App.class, args);
    String serverPort = context.getEnvironment().getProperty("server.port");
    log.info("App started at http://127.0.0.1:" + serverPort);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    // Split config files into outer of jar file
    System.setProperty("spring.config.additional-location", "file:${HOME}/.sbs-front/");
    return application.sources(App.class);
  }
}
