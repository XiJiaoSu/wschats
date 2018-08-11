package websockts.container;

import org.springframework.web.socket.WebSocketSession;

/**
 *
 */
public interface WebSocketSessionService extends SessionService<WebSocketSession,String> {

    /**
     * 发送消息
     * @param content
     */
    void send(String id,String content);

}
