package dr.sbs.front.websocket;

import dr.sbs.front.component.SpringUtils;
import dr.sbs.front.service.UserService;
import dr.sbs.mbg.model.FrontUser;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** 注意：如果有配置 SecurityConfig 的访问路径白名单，务必把 /websocket/** 加入白名单 */
@ServerEndpoint("/websocket/chat/{userId}")
@Component
@Slf4j
public class ChatWebSocketServer {
  private UserService userService;

  // concurrent包的线程安全Map，用来存放每个客户端对应的WebSocket对象
  private static ConcurrentHashMap<Long, ChatWebSocketServer> webSocketMap =
      new ConcurrentHashMap<>();
  // 与某个客户端的连接会话，需要通过它来给客户端发送数据
  private Session session;
  // 接收userId
  private Long userId = 0L;

  /** 连接建立成功调用的方法 */
  @OnOpen
  public void onOpen(Session session, @PathParam("userId") Long userId) {
    log.info("打开WebSocket连接，userId:{}", userId);

    this.session = session;
    this.userId = userId;

    // 事件响应，spring bean 默认不会注入
    if (userService == null) userService = SpringUtils.getBean(UserService.class);

    FrontUser user = userService.getById(userId);
    if (user == null) {
      try {
        sendMessage("用户不存在，关闭连接");
      } catch (IOException e) {
        log.error("发送WebSocket消息失败，userId:{}", userId);
        e.printStackTrace();
      } finally {
        try {
          session.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return;
    }

    if (webSocketMap.containsKey(userId)) {
      webSocketMap.remove(userId);
      // 加入Map中
      webSocketMap.put(userId, this);
    } else {
      // 加入Map中
      webSocketMap.put(userId, this);
    }

    try {
      sendMessage("连接成功");
    } catch (IOException e) {
      log.error("发送WebSocket消息失败，userId:{}", userId);
      e.printStackTrace();
    }
  }

  /** 连接关闭调用的方法 */
  @OnClose
  public void onClose() {
    // 从Map中删除
    webSocketMap.remove(userId);
    log.info("退出WebSocket连接，userId:{}", userId);
  }

  /**
   * 收到客户端消息后调用的方法
   *
   * @param message 客户端发送过来的消息
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("接收WebSocket消息，userId:{}, message:{}", userId, message);
    // message 是JSON字符串，{action, data}

    try {
      sendInfo(userId, message);
    } catch (IOException e) {
      log.info("向其他人推送消息失败，userId:{}, message:{}", userId, message);
    }
  }

  /** 连接出现错误 */
  @OnError
  public void onError(Session session, Throwable error) {
    // 从Map中删除
    webSocketMap.remove(userId);
    log.info("WebSocket连接出现错误，userId:{}, message:{}", userId, error.getMessage());
    error.printStackTrace();
  }

  /** 用户推送消息，message 是JSON字符串，{action, data} */
  public void sendMessage(String message) throws IOException {
    log.info("WebSocket发送用户消息，userId:{}, message:{}", userId, message);
    this.session.getBasicRemote().sendText(message);
  }

  /** 用户向其他人推送消息，message 是JSON字符串，{action, data} */
  public static void sendInfo(Long userId, String message) throws IOException {
    log.info("WebSocket用户向其他人发送消息，userId:{}, message:{}", userId, message);
    for (Map.Entry<Long, ChatWebSocketServer> entry : webSocketMap.entrySet()) {
      if (!entry.getKey().equals(userId)) {
        entry.getValue().sendMessage(message);
      }
    }
  }
}
