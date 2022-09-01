package dr.sbs.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class App extends SpringBootServletInitializer {

  public static void main(String[] args) {
    // Split config files into outer of jar file
    System.setProperty("spring.config.additional-location", "file:${HOME}/.sbs-admin/");

    ApplicationContext context = SpringApplication.run(App.class, args);
    String serverPort = context.getEnvironment().getProperty("server.port");
    log.info("App started at http://127.0.0.1:" + serverPort);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    // Split config files into outer of jar file
    System.setProperty("spring.config.additional-location", "file:${HOME}/.sbs-admin/");
    return application.sources(App.class);
  }
}
