package war.game.board.exception;

import war.game.util.GameSystemException;

@SuppressWarnings("serial")
public class BoardSystemException extends GameSystemException {

	public BoardSystemException() {
		super();
	}

	public BoardSystemException(String msg) {
		super(msg);
	}

}
