package war.game.battle.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import war.game.GameController;
import war.game.battle.BattleResult;
import war.game.domain.Terrain;

public class BattleLog {

	static Logger LOG = LoggerFactory.getLogger(BattleLog.class.getName());
	static GameController gc = GameController.getInstance();

	public static void logAttack(Terrain source, Terrain target,
			int attackingArmies) {

		gc.sendInfo(String.format(
				"[ATTACK] %s está atacando %s com %d exército(s).", source, target,
				attackingArmies), true);
	}

	public static void logResult(Terrain source, Terrain target,
			BattleResult result) {

		gc.sendInfo(String.format("[DICE] Ataque %s \nDefesa %s",
				Arrays.asList(result.getAttackingDiceResults()),
				Arrays.asList(result.getDefendingDiceResults())));

		gc.sendInfo(String.format(
				"[RESULT] Ataque %s Defesa %s (%s perdeu %d exército(s), %s perdeu %d exército(s)).",
				Arrays.asList(result.getAttackingDiceResults()),
				Arrays.asList(result.getDefendingDiceResults()),
				source.getName(), result.getSourceLostArmies(),
				target.getName(), result.getTargetLostArmies()));
	}

	public static void logConquer(Terrain source, Terrain target,
			int movedArmies) {

		gc.sendInfo(String.format(
				"[CONQUER] %s invadiu %s com %d exército(s) (de %s).", source
						.getOwner().getName(), target.getName(), movedArmies,
				source.getName()), true);
	}
}
