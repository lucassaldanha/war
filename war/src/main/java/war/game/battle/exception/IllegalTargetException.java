package war.game.battle.exception;

@SuppressWarnings("serial")
public class IllegalTargetException extends BattleSystemException {

	public IllegalTargetException() {
		super();
	}

	public IllegalTargetException(String msg) {
		super(msg);
	}

}
