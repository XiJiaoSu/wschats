package websockts.container.support;

import java.util.Collection;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import websockts.container.Container;
import websockts.container.WebSocketSessionContainer;

/**
 * chat session container
 * 
 * @author will
 *
 */
@Component("chatContainer")
public class DefaultChatSessionContainer implements WebSocketSessionContainer{
	
	private static final Map<String, WebSocketSession> seesionMaps= 
			new ConcurrentHashMap<String, WebSocketSession>();

	
	public void add(WebSocketSession bean,String id) {
		seesionMaps.put(id, bean);
	}

	public WebSocketSession get(String id) {
		return seesionMaps.get(id);
	}

	public void remove(String id) {
		seesionMaps.remove(id);
	}

	public Collection<String> getKeys() {
		return seesionMaps.keySet();
	}

	public int size() {
		return seesionMaps.size();
	}
}
