package websockts.message;

public class MessageReceive {

	private int type;

	private String from ;
	
	private Object to;

	private Object content;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Object getTo() {
		return to;
	}

	public void setTo(Object to) {
		this.to = to;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MessageReceive{" +
				"type=" + type +
				", from='" + from + '\'' +
				", to=" + to +
				", content=" + content +
				'}';
	}
}
