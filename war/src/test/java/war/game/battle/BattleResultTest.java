package war.game.battle;

import org.junit.Assert;
import org.junit.Test;

import war.game.battle.dice.MasterDice;
import war.game.battle.exception.BattleSystemException;
import war.game.domain.Player;
import war.game.domain.impl.HumanPlayer;
import war.game.domain.impl.SimpleArmy;
import war.game.domain.impl.TerrainImpl;

public class BattleResultTest {

	Player playerA = new HumanPlayer();
	Player playerB = new HumanPlayer();

	TerrainImpl source = new TerrainImpl();
	TerrainImpl target = new TerrainImpl();

	protected void setUp() throws Exception {
		((HumanPlayer) playerA).setName("playerA");
		((HumanPlayer) playerB).setName("playerB");

		source.setOwner(playerA);
		target.setOwner(playerB);
	}

	@Test
	public void test_valid_1x1_battle_attack_wins()
			throws BattleSystemException {
		source.setArmies(new SimpleArmy(2));
		target.setArmies(new SimpleArmy(1));

		BattleResult br = new BattleResult();
		br.setSource(source);
		br.setTarget(target);

		br.setAttackingDice(new MasterDice(6));
		br.setDefendingDice(new MasterDice(2));

		br.compute(1);

		Assert.assertEquals(0, (int) br.getSourceLostArmies());
		Assert.assertEquals(1, (int) br.getTargetLostArmies());
	}

	@Test
	public void test_valid_1x1_battle_defence_wins()
			throws BattleSystemException {
		source.setArmies(new SimpleArmy(2));
		target.setArmies(new SimpleArmy(1));

		BattleResult br = new BattleResult();
		br.setSource(source);
		br.setTarget(target);

		br.setAttackingDice(new MasterDice(1));
		br.setDefendingDice(new MasterDice(3));

		br.compute(1);

		Assert.assertEquals(1, (int) br.getSourceLostArmies());
		Assert.assertEquals(0, (int) br.getTargetLostArmies());
	}

	@Test
	public void test_valid_1x1_battle_defence_wins_with_deal()
			throws BattleSystemException {
		source.setArmies(new SimpleArmy(2));
		target.setArmies(new SimpleArmy(1));

		BattleResult br = new BattleResult();
		br.setSource(source);
		br.setTarget(target);

		br.setAttackingDice(new MasterDice(6));
		br.setDefendingDice(new MasterDice(6));

		br.compute(1);

		Assert.assertEquals(1, (int) br.getSourceLostArmies());
		Assert.assertEquals(0, (int) br.getTargetLostArmies());
	}

	@Test
	public void test_valid_2x3_battle_attack_wins()
			throws BattleSystemException {
		source.setArmies(new SimpleArmy(2));
		target.setArmies(new SimpleArmy(3));

		BattleResult br = new BattleResult();
		br.setSource(source);
		br.setTarget(target);

		br.setAttackingDice(new MasterDice(3, 3));
		br.setDefendingDice(new MasterDice(1, 1, 1));

		br.compute(1);

		Assert.assertEquals(0, (int) br.getSourceLostArmies());
		Assert.assertEquals(1, (int) br.getTargetLostArmies());
	}

	@Test
	public void test_valid_2x3_battle_defence_wins()
			throws BattleSystemException {
		source.setArmies(new SimpleArmy(2));
		target.setArmies(new SimpleArmy(3));

		BattleResult br = new BattleResult();
		br.setSource(source);
		br.setTarget(target);

		br.setAttackingDice(new MasterDice(1, 1));
		br.setDefendingDice(new MasterDice(3, 3, 3));

		br.compute(1);

		Assert.assertEquals(1, (int) br.getSourceLostArmies());
		Assert.assertEquals(0, (int) br.getTargetLostArmies());
	}

	@Test(expected = BattleSystemException.class)
	public void test_invalid_1x1_battle() throws BattleSystemException {
		source.setArmies(new SimpleArmy(1));
		target.setArmies(new SimpleArmy(1));

		BattleResult br = new BattleResult();
		br.setSource(source);
		br.setTarget(target);

		br.setAttackingDice(new MasterDice(1));
		br.setDefendingDice(new MasterDice(3));

		br.compute(0);
	}

}
