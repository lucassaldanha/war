package war.game.battle.exception;

import war.game.util.GameSystemException;

@SuppressWarnings("serial")
public class BattleSystemException extends GameSystemException {

	public BattleSystemException() {
		super();
	}

	public BattleSystemException(String msg) {
		super(msg);
	}

}
