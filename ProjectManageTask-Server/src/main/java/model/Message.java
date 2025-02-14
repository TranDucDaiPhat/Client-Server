package model;

public class Message {
    private String message;
    private String data;

    public Message(String message, String data) {
        this.message = message;
        this.data = data;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + ", data=" + data + "]";
	}
}
