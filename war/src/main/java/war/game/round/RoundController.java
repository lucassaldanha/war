package war.game.round;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import war.game.GameController;
import war.game.board.BoardController;
import war.game.domain.Continent;
import war.game.domain.GamePhase;
import war.game.domain.Goal;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.domain.goal.AbstractGoal;
import war.game.domain.impl.AIPlayer;
import war.game.domain.impl.AbstractPlayer;
import war.game.domain.impl.HumanPlayer;
import war.game.util.GoalUtils;
import war.game.util.PlayerType;
import war.ui.util.ArmyColor;

public class RoundController implements RoundOperations {

	Logger LOG = LoggerFactory.getLogger(RoundController.class);

	private static RoundController SOLE_INSTANCE = new RoundController();

	private GameController gc = GameController.getInstance();

	private List<Player> playerList = new ArrayList<Player>();
	private LinkedList<Player> playerQueue = new LinkedList<Player>();
	private Map<Player, Boolean> readyPlayers = new HashMap<Player, Boolean>();
	private Player currentPlayer = null;
	private GamePhase currentPhase = GamePhase.DEPLOY;

	private boolean gameOver = false;

	private int aiPlayersCont = 0;

	public static RoundController getInstance() {
		return SOLE_INSTANCE;
	}

	public void addplayer(PlayerType type, String playerName, ArmyColor color) {
		AbstractPlayer newPlayer = null;

		switch (type) {
		case HUMAN: {
			newPlayer = new HumanPlayer();
			newPlayer.setName(playerName);
			newPlayer.setColor(color);
			break;
		}
		case COMPUTER_EASY: {
			if (playerName == null) {
				newPlayer = new AIPlayer(1, 1, ++aiPlayersCont);
			} else {
				newPlayer = new AIPlayer(1, 1, playerName);
			}
			newPlayer.setColor(color);
			break;
		}
		case COMPUTER_NORMAL: {
			throw new UnsupportedOperationException();
		}
		case COMPUTER_HARD: {
			throw new UnsupportedOperationException();
		}
		}

		playerList.add(newPlayer);
		playerQueue.add(newPlayer);
	}

	public void initRoundSystem() {
		for (Player p : playerList) {
			Goal g = GoalUtils.getInstance().getGoal();
			((AbstractGoal) g).setPlayer(p);
			((AbstractPlayer) p).setGoal(g);

			LOG.debug(String.format("[GOAL] %s goal is: %s", p,
					g.getDescription()));
		}
		Collections.shuffle(playerQueue);
		currentPlayer = playerQueue.removeFirst();
		startDeployPhase();
	}

	public void advancePhase() {

		if (!gameOver) {

			if (currentPhase.equals(GamePhase.DEPLOY)
					&& readyPlayers.containsKey(currentPlayer)) {

				startAttackPhase();

			} else if (currentPhase.equals(GamePhase.ATTACK)
					&& readyPlayers.containsKey(currentPlayer)) {

				startMovementPhase();

			} else if (currentPhase.equals(GamePhase.MOVE)
					|| !readyPlayers.containsKey(currentPlayer)) {

				if (!readyPlayers.containsKey(currentPlayer)) {
					readyPlayers.put(currentPlayer, true);
				}

				// Coloca jogador atual para o final da fila e coloca o próximo
				// da fila como jogador atual
				playerQueue.addLast(currentPlayer);
				currentPlayer = playerQueue.removeFirst();

				if (checkGoals()) {
					gc.setObservableChanged();
					gc.notifyObservers(this);
				}

				checkPlayersAlive();

				Boolean skipDeploy = readyPlayers.get(currentPlayer);
				if (skipDeploy != null && skipDeploy.booleanValue()) {
					startAttackPhase();
					readyPlayers.put(currentPlayer, false);
				} else {
					startDeployPhase();
				}
			}

		}

	}

	private void startDeployPhase() {
		((AbstractPlayer) currentPlayer).addExtraArmies(calculateExtraArmies());

		gc.sendInfo(String.format(
				"[ROUND] %s começou a etapa de COLOCAR EXÉRCITOS",
				currentPlayer), true);
		currentPhase = GamePhase.DEPLOY;
		callAI();
	}

	private void startAttackPhase() {
		gc.sendInfo(String.format("[ROUND] %s começou a etapa de ATAQUE",
				currentPlayer), true);
		currentPhase = GamePhase.ATTACK;
		callAI();
	}

	private void startMovementPhase() {
		gc.sendInfo(String.format(
				"[ROUND] %s começou a etapa de REMANEJAMENTO", currentPlayer),
				true);

		currentPhase = GamePhase.MOVE;
		callAI();
	}

	private void callAI() {
		if (currentPlayer instanceof AIPlayer) {
			((AIPlayer) currentPlayer).play();
		}
	}

	private boolean checkGoals() {
		for (Player player : playerList) {

			if (player.getGoal() == null) {
				return false;
			}

			gameOver = gameOver
					|| player.getGoal().isCompleted()
					|| gc.getBoardOperations().getPlayersTerritories(player)
							.size() == BoardController.getInstance().getBoard()
							.getGraph().vertexSet().size();
		}
		return gameOver;
	}

	private void checkPlayersAlive() {
		for (Player p : playerList) {
			Set<Terrain> terrains = gc.getBoardOperations()
					.getPlayersTerritories(p);
			boolean alive = false;
			for (Terrain t : terrains) {
				if (t.getArmies() > 0) {
					alive = true;
					break;
				}
			}

			if (!alive) {
				playerQueue.remove(p);
				playerList.remove(p);

				gc.sendInfo(String.format("[ROUND] %s foi eliminado.", p), true);
			}
		}
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public GamePhase getCurrentPhase() {
		return this.currentPhase;
	}

	@Override
	public List<Player> getPlayerQueue() {
		return this.playerQueue;
	}

	@Override
	public List<Player> getPlayerList() {
		return this.playerList;
	}

	private int calculateExtraArmies() {
		int terrainQnty = gc.getBoardOperations()
				.getPlayersTerritories(currentPlayer).size();

		int bonus = 0;

		for (Continent c : gc.getBoardOperations().getBoard().getContinents()
				.values()) {
			if (currentPlayer.equals(c.getOwner())) {
				LOG.debug(String.format(
						"[ROUND] %s ganhou %d exército(s) bônus (%s)",
						currentPlayer, c.getBonus(), c.getName()));
				bonus += c.getBonus();
			}
		}

		if (terrainQnty <= 6) {
			terrainQnty = bonus + 3;
		} else {
			terrainQnty = bonus + (terrainQnty / 2);
		}

		LOG.debug(String.format("[ROUND] %s recebe %d exército(s) para Deploy.",
				currentPlayer, terrainQnty));

		return terrainQnty;
	}

	public void setGameController(GameController gc) {
		this.gc = gc;
	}
}
