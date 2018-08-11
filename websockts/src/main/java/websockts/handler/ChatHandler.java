package websockts.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import websockts.container.SessionService;
import websockts.container.WSConstant;
import websockts.container.WebSocketSessionService;
import websockts.interceptor.ChatHandlerInterceptor;

/**
 * 聊天信息处理
 * @author will
 *
 */
public class ChatHandler extends TextWebSocketHandler{
	
	@Autowired
	@Qualifier("chatSessionService")
	private WebSocketSessionService chatSessionService;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		chatSessionService.send(httpSessionId(session),message.getPayload());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		chatSessionService.onLine(session, httpSessionId(session));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		chatSessionService.offLine(httpSessionId(session));
	}

	private String httpSessionId(WebSocketSession session){
		return (String) session.getAttributes().get(ChatHandlerInterceptor.HTTP_SESSION_NAME);
	}
	
}
