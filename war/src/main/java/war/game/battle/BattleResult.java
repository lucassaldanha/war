package war.game.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import war.game.battle.dice.Dice;
import war.game.battle.exception.BattleSystemException;
import war.game.battle.exception.InsufficientArmyException;
import war.game.domain.Player;
import war.game.domain.Terrain;

public class BattleResult {

	private Terrain source;
	private Terrain target;

	private Dice attackingDice;
	private Dice defendingDice;

	private Integer sourceLostArmies = 0;
	private Integer targetLostArmies = 0;

	private boolean computed = false;

	/*
	 * O mapa irá guardar algumas informações sobre a batalha
	 */
	private Map<Player, List<Integer>> battleSummary = new HashMap<Player, List<Integer>>();

	public BattleResult() {
	}

	public void init() {
		this.battleSummary.put(source.getOwner(), new ArrayList<Integer>());
		this.battleSummary.put(target.getOwner(), new ArrayList<Integer>());
	}

	// public void compute() throws BattleSystemException {
	//
	// init();
	//
	// if (!computed) {
	// int attackingArmies = determineAttackingDices(this.source
	// .getArmies().getQnty());
	// int defendingArmies = determineDefendingDices(this.target
	// .getArmies().getQnty());
	// this.attackingDice.roll(attackingArmies);
	// this.defendingDice.roll(defendingArmies);
	//
	// Integer[] attackingResults = this.attackingDice.getResult();
	// Integer[] defendingResults = this.defendingDice.getResult();
	//
	// int i = 0;
	// while (attackingResults.length > i && defendingResults.length > i) {
	// if (defendingResults[i] > attackingResults[i]) {
	// /*
	// * Vitória da defesa
	// */
	// this.battleSummary.get(source.getOwner()).add(
	// defendingResults[i]);
	// this.battleSummary.get(target.getOwner()).add(
	// attackingResults[i]);
	// this.sourceLostArmies++;
	// } else {
	// /*
	// * Vitória do ataque
	// */
	// this.battleSummary.get(target.getOwner()).add(
	// attackingResults[i]);
	// this.battleSummary.get(source.getOwner()).add(
	// defendingResults[i]);
	// this.targetLostArmies++;
	// }
	// i++;
	// }
	//
	// this.computed = true;
	// }
	// }

	public void compute(int attackingArmies) throws BattleSystemException {

		if (attackingArmies < 1) {
			throw new InsufficientArmyException();
		}

		init();

		if (!computed) {
			int defendingArmies = determineDefendingDices(this.target
					.getArmies());
			this.attackingDice.roll(attackingArmies);
			this.defendingDice.roll(defendingArmies);

			Integer[] attackingResults = this.attackingDice.getResult();
			Integer[] defendingResults = this.defendingDice.getResult();

			int i = 0;
			while (attackingResults.length > i && defendingResults.length > i) {
				if (defendingResults[i] >= attackingResults[i]) {
					/*
					 * Vitória da defesa
					 */
					this.battleSummary.get(source.getOwner()).add(
							defendingResults[i]);
					this.battleSummary.get(target.getOwner()).add(
							attackingResults[i]);
					this.sourceLostArmies++;
				} else {
					/*
					 * Vitória do ataque
					 */
					this.battleSummary.get(target.getOwner()).add(
							attackingResults[i]);
					this.battleSummary.get(source.getOwner()).add(
							defendingResults[i]);
					this.targetLostArmies++;
				}
				i++;
			}

			this.computed = true;
		}
	}

	public Integer getSourceLostArmies() {
		return sourceLostArmies;
	}

	public void setSourceLostArmies(Integer sourceLostArmies) {
		this.sourceLostArmies = sourceLostArmies;
	}

	public Integer getTargetLostArmies() {
		return targetLostArmies;
	}

	public void setTargetLostArmies(Integer targetLostArmies) {
		this.targetLostArmies = targetLostArmies;
	}

	public Map<Player, List<Integer>> getBattleSummary() {
		return battleSummary;
	}

	private int determineDefendingDices(int armiesQnty)
			throws BattleSystemException {
		if (armiesQnty == 1) {
			return 1;
		} else if (armiesQnty == 2) {
			return 2;
		} else if (armiesQnty >= 3) {
			return 3;
		} else {
			throw new BattleSystemException(
					String.format(
							"Illegal quantity of armies (Expected 1, 2 or 3+. Received %d).",
							armiesQnty));
		}
	}

	public Terrain getSource() {
		return source;
	}

	public void setSource(Terrain source) {
		this.source = source;
	}

	public Terrain getTarget() {
		return target;
	}

	public void setTarget(Terrain target) {
		this.target = target;
	}

	public Integer[] getAttackingDiceResults() {
		if (attackingDice != null) {
			return attackingDice.getResult();
		} else {
			return new Integer[] {};
		}
	}

	public void setAttackingDice(Dice attackingDice) {
		this.attackingDice = attackingDice;
	}

	public Integer[] getDefendingDiceResults() {
		if (defendingDice != null) {
			return defendingDice.getResult();
		} else {
			return new Integer[] {};
		}
	}

	public void setDefendingDice(Dice defendingDice) {
		this.defendingDice = defendingDice;
	}

}
