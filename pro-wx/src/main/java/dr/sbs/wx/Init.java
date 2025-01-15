package dr.sbs.wx;

import dr.sbs.common.util.SbsCacheKeyUtil;
import dr.sbs.common.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Init implements ApplicationRunner {
  @Value("${app.workerId}")
  private long workerId;

  @Value("${app.dataCenterId}")
  private long dataCenterId;

  @Value("${spring.profiles.active}")
  private String springProfilesActive;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("App is running in server env: {}", springProfilesActive);

    // Init UuidUtil
    UuidUtil.init(workerId, dataCenterId);

    // cache env
    SbsCacheKeyUtil.ENV = springProfilesActive;

    // constants
    Constants.isLocalDevEnv = springProfilesActive.equals("dev");
    Constants.isOnlineTestEnv = springProfilesActive.equals("test");
    Constants.isDevOrTestEnv = Constants.isLocalDevEnv || Constants.isOnlineTestEnv;
  }
}
