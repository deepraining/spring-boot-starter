package dr.sbs.cli;

import dr.sbs.cli.service.ArticleService;
import dr.sbs.cli.service.UserService;
import dr.sbs.common.util.JsonUtil;
import dr.sbs.mbg.model.Article;
import dr.sbs.mbg.model.FrontUser;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Init implements ApplicationRunner {
  @Autowired ArticleService articleService;
  @Autowired UserService userService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("args.getSourceArgs()");
    for (String i : args.getSourceArgs()) {
      log.info(i);
    }

    log.info("args.getOptionNames()");
    for (String i : args.getOptionNames()) {
      log.info(i);
    }

    log.info("args.getNonOptionArgs()");
    for (String i : args.getNonOptionArgs()) {
      log.info(i);
    }

    List<Article> articleList = articleService.list("", 10, 1);
    log.info("articles");
    log.info(JsonUtil.objectToJson(articleList));

    List<FrontUser> userList = userService.list(10, 1);
    log.info("users");
    log.info(JsonUtil.objectToJson(userList));
  }
}
