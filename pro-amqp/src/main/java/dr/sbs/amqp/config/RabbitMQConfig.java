package dr.sbs.amqp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Slf4j
@Configuration
public class RabbitMQConfig {
  // 简单模式队列
  @Bean
  public Queue simpleQueue() {
    return new Queue("simple");
  }

  // 抢占模式队列
  @Bean
  public Queue raceQueue() {
    return new Queue("race");
  }

  /** 订阅模式队列 */
  @Bean
  FanoutExchange fanoutExchange() {
    return new FanoutExchange("fanoutExchange");
  }

  @Bean
  public Queue subscribe1Queue() {
    return new Queue("subscribe1");
  }

  @Bean
  public Queue subscribe2Queue() {
    return new Queue("subscribe2");
  }

  @Bean
  Binding bindingSubscribe1Exchange(Queue subscribe1Queue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(subscribe1Queue).to(fanoutExchange);
  }

  @Bean
  Binding bindingSubscribe2Exchange(Queue subscribe2Queue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(subscribe2Queue).to(fanoutExchange);
  }

  /** 路由模式队列 */
  @Bean
  public Queue route1Queue() {
    return new Queue("route1");
  }

  @Bean
  public Queue route2Queue() {
    return new Queue("route2");
  }

  @Bean
  DirectExchange directExchange() {
    return new DirectExchange("directExchange");
  }

  @Bean
  Binding bindingRoute1Exchange(Queue route1Queue, DirectExchange directExchange) {
    return BindingBuilder.bind(route1Queue).to(directExchange).with("route.1");
  }

  @Bean
  Binding bindingRoute11Exchange(Queue route1Queue, DirectExchange directExchange) {
    return BindingBuilder.bind(route1Queue).to(directExchange).with("route.11");
  }

  @Bean
  Binding bindingRoute2Exchange(Queue route2Queue, DirectExchange directExchange) {
    return BindingBuilder.bind(route2Queue).to(directExchange).with("route.2");
  }

  @Bean
  Binding bindingRoute22Exchange(Queue route2Queue, DirectExchange directExchange) {
    return BindingBuilder.bind(route2Queue).to(directExchange).with("route.22");
  }

  /** 主题模式队列 */
  @Bean
  public Queue topicAllQueue() {
    return new Queue("topicAll");
  }

  @Bean
  public Queue topic11Queue() {
    return new Queue("topic.1.1");
  }

  @Bean
  public Queue topic12Queue() {
    return new Queue("topic.1.2");
  }

  @Bean
  public Queue topic21Queue() {
    return new Queue("topic.2.1");
  }

  @Bean
  public Queue topic22Queue() {
    return new Queue("topic.2.2");
  }

  @Bean
  TopicExchange topicExchange() {
    return new TopicExchange("topicExchange");
  }

  @Bean
  Binding bindingTopicAllExchange(Queue topicAllQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(topicAllQueue).to(topicExchange).with("topic.#");
  }

  @Bean
  Binding bindingTopic11Exchange(Queue topic11Queue, TopicExchange topicExchange) {
    return BindingBuilder.bind(topic11Queue).to(topicExchange).with("topic.1.*");
  }

  @Bean
  Binding bindingTopic12Exchange(Queue topic12Queue, TopicExchange topicExchange) {
    return BindingBuilder.bind(topic12Queue).to(topicExchange).with("topic.1.*");
  }

  @Bean
  Binding bindingTopic21Exchange(Queue topic21Queue, TopicExchange topicExchange) {
    return BindingBuilder.bind(topic21Queue).to(topicExchange).with("topic.2.*");
  }

  @Bean
  Binding bindingTopic22Exchange(Queue topic22Queue, TopicExchange topicExchange) {
    return BindingBuilder.bind(topic22Queue).to(topicExchange).with("topic.2.*");
  }
}
