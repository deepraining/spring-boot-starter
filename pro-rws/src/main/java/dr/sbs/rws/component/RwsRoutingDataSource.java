package dr.sbs.rws.component;

import static dr.sbs.rws.component.DataSourceHolder.READ_DATASOURCE;
import static dr.sbs.rws.component.DataSourceHolder.READ_DATASOURCE2;
import static dr.sbs.rws.component.DataSourceHolder.WRITE_DATASOURCE;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
public class RwsRoutingDataSource extends AbstractRoutingDataSource {
  @Resource(name = "writeDataSourceProperties")
  private DataSourceProperties writeProperties;

  @Resource(name = "readDataSourceProperties")
  private DataSourceProperties readProperties;

  @Resource(name = "readDataSourceProperties2")
  private DataSourceProperties readProperties2;

  @Override
  public void afterPropertiesSet() {
    DataSource writeDataSource = writeProperties.initializeDataSourceBuilder().build();
    DataSource readDataSource = readProperties.initializeDataSourceBuilder().build();
    DataSource readDataSource2 = readProperties2.initializeDataSourceBuilder().build();

    setDefaultTargetDataSource(writeDataSource);

    Map<Object, Object> dataSourceMap = new HashMap<>();
    dataSourceMap.put(WRITE_DATASOURCE, writeDataSource);
    dataSourceMap.put(READ_DATASOURCE, readDataSource);
    dataSourceMap.put(READ_DATASOURCE2, readDataSource2);
    setTargetDataSources(dataSourceMap);

    super.afterPropertiesSet();
  }

  @Override
  protected Object determineCurrentLookupKey() {
    String key = DataSourceHolder.getDataSource();

    if (key == null) {
      // default datasource
      return WRITE_DATASOURCE;
    }

    return key;
  }
}
