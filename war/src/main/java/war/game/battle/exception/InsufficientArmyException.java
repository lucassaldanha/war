package war.game.battle.exception;

@SuppressWarnings("serial")
public class InsufficientArmyException extends BattleSystemException {

	public InsufficientArmyException() {
		super();
	}

	public InsufficientArmyException(String msg) {
		super(msg);
	}

}
