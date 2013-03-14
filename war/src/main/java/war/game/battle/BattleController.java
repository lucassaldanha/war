package war.game.battle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import war.game.GameController;
import war.game.battle.dice.AbstractDice;
import war.game.battle.dice.Dice;
import war.game.battle.dice.SixDice;
import war.game.battle.exception.BattleSystemException;
import war.game.battle.exception.IllegalTargetException;
import war.game.battle.exception.InsufficientArmyException;
import war.game.battle.exception.UnreacheableTargetException;
import war.game.battle.util.BattleLog;
import war.game.board.exception.BoardSystemException;
import war.game.domain.Terrain;
import war.game.domain.impl.TerrainImpl;
import war.game.util.GameSystemException;

public class BattleController implements BattleOperations {

	Logger LOG = LoggerFactory.getLogger(BattleController.class.getName());

	private static BattleController SOLE_INSTANCE = new BattleController();

	private GameController gc = GameController.getInstance();

	private AbstractDice dicePrototype = null;

	private BattleController() {
	}

	public static BattleController getInstance() {
		return SOLE_INSTANCE;
	}

	public void initBattleSystem() {
		initBattleSystem(new SixDice());
	}

	public void initBattleSystem(AbstractDice dice) {
		setDiceType(dice);
	}

	public void setDiceType(AbstractDice dice) {
		this.dicePrototype = dice;
	}

	public BattleResult attack(Terrain source, Terrain target)
			throws GameSystemException {

		if (source.getArmies() > 3) {
			return attack(source, target, 3);
		} else {
			return attack(source, target, source.getArmies() - 1);
		}

	}

	public BattleResult attack(Terrain source, Terrain target,
			int attackingArmies) throws GameSystemException {

		return attack(source, target, attackingArmies, attackingArmies);
	}

	public BattleResult attack(Terrain source, Terrain target,
			int attackingArmies, int movingArmies) throws GameSystemException {
		/*
		 * Verifica se o dono do território atacante e do atacado são o mesmo
		 */
		if (source.getOwner().equals(target.getOwner())) {
			throw new IllegalTargetException(
					"Você não pode atacar seu próprio território.");
		}

		/*
		 * Verifica se o território atacante tem fronteira com o território alvo
		 */
		if (gc.getBoardOperations().isNeighbor(source, target) == false) {
			throw new UnreacheableTargetException();
		}

		/*
		 * Verifica se a quantidade de exércitos de ataque é maior do que o
		 * máximo permitido
		 */
		if (attackingArmies > 3) {
			throw new BattleSystemException(
					"You can't attack with more then 3 armies at the same time.");
		}

		/*
		 * Verifica se o território atacante tem a quantidade de exércitos
		 * mínima para atacar
		 */
		if (source.getArmies() - attackingArmies < 1) {
			throw new InsufficientArmyException(
					"Exércitos insuficientes (Esperados 2 ou mais exércitos para atacar).");
		}

		/*
		 * Verifica se o território atacante pode mover a quantidade de
		 * exércitos solicitada
		 */
		if (source.getArmies() - movingArmies < 1) {
			throw new InsufficientArmyException(
					"Exércitos insuficientes (Você deve manter pelo menos 1 exército em seu território).");
		}

		return performAttack(source, target, attackingArmies, movingArmies);
	}

	private BattleResult performAttack(Terrain source, Terrain target,
			int attackingArmies, int movingArmies) throws BattleSystemException {
		Dice attackingDice = null;
		Dice deffendingDice = null;

		try {
			attackingDice = dicePrototype.clone();
			deffendingDice = dicePrototype.clone();
		} catch (CloneNotSupportedException e) {
			throw new BattleSystemException("Error cloning dices.");
		}

		BattleLog.logAttack(source, target, attackingArmies);

		BattleResult result = new BattleResult();

		result.setSource(source);
		result.setTarget(target);

		result.setAttackingDice(attackingDice);
		result.setDefendingDice(deffendingDice);

		result.compute(attackingArmies);

		BattleLog.logResult(source, target, result);

		/*
		 * Em caso de conquista de território, remaneja a quantidade de
		 * exércitos atacantes que sobreviveram para o novo território
		 */
		((TerrainImpl) source).getArmyObject().setQnty(
				source.getArmies() - result.getSourceLostArmies());
		((TerrainImpl) target).getArmyObject().setQnty(
				target.getArmies() - result.getTargetLostArmies());

		if (target.getArmies() == 0) {
			((TerrainImpl) target).setOwner(source.getOwner());

			try {
				gc.getBoardOperations().moveArmy(source, target, movingArmies);
			} catch (BoardSystemException e) {
				throw new BattleSystemException(e.getMessage());
			}

			BattleLog.logConquer(source, target, movingArmies);
		}

		return result;
	}

	public void setGameController(GameController gc) {
		this.gc = gc;
	}
}
