package war.game.battle.dice;

import java.util.Arrays;
import java.util.Collections;

public abstract class AbstractDice implements Dice {

	protected Integer[] result;
	protected Integer maxValue;

	public AbstractDice(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public void roll(int qnty) {
		this.result = new Integer[qnty];
		for (int i = 0; i < qnty; i++) {
			this.result[i] = (int) (Math.round(Math.random() * (maxValue - 1)) + 1);
		}

		Arrays.sort(this.result, Collections.reverseOrder());
	}

	public Integer[] getResult() {
		return result;
	}

	@Override
	public abstract Dice clone() throws CloneNotSupportedException;
}
