package websockts.container;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import websockts.message.MessageReceive;

import java.io.IOException;

/**
 *聊天抽象Session
 */
public abstract class AbstractChatSessionService<T extends MessageReceive> implements WebSocketSessionService {


    public void send(String id,String content) {
        T message=parseMessage(content);

        switch (message.getType()){
            case WSConstant.MESSAGE_RECEIVE_SINGLE:
                sendSingle(id,message);
                break;
            case WSConstant.MESSAGE_RECEIVE_GROUP:
                sendGroup(id,message);
                break;
            case WSConstant.MESSAGE_RECEIVE_NOTIFICATION:
                sendAll(id,message);
                break;
            default:
                sendTypeError(id);
        }
    }


    protected abstract T parseMessage(String content);

    /**
     * 给组内人发消息
     */
    protected abstract void sendGroup(String id,T message);

    /**
     * 私聊
     */
    protected abstract void sendSingle(String id,T message);

    /**
     *
     */
    protected  abstract void sendAll(String id,T message);

    /**
     * 信息发送类型出错
     */
    protected  abstract void sendTypeError(String id);


    /**
     * 发送消息
     */
    protected void sendMessage(WebSocketSession session,String content){
        if (session==null||content==null||content.length()==0){
            return;
        }
        try {
            session.sendMessage(new TextMessage(content.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
