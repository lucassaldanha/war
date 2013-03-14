package war.game.util;

public enum Response {

	OK(1, "Request ok"), ERROR(-1, "Request error");

	int code;
	String message;

	private Response(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Response msg(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return message;
	}
}
