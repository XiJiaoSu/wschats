package websockts.service;

import websockts.pojo.Message;

public interface NotificationService {
	
	/**
	 * 系统通知消息发送
	 * @param message
	 * @return
	 */
	public Message notificationAllUsers(Message message);
	
	public Message notificationSingle(String uid,Message message);
	
}
