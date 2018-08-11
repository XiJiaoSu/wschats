
$("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);

let wsOpen=function(evt){
    $$.log("onOpen")
    // for (let i=0;i<40;i++){
    //     renderChatContent(
    //         {
    //             id: "bubble"+i,
    //             info: "sadf"+i,
    //             component: $("#chat_content")
    //         })
    //     $("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);
    // }
}

let wsClose=function(evt){
    $$.log(evt);
};

let wsReceive=function (evt) {
    let data=JSON.parse(evt.data);
    let code=data.type;
    $$.log(data)
    if (code==$$.status.MESSAGE_SEND_SELF_INFO){//用户登录之后获取自己的信息
        $$.wsUserId=data.content;
        $$.onLists=[]
    } else if (code==$$.status.MESSAGE_SEND_ONLINE_LIST){//用户上线后去获取在线列表
        $$.onLists=$$.onLists.concat(data.content);
        renderOnLineList();
    }else if (code==$$.status.MESSAGE_SEND_ONLINE_NOTIFICATION){//别人上线
        let index=$$.onLists.indexOf(data.content);
        if (index<0){
            $$.log(index+" : "+data.content)
            $$.onLists.push(data.content);
            $$.log($$.onLists)
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
    $$.log("renderOnLineList "+$$.onLists)
    let component=$("#chat_right");
    component.empty();
    $$.onLists.forEach(function (item) {
        let tmp=$("#onlist_tmp").clone();
        tmp.attr("id",item);
        tmp.css("display","block")
        tmp.find(".online_item").text(item)
        if (item==$$.wsUserId){
            tmp.css("background","red");
        }
        component.append(tmp)
    })
}

function renderChatContent(obj) {
    let tmp=$("#bubble_tmp").clone();
    tmp.attr("id",obj.id);
    tmp.css("display","block")
    tmp.find(".article").text(obj.info)
    if (obj.type==100){
        tmp.css("text-align","right");
        tmp.find(".article").css("background","#ef8201")
    }
    obj.component.append(tmp)
}

let ws={
    url:$$.api.wsUrl({url:"/chatHadler"}),
    // url:$$.api.wsUrl({url:"/websockts/chatHadler"}),
    onOpen:wsOpen,
    onClose:wsClose,
    onReceive:wsReceive,
    onError:wsConnectError
};

let wsSend=$$.WebSocket(ws);

$("#chat_send").on("click",function (evt) {

    let infos=$("#chat_info");
    if(infos.val().length>0){
        console.log($("#chat_info").val());
        //发送信息
        let info={}
        info.type=2;
        info.content=$("#chat_info").val();
        wsSend(JSON.stringify(info));
        //聊天内容记录框记录添加
        renderChatContent(
            {
                id:"bubble",
                info:infos.val(),
                component:$("#chat_content"),
                type:100
            }
        );
        infos.val("")
        $("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);
    }
})