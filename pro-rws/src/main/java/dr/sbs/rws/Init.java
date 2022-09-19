package dr.sbs.rws;

import dr.sbs.common.util.UuidUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements ApplicationRunner {
  @Override
  public void run(ApplicationArguments args) throws Exception {
    // Init UuidUtil
    UuidUtil.init(1, 1);
  }
}
