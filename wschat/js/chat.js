
$("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);

let wsOpen=function(evt){
    $$.log("onOpen");
}

let wsClose=function(evt){
    $$.log(evt);
};

let wsReceive=function (evt) {
    let data=JSON.parse(evt.data);
    let code=data.type;
    if (code==$$.status.MESSAGE_SEND_SELF_INFO){//用户登录之后获取自己的信息
        $$.wsUserId=data.content;
        $$.onLists=[];
    } else if (code==$$.status.MESSAGE_SEND_ONLINE_LIST){//用户上线后去获取在线列表
        $$.onLists=$$.onLists.concat(data.content);
        renderOnLineList();
    }else if (code==$$.status.MESSAGE_SEND_ONLINE_NOTIFICATION){//别人上线
        let index=$$.onLists.indexOf(data.content);
        if (index<0){
            $$.onLists.push(data.content);
        }
        renderOnLineList();
    } else if (code==$$.status.MESSAGE_SEND_OFFLINE_NOTIFICATION){//别人下线
        let index=$$.onLists.indexOf(data.content);
        $$.onLists.splice(index,1);
        renderOnLineList();
    }
    if (code==$$.status.MESSAGE_SEND_CHAT){//聊天信息
        renderChatContent(
            {
                id: new Date().getMilliseconds(),
                info: data.content,
                component: $("#chat_content")
            })
        $("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);
    }
}

let wsConnectError=function (evt) {
    console.log(evt);
}

function renderOnLineList() {
    let component=$("#chat_right");
    component.empty();
    $$.onLists.forEach(function (item) {
        let tmp=$("#onlist_tmp").clone();
        tmp.attr("id",item);
        tmp.css("display","block");
        tmp.find(".online_item").text(item);
        if (item==$$.wsUserId){
            tmp.css("background","red");
        }
        tmp.on("dblclick",function (evt) {
            $$.log(this.id);
            let groups=$("#chat_group");
            if (groups.find("#chat_"+this.id).length==0){
                let chat_tmp=$("#chat_list_tmp").clone();
                chat_tmp.attr("id","chat_"+this.id);
                chat_tmp.css("display","block");
                chat_tmp.find(".chat_name").text(this.id);
                chat_tmp.on("dblclick",function (evt) {
                    this.remove();
                });
                groups.append(chat_tmp);
            }
        });
        component.append(tmp);
    })
}

function renderChatContent(obj) {
    let tmp=$("#bubble_tmp").clone();
    tmp.attr("id",obj.id);
    tmp.css("display","block");
    tmp.find(".article").text(obj.info);
    if (obj.type==$$.bubble){
        tmp.css("text-align","right");
        tmp.find(".article").css("background","#ef8201");
    }
    obj.component.append(tmp);
}

let ws={
    url:$$.api.wsUrl({url:"/websockts/chatHadler"}),
    // url:$$.api.wsUrl({url:"/chatHadler"}),
    onOpen:wsOpen,
    onClose:wsClose,
    onReceive:wsReceive,
    onError:wsConnectError
};

let wsSend=$$.WebSocket(ws);

function isNotification() {

    let groups=$("#chat_group").find(".chat_obj");
    if (groups.length==0){//左侧聊天人数为空，通知全部
        return {type:$$.status.MESSAGE_RECEIVE_NOTIFICATION};
    }else if(groups.length==1){//左侧只有一个：私聊
        let single=groups[0].id.slice(5);
        return {type:$$.status.MESSAGE_RECEIVE_SINGLE,to:single}
    }else{//右侧多个，群聊
        let tos=[];
        for(let i=0;i<groups.length;i++){
            tos.push(groups[i].id.slice(5));
        }
        return {type:$$.status.MESSAGE_RECEIVE_GROUP,to:tos}
    }
}

$("#chat_send").on("click",function (evt) {
    $$.log(isNotification())
    let infos=$("#chat_info");
    if(infos.val().length>0){
        //发送信息
        let info=isNotification()
        // info.type=2;
        info.content=$("#chat_info").val();
        wsSend(JSON.stringify(info));
        //聊天内容记录框记录添加
        renderChatContent(
            {
                id: new Date().getMilliseconds(),
                info:infos.val(),
                component:$("#chat_content"),
                type:$$.bubble
            }
        );
        infos.val("");
        $("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);
    }
})