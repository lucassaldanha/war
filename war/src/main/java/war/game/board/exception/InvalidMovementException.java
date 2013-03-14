package war.game.board.exception;

@SuppressWarnings("serial")
public class InvalidMovementException extends BoardSystemException {

	public InvalidMovementException() {
		super();
	}

	public InvalidMovementException(String msg) {
		super(msg);
	}

}
