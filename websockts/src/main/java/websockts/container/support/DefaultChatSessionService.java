package websockts.container.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import websockts.container.AbstractChatSessionService;
import websockts.container.WSConstant;
import websockts.container.WebSocketSessionContainer;
import websockts.message.MessageReceive;
import websockts.message.MessageSend;
import websockts.parser.Parser;

import java.util.Collection;
import java.util.List;

@Component("chatSessionService")
public final class DefaultChatSessionService extends AbstractChatSessionService<MessageReceive> {

	@Autowired
	@Qualifier("chatContainer")
	private WebSocketSessionContainer container;

	@Autowired
	@Qualifier("parser")
	private Parser parser;

	/**
	 * 上线后有两个任务：(1) 自己的信息 (2) 当前用户获取所有在线用户，(3) 通知其他用户上线信息
	 * @param session
	 * @param id
	 */
	public void onLine(WebSocketSession session, String id) {
		String strOnLine=parser.ObjectToString(new MessageSend(WSConstant.MESSAGE_SEND_ONLINE_NOTIFICATION,id));
		container.add(session,id);
		Collection<String> keys = container.getKeys();
			//自己上线，将自己的连接信息返回
		sendMessage(session,parser.ObjectToString(
				new MessageSend(WSConstant.MESSAGE_SEND_SELF_INFO,id)));
			//给自己发送在线列表
		sendMessage(session,parser.ObjectToString(
				new MessageSend(WSConstant.MESSAGE_SEND_ONLINE_LIST,keys)));
		for (String key:keys) {//通知别人自己上线
			if (key.equals(id)){
				continue;
			}
			sendMessage(container.get(key),strOnLine);//通知其他用户自己上线
		}
	}

	/**
	 * 下线，任务:（1）session移除；(2) 通知其他在线用户已经自己下线
	 */
	public void offLine(String id) {
		container.remove(id);
		Collection<String> keys = container.getKeys();
		String strOffLine=parser.ObjectToString(new MessageSend(WSConstant.MESSAGE_SEND_OFFLINE_NOTIFICATION,id));
		for (String key:keys) {
			sendMessage(container.get(key),strOffLine);
		}
	}


	protected MessageReceive parseMessage(String content) {
		return parser.stringToObject(content,MessageReceive.class);
	}

	protected void sendGroup(String id, MessageReceive message) {
		Object objectTo=message.getTo();
		String strChat=parser.ObjectToString(new MessageSend(WSConstant.MESSAGE_SEND_CHAT,id,message.getContent()));
		if (objectTo instanceof List){
			for (String uid : (List<String>)objectTo){//将消息发送所有人
				sendMessage(container.get(uid),strChat);
			}
		}
	}

	
	protected void sendSingle(String id, MessageReceive message) {
		Object objectTo=message.getTo();
		String strChat=parser.ObjectToString(new MessageSend(WSConstant.MESSAGE_SEND_CHAT,id,message.getContent()));
		if (objectTo instanceof String){
			sendMessage(container.get((String) objectTo),strChat);
		}
	}

	/**
	 * 信息通告全部在线成员
	 * @param id
	 * @param message
	 */
	protected void sendAll(String id, MessageReceive message) {

		Collection<String> keys = container.getKeys();
		String strChat=parser.ObjectToString(new MessageSend(WSConstant.MESSAGE_SEND_CHAT,id,message.getContent()));
		for (String key:keys) {
			if (key.equals(id)){//是自己，无须给自己发送
				continue;
			}
			sendMessage(container.get(key),strChat);
		}
	}

	protected void sendTypeError(String id) {

	}

}
