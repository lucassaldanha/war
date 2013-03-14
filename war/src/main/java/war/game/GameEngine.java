package war.game;

import java.util.List;
import java.util.Observer;

import war.game.battle.BattleOperations;
import war.game.board.BoardOperations;
import war.game.domain.GamePhase;
import war.game.domain.Goal;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.round.RoundOperations;
import war.game.util.PlayerType;
import war.game.util.Response;
import war.ui.util.ArmyColor;

public interface GameEngine {

	public void initGame();

	public void requestAddPlayer(PlayerType type, String playerName,
			ArmyColor color);

	public Player requestCurrentPlayer();

	public Terrain requestSelectedTerrain();
	
	public Goal requestPlayerGoal();

	public GamePhase requestCurrentPhase();

	public void requestAdvancePhase();

	/**
	 * Método chamado pela interface para solicitar a inserção de exércitos no
	 * território selecionado.<br>
	 * <br>
	 * O controlador de jogo irá verificar se o território selecionado é do
	 * jogador que está na vez.
	 * 
	 * @param qnty
	 *            a quantidade de exércitos a serem adicionados
	 * @return um inteiro que corresponde ao status da solicitação
	 */
	public Response requestAddArmy(Terrain clickedTerrain, int qnty);

	/**
	 * Método chamado pela interface para solicitar um ataque. O ataque tem como
	 * origem o território selecionado e como destino o território passado como
	 * parâmetro. <br>
	 * <br>
	 * O controlador de jogo irá verificar se o território é do jogador que está
	 * na vez.
	 * 
	 * @param target
	 *            o território destino da ação
	 * 
	 * @return um inteiro que corresponde ao status da solicitação
	 */
	public Response requestAttack(Terrain target);

	/**
	 * Método chamado pela interface para solicitar um ataque. O ataque tem como
	 * origem o território selecionado e como destino o território passado como
	 * parâmetro. <br>
	 * <br>
	 * O controlador de jogo irá verificar se o território é do jogador que está
	 * na vez.
	 * 
	 * @param target
	 *            o território destino da ação
	 * 
	 * @param qnty
	 *            a quantidade de territórios atacantes
	 * 
	 * @return um inteiro que corresponde ao status da solicitação
	 */
	public Response requestAttack(Terrain target, int qnty);

	/**
	 * Método chamado pela interface para solicitar um ataque. O ataque tem como
	 * origem o território selecionado e como destino o território passado como
	 * parâmetro. <br>
	 * <br>
	 * O controlador de jogo irá verificar se o território é do jogador que está
	 * na vez.
	 * 
	 * @param target
	 *            o território destino da ação
	 * 
	 * @param attkQnty
	 *            a quantidade de territórios atacantes
	 * 
	 * @param movQnty
	 *            a quantidade de exércitos que serão movidos no caso de uma
	 *            conquista
	 * 
	 * @return um inteiro que corresponde ao status da solicitação
	 */
	public Response requestAttack(Terrain target, int attkQnty, int movQnty);

	/**
	 * Método chamado pela interface para fazer um remanejamento após uma
	 * batalha. O remanejamento tem como origem o território selecionado e
	 * destino o território passado como parâmetro. <br>
	 * <br>
	 * O controlador de jogo irá verificar: <li>se o território de origem é do
	 * jogador que está na vez</li> <li>se o território selecionado foi um
	 * território vitorioso</li> <li>se o território destino foi conquistado a
	 * partir do território selecionado</li>
	 * 
	 * <br>
	 * <br>
	 * 
	 * @param target
	 *            o território destino da ação
	 * @param qnty
	 *            a quantidade de exércitos a serem remanejados
	 * 
	 * @return um inteiro que corresponde ao status da solicitação
	 */
	public Response requestArmyMovementAfterBattle(Terrain target, int qnty);

	/**
	 * Método chamado pela interface para fazer um remanejamento. O
	 * remanejamento tem como origem o território selecionado e destino o
	 * território passado como parâmetro.
	 * 
	 * O controlador de jogo irá verificar se o território de origem é do
	 * jogador que está na vez. <br>
	 * <br>
	 * 
	 * @param target
	 *            o território destino da ação
	 * @param qnty
	 *            a quantidade de exércitos a serem remanejados
	 * 
	 * @return um inteiro que corresponde ao status da solicitação
	 */
	public Response requestArmyMovement(Terrain target, int qnty);

	/**
	 * Método chamado pela interface para definir um território como selecionado
	 * 
	 * @param selectedTerrain
	 *            o território que está selecionado na interface
	 * @return um inteiro que corresponde ao status da solicitação
	 */
	public Response requestSelectTerrain(Terrain clickedTerrain);

	public Response requestDeselectTerrain(Terrain clickedTerrain);

	public BoardOperations getBoardOperations();

	public BattleOperations getBattleOperations();

	public RoundOperations getRoundOperations();

	/**
	 * Retorna uma lista de proxies de jogadores, representando a lista de
	 * jogadores do jogo.
	 * 
	 * @return uma lista de proxies de Player
	 */
	public List<Player> requestPlayerList();

	public void addObserver(Observer o);

	public Response sendError(String errorMsg);

	public Response sendInfo(String infoMsg);

	public Response sendInfo(String infoMsg, boolean toScreen);
}
