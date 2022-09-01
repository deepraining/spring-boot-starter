package dr.sbs.admin.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigInteger;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  /**
   * Transform long to string when jackson serialize.
   *
   * @return Jackson2ObjectMapperBuilderCustomizer
   */
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

    return new Jackson2ObjectMapperBuilderCustomizer() {
      @Override
      public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
      }
    };
  }
}
