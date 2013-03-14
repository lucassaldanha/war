package war.game;

import java.util.List;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import war.game.battle.BattleController;
import war.game.battle.BattleOperations;
import war.game.battle.BattleResult;
import war.game.board.BoardController;
import war.game.board.BoardOperations;
import war.game.board.exception.BoardSystemException;
import war.game.domain.GamePhase;
import war.game.domain.Goal;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.domain.impl.HumanPlayer;
import war.game.round.RoundController;
import war.game.round.RoundOperations;
import war.game.util.GameSystemException;
import war.game.util.PlayerType;
import war.game.util.Response;
import war.ui.util.ArmyColor;

public class GameController extends Observable implements GameEngine {

	protected Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	private static GameController SOLE_INSTANCE = new GameController();
	private static BattleController battleOps = BattleController.getInstance();
	private static BoardController boardOps = BoardController.getInstance();
	private static RoundController roundOps = RoundController.getInstance();

	private GameController() {
	}

	public static GameController getInstance() {
		return SOLE_INSTANCE;
	}

	@Override
	public void initGame() {

		sendInfo("[SYSTEM] Iniciando jogo...");

		battleOps.initBattleSystem();
		boardOps.initBoardSystem();
		roundOps.initRoundSystem();
	}

	public BoardOperations getBoardOperations() {
		return boardOps;
	}

	public BattleOperations getBattleOperations() {
		return battleOps;
	}

	public RoundOperations getRoundOperations() {
		return roundOps;
	}

	public void requestAddPlayer(PlayerType type, String playerName,
			ArmyColor color) {
		roundOps.addplayer(type, playerName, color);
	}

	public List<Player> requestPlayerList() {
		return roundOps.getPlayerList();
	}

	@Override
	public Player requestCurrentPlayer() {
		return roundOps.getCurrentPlayer();
	}

	@Override
	public Goal requestPlayerGoal() {
		return roundOps.getCurrentPlayer().getGoal();
	}

	public GamePhase requestCurrentPhase() {
		return roundOps.getCurrentPhase();
	}

	@Override
	public void requestAdvancePhase() {
		roundOps.advancePhase();
		setChanged();
		notifyObservers(roundOps.getCurrentPhase());
	}

	@Override
	public Response requestSelectTerrain(Terrain clickedTerrain) {

		sendInfo(String.format("[REQUEST] Seleciona %s", clickedTerrain));

		if (clickedTerrain.getOwner().equals(roundOps.getCurrentPlayer())) {
			boardOps.setSelectedTerrain(clickedTerrain);
			return Response.OK;
		} else {
			return sendError(String.format(
					"O território %s não pertence ao jogador da vez (%s).",
					clickedTerrain.getName(), roundOps.getCurrentPlayer()
							.getName()));
		}
	}

	@Override
	public Response requestDeselectTerrain(Terrain clickedTerrain) {
		sendInfo(String.format("[REQUEST] Deseleciona %s", clickedTerrain));

		if (clickedTerrain == null) {
			if (boardOps.getSelectedTerrain() != null) {
				boardOps.setSelectedTerrain(null);
			}
			return Response.OK;
		} else if (boardOps.getSelectedTerrain() != null
				&& boardOps.getSelectedTerrain().equals(clickedTerrain)) {
			boardOps.setSelectedTerrain(null);
			return Response.OK;
		} else {
			return Response.ERROR.msg(String.format(
					"O território %s não está selecionado.",
					clickedTerrain.getName()));
		}
	}

	@Override
	public Terrain requestSelectedTerrain() {
		return boardOps.getSelectedTerrain();
	}

	@Override
	public Response requestAddArmy(Terrain clickedTerrain, int qnty) {

		sendInfo(String
				.format("[REQUEST] Adicionado %d exército à(ao) %s (%s tem %d exércitos extras).",
						qnty, clickedTerrain.getName(), roundOps
								.getCurrentPlayer().getName(), roundOps
								.getCurrentPlayer().getExtraArmies()));

		try {
			boardOps.addArmy(roundOps.getCurrentPlayer(), clickedTerrain, qnty);
			return sendInfo(
					String.format(
							"[BOARD] Adicionado %d exército à(ao) %s (%d exército(s) restando)",
							qnty, clickedTerrain.getName(), roundOps
									.getCurrentPlayer().getExtraArmies()), true);
		} catch (BoardSystemException e) {
			return sendError(e.getMessage());
		}
	}

	@Override
	public Response requestAttack(Terrain target) {

		sendInfo(String.format("[REQUEST] Ataca %s (atacando de %s).", target,
				boardOps.getSelectedTerrain()));

		try {
			BattleResult result = battleOps.attack(
					boardOps.getSelectedTerrain(), target);

			if (result != null) {
				setChanged();
				notifyObservers(result);
			}

			return Response.OK;
		} catch (GameSystemException e) {
			return sendError(e.getMessage());
		}
	}

	@Override
	public Response requestAttack(Terrain target, int qnty) {

		sendInfo(String.format(
				"[REQUEST] Ataca %s com %d exército(s) (atacando de %s).",
				target, qnty, boardOps.getSelectedTerrain()));

		try {
			BattleResult result = battleOps.attack(
					boardOps.getSelectedTerrain(), target, qnty);

			if (result != null) {
				setChanged();
				notifyObservers(result);
			}

			return Response.OK;
		} catch (GameSystemException e) {
			return sendError(e.getMessage());
		}
	}

	@Override
	public Response requestAttack(Terrain target, int attkQnty, int movQnty) {

		sendInfo(String.format(
				"[REQUEST] Ataca %s com %d exército(s) (atacando de %s).",
				target, attkQnty, boardOps.getSelectedTerrain()));

		try {
			BattleResult result = battleOps.attack(
					boardOps.getSelectedTerrain(), target, attkQnty, movQnty);

			if (result != null) {
				setChanged();
				notifyObservers(result);
			}

			return Response.OK;
		} catch (GameSystemException e) {
			return sendError(e.getMessage());
		}
	}

	@Override
	public Response requestArmyMovementAfterBattle(Terrain target, int qnty) {

		throw new UnsupportedOperationException();

		// sendInfo(String.format(
		// "[REQUEST] Move to %s with %d armies (moving from %s).",
		// target, qnty, selectedTerrain));
		//
		// if (conqueredTerrains.get(target).equals(selectedTerrain) == false) {
		// return Response.ERROR
		// .msg("Só é possível transferir exércitos para um território conquistado.");
		// }
		//
		// try {
		// boardOps.moveArmy(selectedTerrain, target, qnty);
		// return Response.OK;
		// } catch (BoardSystemException e) {
		// return Response.ERROR.msg(e.getMessage());
		// }

	}

	@Override
	public Response requestArmyMovement(Terrain target, int qnty) {

		sendInfo(String.format(
				"[REQUEST] Moveu para %s com %d exército(s) (movendo de %s).",
				target, qnty, boardOps.getSelectedTerrain()));

		try {
			boardOps.moveArmy(boardOps.getSelectedTerrain(), target, qnty);

			sendInfo(String.format(
					"[BOARD] Remanejou %d exército(s) de %s para %s.", qnty,
					boardOps.getSelectedTerrain().getName(), target.getName()),
					true);

			return Response.OK;
		} catch (BoardSystemException e) {
			return sendError(e.getMessage());
		}
	}

	public Response sendError(String errorMsg) {
		LOG.error(errorMsg);
		return sendResponse(Response.ERROR.msg(errorMsg));
	}

	public Response sendInfo(String infoMsg) {
		LOG.debug(infoMsg);
		return Response.OK.msg(infoMsg);
	}

	public Response sendInfo(String infoMsg, boolean toScreen) {
		LOG.info(infoMsg);
		return sendResponse(Response.OK.msg(infoMsg));
	}

	private Response sendResponse(Response resp) {
		setChanged();
		notifyObservers(resp.msg(resp.getMessage().replaceAll("^\\[.*?\\]\\s+",
				"")));
		return resp;
	}

	public void setObservableChanged() {
		setChanged();
	}

}
