package websockts.container;

public class WSConstant {


     //私聊
     public final static int MESSAGE_RECEIVE_SINGLE=0;
     //组聊
     public final static int MESSAGE_RECEIVE_GROUP=1;
     //推送通知
     public final static int MESSAGE_RECEIVE_NOTIFICATION=2;

     //上线后获取在线列表
     public final static int MESSAGE_SEND_ONLINE_LIST=100;

     //当前用户上线，并将上线消息通知给其他用户
     public final static int MESSAGE_SEND_ONLINE_NOTIFICATION=101;

     //当前用户下线，并将下线消息通知给其他用户
     public final  static  int MESSAGE_SEND_OFFLINE_NOTIFICATION=102;

     //正常聊天消息
     public final  static int MESSAGE_SEND_CHAT=103;

     //连接成功获取自己的信息，这里只是自己的id
     public final  static int MESSAGE_SEND_SELF_INFO=104;
}
