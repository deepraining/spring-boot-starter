package senntyou.sbs.mbg;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {
  public static void main(String[] args) throws Exception {
    List<String> warnings = new ArrayList<String>();

    InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(is);
    is.close();

    DefaultShellCallback callback = new DefaultShellCallback(true);

    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

    myBatisGenerator.generate(null);

    for (String warning : warnings) {
      System.out.println(warning);
    }
  }
}
