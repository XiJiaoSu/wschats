package websockts.service.imp;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import websockts.container.WSConstant;
import websockts.container.WebSocketSessionService;
import websockts.message.MessageReceive;
import websockts.parser.Parser;
import websockts.pojo.Message;
import websockts.service.NotificationService;

/**
 * 系统通知：将消息通知给所有在线用户
 * @author will
 *
 */
@Service("notificationService")
public class NotificationServiceImp implements NotificationService {

    @Autowired
    @Qualifier("chatSessionService")
    private WebSocketSessionService sessionService;
    
    @Autowired
    @Qualifier("parser")
    private Parser parser;
    

	public Message notificationAllUsers(Message message) {
		message.setId(UUID.randomUUID().toString());
		MessageReceive receive=new MessageReceive();
		receive.setType((WSConstant.MESSAGE_RECEIVE_NOTIFICATION));
		receive.setContent(message.getContent());
		sessionService.send("123123123", parser.ObjectToString(receive));
		return message;
	}


	public Message notificationSingle(String uid, Message message) {
		message.setId(UUID.randomUUID().toString());
		MessageReceive receive=new MessageReceive();
		receive.setType((WSConstant.MESSAGE_RECEIVE_SINGLE));
		receive.setContent(message.getContent());
		receive.setTo(uid);
		sessionService.send("123123123", parser.ObjectToString(receive));
		return message;
	}

   
    
}
