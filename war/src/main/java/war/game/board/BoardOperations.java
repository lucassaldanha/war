package war.game.board;

import java.util.List;
import java.util.Set;

import war.game.board.exception.BoardSystemException;
import war.game.board.graph.Board;
import war.game.board.util.TerrainType;
import war.game.domain.Player;
import war.game.domain.Terrain;

public interface BoardOperations {

	public void initBoardSystem(String mapFilepath);

	public Board getBoard();

	public Terrain getSelectedTerrain();

	/**
	 * Recupera a lista com todos os territórios do tabuleiro
	 * 
	 * @return Um objeto que representa a lista de todos os territórios do
	 *         tabuleiro
	 */
	public List<Terrain> listTerrains();

	public void addArmy(Player player, Terrain target, int qnty)
			throws BoardSystemException;

	public void moveArmy(Terrain source, Terrain target, int qnty)
			throws BoardSystemException;

	public boolean isNeighbor(Terrain source, Terrain target)
			throws BoardSystemException;

	public Set<Terrain> getNeighbors(Terrain source)
			throws BoardSystemException;

	/**
	 * retorna lista de territórios inimigos ao redor de um território
	 * 
	 * @param terrain
	 * @return
	 */
	public List<Terrain> getEnemyNeighbor(Terrain terrain);

	public Set<Terrain> getPlayersTerritories(Player owner);

	public List<Terrain> getPlayerTerritories(Player playerOwner,
			boolean noCore, boolean noBorder, boolean noIsland, boolean attack);

	/**
	 * Retorna lista de territórios candidatos a atacarem (+1 exército ou Core)
	 * 
	 * @param playerOwner
	 * @return
	 */
	public List<Terrain> getPlayerTerritoriesToAttack(Player playerOwner);

	/**
	 * Procura um caminho entre t1 e t2. <br>
	 * <br>
	 * Para existir um caminho entre t1 e t2 é necessário que seja possível
	 * chegar de t1 até t2 atravessando fronteiras apenas de países do mesmo
	 * dono de t1 e t2.
	 * 
	 * @param t1
	 *            o território de origem da busca
	 * @param t2
	 *            o território de destino da busca
	 * @return <li>verdadeiro, caso haja um caminho entre t1 e t2</li> <li>
	 *         falso, se t1 e t2 forem o mesmo território</li> <li>falso, se t1
	 *         e t2 não pertencerem ao mesmo dono</li> <li>falso, se não existir
	 *         caminho entre t1 e t2</li>
	 * @throws BoardSystemException
	 */
	public boolean hasPathBetween(Terrain t1, Terrain t2);

	/**
	 * Avalia um território para saber se ele é do tipo Core.<br>
	 * Um território deste tipo está cercado por territórios do mesmo dono
	 * (jogador).
	 * 
	 * @param terrain
	 *            o terreno que está sendo avaliado.
	 * @return
	 * @throws BoardSystemException
	 *             caso haja algum erro durante a manipulação do grafo do
	 *             tabuleiro.
	 */
	public boolean isCore(Terrain terrain) throws BoardSystemException;

	/**
	 * Avalia um território para saber se ele é do tipo Island.<br>
	 * Um território deste tipo está cercado por territórios de outro dono
	 * (jogador).
	 * 
	 * @param terrain
	 *            o terreno que está sendo avaliado.
	 * @return
	 * @throws BoardSystemException
	 *             caso haja algum erro durante a manipulação do grafo do
	 *             tabuleiro.
	 */
	public boolean isIsland(Terrain terrain) throws BoardSystemException;

	/**
	 * Avalia um território para saber se ele é do tipo Border.<br>
	 * Um território é do tipo Border se ele não é do tipo Core nem do tipo
	 * Island
	 * 
	 * @param terrain
	 *            o terreno que está sendo avaliado.
	 * @return
	 * @throws BoardSystemException
	 *             caso haja algum erro durante a manipulação do grafo do
	 *             tabuleiro.
	 */
	public boolean isBorder(Terrain terrain) throws BoardSystemException;

	/**
	 * Retorna o tipo de território (Core, Island ou Border), representado por
	 * um inteiro conforme definido na classe {@link TerrainType}.<br>
	 * <br>
	 * Os tipos possíveis de retorno são:<br>
	 * <li>0 (zero), caso o território seja do tipo Border</li> <li>1 (um), caso
	 * o território seja do tipo Core</li> <li>2 (dois), caso o território seja
	 * do tipo Island</li><br>
	 * <br>
	 * 
	 * @param terrain
	 *            o território que está sendo avaliado.
	 * @return um inteiro representando o tipo de território, -1, caso o
	 *         território informado seja nulo
	 * @throws BoardSystemException
	 *             caso haja algum erro durante a manipulação do grafo do
	 *             tabuleiro.
	 */
	public int getTerrainType(Terrain terrain) throws BoardSystemException;

	public String getMapImageFilepath();
}
