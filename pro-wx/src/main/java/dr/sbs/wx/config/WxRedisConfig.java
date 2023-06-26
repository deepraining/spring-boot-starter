package dr.sbs.wx.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/** Redis配置类 */
@Configuration
public class WxRedisConfig {
  @Value("${wxRedis.host}")
  private String wxRedisHost;

  @Value("${wxRedis.database}")
  private Integer wxRedisDatabase;

  @Value("${wxRedis.port}")
  private Integer wxRedisPort;

  @Value("${wxRedis.password}")
  private String wxRedisPassword;

  @Value("${wxRedis.timeout}")
  private Integer wxRedisTimeout;

  @Bean
  public RedisConnectionFactory wxRedisConnectionFactory() {
    LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory();
    redisConnectionFactory.setHostName(wxRedisHost);
    redisConnectionFactory.setPort(wxRedisPort);
    redisConnectionFactory.setDatabase(wxRedisDatabase);
    redisConnectionFactory.setPassword(wxRedisPassword);
    redisConnectionFactory.setTimeout(wxRedisTimeout);
    return redisConnectionFactory;
  }

  @Bean
  public StringRedisTemplate wxStringRedisTemplate(
      @Qualifier("wxRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
    stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
    stringRedisTemplate.afterPropertiesSet();
    return stringRedisTemplate;
  }

  @Bean
  public RedisTemplate<String, Object> wxRedisTemplate(
      @Qualifier("wxRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
    RedisSerializer<Object> serializer = wxRedisSerializer();
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(serializer);
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(serializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean
  public RedisSerializer<Object> wxRedisSerializer() {
    // 创建JSON序列化器
    Jackson2JsonRedisSerializer<Object> serializer =
        new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

    SimpleBeanPropertyFilter managerFilter = SimpleBeanPropertyFilter.serializeAll();
    FilterProvider filters = new SimpleFilterProvider().addFilter("managerFilter", managerFilter);
    objectMapper.setFilterProvider(filters);

    serializer.setObjectMapper(objectMapper);
    return serializer;
  }
}
