package war.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import war.game.GameController;
import war.game.board.BoardOperations;
import war.game.board.exception.BoardSystemException;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.util.Response;

public class ProbabilisticAI implements AIImplementation {

	int AImode;
	Player playerOwner;

	public boolean setMode(int mode) {
		AImode = mode;
		return false;
	}

	public boolean sendtroop(BoardOperations mapa, int num) {
		switch (AImode) {
		case 1: {
			doRandomSendTroops(mapa, num);
			break;
		}
		case 2: {
			throw new UnsupportedOperationException();
		}
		default: {
			break;
		}
		}
		return false;
	}

	public boolean attack(BoardOperations mapa) {
		switch (AImode) {
		case 1: {
			doRandomProbabilisticAttack(mapa);
			break;
		}
		case 2: {
			throw new UnsupportedOperationException();
		}
		default: {
			break;
		}
		}
		return false;
	}

	public boolean redistrib(BoardOperations mapa) {
		switch (AImode) {
		case 1: {
			doRandomRedistribute(mapa);
			break;
		}
		case 2: {
			throw new UnsupportedOperationException();
		}
		default: {
			break;
		}
		}
		return false;
	}

	private void doRandomSendTroops(BoardOperations boardOps, int num) {
		List<Terrain> bases = new ArrayList<Terrain>(
				boardOps.getPlayersTerritories(playerOwner));
		while (num > 0) {
			int randoNum = (int) (Math.random() * num) + 1;
			int aBase = (int) (Math.random() * (bases.size() - 1));
			AIController.think();

			GameController.getInstance().requestAddArmy(bases.get(aBase),
					randoNum);

			// }
			num = num - randoNum;
			bases.remove(aBase);
		}
	}

	/**
	 * Só ataca em condições razoáveis
	 * 
	 * @param boardOps
	 */
	private void doRandomProbabilisticAttack(BoardOperations boardOps) {
		List<Terrain> terrains = boardOps
				.getPlayerTerritoriesToAttack(playerOwner);
		Collections.shuffle(terrains);
		for (Terrain playerTerrain : terrains) {
			List<Terrain> targets = boardOps.getEnemyNeighbor(playerTerrain);
			Collections.shuffle(targets);
			for (Terrain aTarget : targets) {
				if (playerTerrain.getArmies() >= aTarget.getArmies()) {

					AIController.think();

					Response resp = GameController.getInstance()
							.requestSelectTerrain(playerTerrain);
					if (resp.equals(Response.OK)) {
						int attackingArmies = (playerTerrain.getArmies() > 3 ? 3
								: playerTerrain.getArmies() - 1);
						GameController.getInstance().requestAttack(aTarget,
								attackingArmies);
						break;
					}
				}
			}
		}
	}

	private void doRandomRedistribute(BoardOperations boardOps) {
		List<Terrain> bases = new ArrayList<Terrain>(
				boardOps.getPlayersTerritories(playerOwner));
		Collections.shuffle(bases);

		Set<Terrain> blackList = new HashSet<Terrain>();

		for (Terrain base : bases) {

			if (blackList.contains(base)) {
				break;
			}

			if (base.getArmies() > 1) {
				List<Terrain> targets = null;
				try {
					targets = new ArrayList<Terrain>(
							boardOps.getNeighbors(base));
					Collections.shuffle(targets);
				} catch (BoardSystemException e) {
					break;
				}

				for (Terrain target : targets) {

					if (blackList.contains(target)) {
						break;
					}

					if (target.getOwner().equals(base.getOwner())
							&& target.getArmies() < base.getArmies()) {

						Response resp = GameController.getInstance()
								.requestSelectTerrain(base);

						AIController.think();

						if (resp.equals(Response.OK)) {
							GameController.getInstance().requestArmyMovement(
									target, (base.getArmies() - 1));

							blackList.add(base);
							blackList.add(target);
						}
					}

				}
			}
		}

	}

	// private boolean RandomAttack(BoardOperations mapa) {
	// List<Terrain> bases = mapa.getPlayerTerritoriesToAttack(playerOwner);
	// int maxAttacks = 1;
	// while (!bases.isEmpty() && (maxAttacks > 0)) {
	// int aBase = (int) (bases.size() * Math.random());
	// List<Terrain> targets = mapa.getEnemyNeighbor(bases.get(aBase));
	// if (targets.size() > 0) {
	// int theTarget = (int) (targets.size() * Math.random());
	// int troops = attackRandom(mapa, bases, targets, aBase,
	// theTarget);
	// if (targets.get(theTarget).getArmies() == 0) {
	// // afterBattleRandom(mapa, bases, targets, aBase, theTarget,
	// // troops);
	// }
	// }
	// bases = mapa.getPlayerTerritoriesToAttack(playerOwner);
	// maxAttacks--;
	// }
	// return false;
	// }
	//
	// private int attackRandom(BoardOperations mapa, List<Terrain> bases,
	// List<Terrain> targets, int aBase, int theTarget) {
	// BattleController battle = BattleController.getInstance();
	// BattleResult result = null;
	// int value = 0;
	//
	// AIController.think();
	//
	// switch (AImode) {
	// case 1: {
	// value = maxNum(bases.get(aBase).getArmies() - 1, 3);
	//
	// AIController.think();
	//
	// Response resp = GameController.getInstance().requestSelectTerrain(
	// bases.get(aBase));
	// if (resp.equals(Response.OK)) {
	// GameController.getInstance().requestAttack(
	// targets.get(theTarget), value);
	// }
	//
	// break;
	// }
	// case 2: {
	// try {
	// value = maxNum(bases.get(aBase).getArmies() / 2, 3);
	// result = battle.attack(bases.get(aBase),
	// targets.get(theTarget), value);
	// } catch (GameSystemException e) {
	// e.printStackTrace();
	// }
	// break;
	// }
	// default: {
	// break;
	// }
	// }
	//
	// if (targets.get(theTarget).getArmies() > 1) {
	// return 0;
	// } else {
	// return (value - targets.get(theTarget).getArmies());
	// }
	// }

	// private int maxNum(int x, int num) {
	// if (x > 2) {
	// return 3;
	// } else {
	// return x;
	// }
	// }

	// private void afterBattleRandom(BoardOperations mapa, List<Terrain> bases,
	// List<Terrain> targets, int aBase, int theTarget, int troops) {
	// int toMove = 0;
	// switch (AImode) {
	// case 1: {
	// toMove = troops;
	// }
	// case 2: {
	// toMove = maxNum(bases.get(aBase).getArmies() / 2, troops);
	// break;
	// }
	// default: {
	// break;
	// }
	// }
	// try {
	//
	// // TODO estou pensando em criar um método específico para movimentar
	// // exércitos em caso de conquista
	//
	// mapa.moveArmy(bases.get(aBase), targets.get(theTarget), toMove);
	// // targets.get(theTarget).setOwner(playerOwner);
	// } catch (BoardSystemException e) {
	// e.printStackTrace();
	// }
	// }

	public void setup(int mode, Player owner) {
		AImode = mode;
		playerOwner = owner;
	}

}
