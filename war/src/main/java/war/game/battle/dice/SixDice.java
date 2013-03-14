package war.game.battle.dice;

public class SixDice extends AbstractDice {

	public SixDice() {
		super(6);
	}

	@Override
	public Dice clone() throws CloneNotSupportedException {
		Dice clonedDice = new SixDice();
		return clonedDice;
	}

}
