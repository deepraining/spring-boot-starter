package dr.sbs.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Slf4j
@SpringBootApplication
public class App extends SpringBootServletInitializer {

  public static void main(String[] args) {
    // Split config files into outer of jar file
    System.setProperty("spring.config.additional-location", "file:${HOME}/.sbs-cli/");

    SpringApplication.run(App.class, args);
    log.info("App executed.");
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    // Split config files into outer of jar file
    System.setProperty("spring.config.additional-location", "file:${HOME}/.sbs-cli/");
    return application.sources(App.class);
  }
}
