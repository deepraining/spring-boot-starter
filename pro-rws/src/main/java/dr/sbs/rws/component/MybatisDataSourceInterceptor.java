package dr.sbs.rws.component;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Intercepts({
  @Signature(
      type = Executor.class,
      method = "update",
      args = {MappedStatement.class, Object.class}),
  @Signature(
      type = Executor.class,
      method = "query",
      args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
  @Signature(
      type = Executor.class,
      method = "query",
      args = {
        MappedStatement.class,
        Object.class,
        RowBounds.class,
        ResultHandler.class,
        CacheKey.class,
        BoundSql.class
      })
})
public class MybatisDataSourceInterceptor implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
    if (!synchronizationActive) {
      Object[] objects = invocation.getArgs();
      MappedStatement ms = (MappedStatement) objects[0];

      // 仅当未在事务中，并且调用的sql是select类型时，将数据源设为read
      if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
        // 一主一从
        // DataSourceHolder.putDataSource(DataSourceHolder.READ_DATASOURCE);

        // 一主多从
        // 多以写自己的选择算法
        List<String> keyList =
            Arrays.asList(DataSourceHolder.READ_DATASOURCE, DataSourceHolder.READ_DATASOURCE2);
        long keyIndex = System.currentTimeMillis() % 2;
        DataSourceHolder.putDataSource(keyList.get(Math.toIntExact(keyIndex)));
      }
    }

    // 其他情况下，使用默认的write数据源
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {}
}
