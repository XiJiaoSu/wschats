package websockts.message;

/**
 * session发送给客户端的信息格式
 */
public class MessageSend {

    private int code;

    private Object from;

    public Object content;

    public MessageSend(int code, Object from, Object content) {
        this.code = code;
        this.from = from;
        this.content = content;
    }

    public MessageSend() {
    }

    public MessageSend(int code) {
        this.code = code;
    }

    public MessageSend(int code, Object content) {
        this.code = code;
        this.content = content;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public int getType() {
        return code;
    }

    public void setType(int type) {
        this.code = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
