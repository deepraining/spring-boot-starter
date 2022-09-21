package dr.sbs.amqp.component;

import com.rabbitmq.client.Channel;
import dr.sbs.amqp.websocket.ChatWebSocketServer;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RabbitListener(queues = "subscribe2")
@Component
public class Subscribe2Receiver {
  // 如果 spring.rabbitmq.listener.direct.acknowledge-mode: auto，则可以用这个方式，会自动ack
  // @RabbitHandler
  public void directHandlerAutoAck(String msg) {
    String logMsg = "subscribe2 队列处理器，接收消息：" + msg;
    log.info(logMsg);
    ChatWebSocketServer.sendInfo(logMsg);
  }

  @RabbitHandler
  public void directHandlerManualAck(String msg, Message message, Channel channel) {
    // 配置 spring.rabbitmq.listener.direct.acknowledge-mode: manual，需要手动ack
    final long deliveryTag = message.getMessageProperties().getDeliveryTag();
    String logMsg = "subscribe2 队列处理器，接收消息：" + msg;
    log.info(logMsg);
    ChatWebSocketServer.sendInfo(logMsg);
    try {
      // 通知 MQ 消息已被成功消费,可以ACK了
      channel.basicAck(deliveryTag, false);
      log.info("subscribe2 队列处理器手动ACK完成，接收消息：{}", msg);
    } catch (IOException e) {
      try {
        // 处理失败,重新压入MQ
        channel.basicRecover();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }
}
