package war.game.round.exception;

import war.game.util.GameSystemException;

@SuppressWarnings("serial")
public class RoundSystemException extends GameSystemException {

	public RoundSystemException() {
		super();
	}

	public RoundSystemException(String msg) {
		super(msg);
	}

}
