package websockts.pojo;

public class JsonResult {
	
	private int code=200;
	private String msg;
	
	private Object result;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public JsonResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getResult() {
		return result;
	}

	public JsonResult setResult(Object result) {
		this.result = result;
		return this;
	}
	
}
