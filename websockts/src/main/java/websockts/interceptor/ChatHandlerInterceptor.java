package websockts.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class ChatHandlerInterceptor implements HandshakeInterceptor {

	private boolean createSession;
	
    //http session的名字，用于标记WebSocketSession，需要改成用户id
    public final static String HTTP_SESSION_NAME="http_session";
	
    public ChatHandlerInterceptor() {
    	this.createSession=true;
	}
    
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		HttpSession session = getSession(request);
		//********替换成userid
		if (session != null) {
			attributes.put(HTTP_SESSION_NAME,session.getId());
		}
		return true;
	}
	
	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(isCreateSession());
		}
		return null;
	}
	
	public boolean isCreateSession() {
		return this.createSession;
	}
	
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

	}

}
