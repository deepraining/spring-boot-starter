package dr.sbs.amqp.websocket;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/chat")
@Component
@Slf4j
public class ChatWebSocketServer {
  // 与客户端对应的WebSocket对象，仅维持一个实例
  private static ChatWebSocketServer instance;
  // 与某个客户端的连接会话，需要通过它来给客户端发送数据
  private Session session;

  /** 连接建立成功调用的方法 */
  @OnOpen
  public void onOpen(Session session) {
    log.info("打开WebSocket连接");

    this.session = session;

    instance = this;

    try {
      sendMessage("连接成功");
    } catch (IOException e) {
      log.error("发送WebSocket消息失败");
      e.printStackTrace();
    }
  }

  /** 连接关闭调用的方法 */
  @OnClose
  public void onClose() {
    instance = null;
    log.info("退出WebSocket连接");
  }

  /**
   * 收到客户端消息后调用的方法
   *
   * @param message 客户端发送过来的消息
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("接收WebSocket消息, message:{}", message);
    // message 是JSON字符串，{action, data}
  }

  /** 连接出现错误 */
  @OnError
  public void onError(Session session, Throwable error) {
    instance = null;
    log.info("WebSocket连接出现错误, message:{}", error.getMessage());
    error.printStackTrace();
  }

  /** 用户推送消息，message 是JSON字符串，{action, data} */
  public synchronized void sendMessage(String message) throws IOException {
    log.info("WebSocket发送用户消息, message:{}", message);
    session.getBasicRemote().sendText(message);
  }

  /** 向用户推送消息，message 是JSON字符串，{action, data} */
  public static void sendInfo(String message) {
    log.info("WebSocket向用户发送消息, message:{}", message);
    if (instance == null) {
      log.info("WebSocket向用户发送消息失败，用户不存在, message:{}", message);
      return;
    }
    try {
      instance.sendMessage(message);
    } catch (IOException e) {
      log.info("WebSocket向用户发送消息失败，发送错误, message:{}", message);
      e.printStackTrace();
    }
  }
}
