package war.game.battle.dice;

public class MasterDice extends AbstractDice {

	private Integer[] masterNumbers;

	public MasterDice(Integer... values) {
		super(Integer.MAX_VALUE);
		this.masterNumbers = values;
	}

	@Override
	public void roll(int qnty) {
		this.result = new Integer[qnty];
		for (int i = 0; i < qnty; i++) {
			this.result[i] = this.masterNumbers[i];
		}
	}

	@Override
	public Dice clone() throws CloneNotSupportedException {
		Dice clonedDice = new MasterDice(this.masterNumbers);
		return clonedDice;
	}
}
