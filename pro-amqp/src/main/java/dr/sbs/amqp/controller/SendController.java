package dr.sbs.amqp.controller;

import dr.sbs.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
public class SendController {
  private static int i = 0;

  @Autowired RabbitTemplate rabbitTemplate;

  // 发送简单模式队列消息
  @RequestMapping(value = "/sendSimple", method = RequestMethod.GET)
  public CommonResult<Integer> sendSimple() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendSimple: {}", msg);
    this.rabbitTemplate.convertAndSend("simple", msg);
    return CommonResult.success(1);
  }

  // 发送抢占模式队列消息
  @RequestMapping(value = "/sendRace", method = RequestMethod.GET)
  public CommonResult<Integer> sendRace() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendRace: {}", msg);
    this.rabbitTemplate.convertAndSend("race", msg);
    return CommonResult.success(1);
  }

  // 发送订阅模式队列消息
  @RequestMapping(value = "/sendSubscribe", method = RequestMethod.GET)
  public CommonResult<Integer> sendSubscribe() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendSubscribe: {}", msg);
    this.rabbitTemplate.convertAndSend("subscribeExchange", "", msg);
    return CommonResult.success(1);
  }

  // 发送路由模式队列消息
  @RequestMapping(value = "/sendRoute1", method = RequestMethod.GET)
  public CommonResult<Integer> sendRoute1() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendRoute1: {}", msg);
    this.rabbitTemplate.convertAndSend("directExchange", "route.1", msg);
    return CommonResult.success(1);
  }

  @RequestMapping(value = "/sendRoute11", method = RequestMethod.GET)
  public CommonResult<Integer> sendRoute11() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendRoute11: {}", msg);
    this.rabbitTemplate.convertAndSend("directExchange", "route.11", msg);
    return CommonResult.success(1);
  }

  @RequestMapping(value = "/sendRoute2", method = RequestMethod.GET)
  public CommonResult<Integer> sendRoute2() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendRoute2: {}", msg);
    this.rabbitTemplate.convertAndSend("directExchange", "route.2", msg);
    return CommonResult.success(1);
  }

  @RequestMapping(value = "/sendRoute22", method = RequestMethod.GET)
  public CommonResult<Integer> sendRoute22() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendRoute22: {}", msg);
    this.rabbitTemplate.convertAndSend("directExchange", "route.22", msg);
    return CommonResult.success(1);
  }

  // 发送主题模式队列消息
  @RequestMapping(value = "/sendTopic1", method = RequestMethod.GET)
  public CommonResult<Integer> sendTopic1() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendTopic1: {}", msg);
    this.rabbitTemplate.convertAndSend("topicExchange", "topic.1.0", msg);
    return CommonResult.success(1);
  }

  @RequestMapping(value = "/sendTopic2", method = RequestMethod.GET)
  public CommonResult<Integer> sendTopic2() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendTopic2: {}", msg);
    this.rabbitTemplate.convertAndSend("topicExchange", "topic.2.0", msg);
    return CommonResult.success(1);
  }

  @RequestMapping(value = "/sendTopic3", method = RequestMethod.GET)
  public CommonResult<Integer> sendTopic3() {
    i++;
    String msg = "rabbitmq msg " + i;
    log.info("============================== sendTopic3: {}", msg);
    this.rabbitTemplate.convertAndSend("topicExchange", "topic.3.0", msg);
    return CommonResult.success(1);
  }
}
