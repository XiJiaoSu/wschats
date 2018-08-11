(function (global,factory) {

    factory(global);

})(typeof(window) !== "undefined" ? window : this,function (window) {

    let JCoding={};
    let api={};

    JCoding.api=api;

    JCoding.api.IP="10.222.29.181";
    JCoding.api.PORT="9090";

    getIp=obj=>{
        if (obj){
            return this.JCoding.api.IP
        }
        return obj.ip==undefined?this.JCoding.IP:obj.ip;
    }

    getPort=obj=> {
        if (obj==undefined){
            return this.JCoding.api.PORT;
        }
        return obj.port==undefined?this.JCoding.api.PORT:obj.port;
    }


    JCoding.api.wsUrl=obj=>{// /websockts/chatHadler
        return "ws://"+this.getIp(obj)+":"+this.getPort(obj)+obj.url;
    }

    JCoding.api.httpUrl=obj=>{
        return "http://"+this.JCoding.api.getIp(obj)+":"+this.JCoding.api.getPort(obj)+obj.url;
    }

    JCoding.WebSocket=function(obj){
        let socket=new WebSocket(obj.url)
        socket.onopen=obj.onOpen;
        socket.onclose=obj.onClose;
        socket.onmessage=obj.onReceive;
        socket.onerror=obj.onError;
        return function (txt) {
            if(socket.readyState == socket.OPEN) {
                socket.send(txt);
            }else{
                console.log("connect error")
            }
        }
    }
    //websocket id
    JCoding.wsUserId=undefined;
    JCoding.onLists=[];//在线用户列表
    JCoding.onChats=[];//正在聊天的人员，length=0群发
    JCoding.bubble=100;//自己发送消息状态
    JCoding.status={};
    //私聊
    JCoding.status.MESSAGE_RECEIVE_SINGLE=0;
    //组聊
    JCoding.status.MESSAGE_RECEIVE_GROUP=1;
    //推送通知
    JCoding.status.MESSAGE_RECEIVE_NOTIFICATION=2;
    //上线后获取在线列表
    JCoding.status.MESSAGE_SEND_ONLINE_LIST=100;
    //当前用户上线，并将上线消息通知给其他用户
    JCoding.status.MESSAGE_SEND_ONLINE_NOTIFICATION=101;
    //当前用户下线，并将下线消息通知给其他用户
    JCoding.status.MESSAGE_SEND_OFFLINE_NOTIFICATION=102;
    //正常聊天消息
    JCoding.status.MESSAGE_SEND_CHAT=103;
    //连接成功获取自己的信息，这里只是自己的id
    JCoding.status.MESSAGE_SEND_SELF_INFO=104;

    JCoding.log=function(txt){
        console.log(txt);
    }

    window.$$ = window.JCoding=JCoding;
})