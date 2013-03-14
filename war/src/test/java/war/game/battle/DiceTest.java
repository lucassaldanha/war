package war.game.battle;

import org.junit.Assert;
import org.junit.Test;

import war.game.battle.dice.Dice;
import war.game.battle.dice.SixDice;

public class DiceTest {

	@Test
	public void test_dice_reverse_order() {
		for (int i = 0; i < 10; i++) {
			Dice d = new SixDice();
			d.roll(3);
			Integer[] rolledResult = d.getResult();

			for (int j = 0; j < rolledResult.length - 1; j++) {
				Assert.assertTrue(rolledResult[j] >= rolledResult[j + 1]);
			}
		}
	}

}
