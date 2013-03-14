package war.game.battle.exception;

@SuppressWarnings("serial")
public class UnreacheableTargetException extends BattleSystemException {
	public UnreacheableTargetException() {
		super("Target out of reach.");
	}
}
