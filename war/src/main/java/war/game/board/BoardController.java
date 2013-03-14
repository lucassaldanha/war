package war.game.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;

import war.game.GameController;
import war.game.board.builder.impl.BasicTerrainBuilder;
import war.game.board.exception.BoardSystemException;
import war.game.board.exception.InvalidArmyPlacementException;
import war.game.board.exception.InvalidMovementException;
import war.game.board.graph.Board;
import war.game.board.territories.TerritoryOccupation;
import war.game.board.util.TerrainType;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.domain.impl.AbstractPlayer;
import war.game.domain.impl.SimpleArmy;
import war.game.domain.impl.TerrainImpl;

public class BoardController implements BoardOperations {

	private static BoardController SOLE_INSTANCE = new BoardController();
	private Board board = null;

	private final String STANDART_MAP_PATH = "./data/map/standart_map.xml";
	private String MAP_IMAGE_PATH = null;

	private GameController gc = GameController.getInstance();

	private static Set<Terrain> visitedTerrains = null;

	private Terrain selectedTerrain = null;

	public BoardController() {
		board = new Board();
	}

	public static BoardController getInstance() {
		return SOLE_INSTANCE;
	}

	public void initBoardSystem() {
		initBoardSystem(STANDART_MAP_PATH);
	}

	public void initBoardSystem(String mapFilepath) {

		MAP_IMAGE_PATH = mapFilepath.replace(".xml", ".png");

		BasicTerrainBuilder tb = new BasicTerrainBuilder(board);
		try {
			tb.buildBoard(mapFilepath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gc.sendInfo(String.format("[SYSTEM] Loaded %s.", mapFilepath));

		List<Terrain> terrainList = new ArrayList<Terrain>();
		terrainList.addAll(board.getGraph().vertexSet());
		TerritoryOccupation.TerrainDistributor(gc.getRoundOperations()
				.getPlayerQueue(), terrainList);

		TerritoryOccupation.TerrainDistributor(gc.getRoundOperations()
				.getPlayerQueue(), listTerrains());

		TerritoryOccupation.fillTerritory(terrainList, 1, 1);
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public Terrain getSelectedTerrain() {
		return this.selectedTerrain;
	}

	public void setSelectedTerrain(Terrain selectedTerrain) {
		this.selectedTerrain = selectedTerrain;
	}

	@Override
	public List<Terrain> listTerrains() {
		List<Terrain> terrainList = new ArrayList<Terrain>();
		terrainList.addAll(board.getGraph().vertexSet());
		return terrainList;
	}

	public void addArmy(Player player, Terrain target, int qntyToAdd)
			throws BoardSystemException {

		// O jogador só pode inserir exércitos em um territórios que seja seu.
		if (target.getOwner().equals(player) == false) {
			throw new InvalidArmyPlacementException();
		}

		// Verifica se o jogador possui a quantidade solicitada de exércitos
		// extra para serem adicionados
		if (player.getExtraArmies() < qntyToAdd) {
			throw new BoardSystemException("Player não tem exércitos suficientes");
		}

		if (target.getArmies() == 0) {
			((TerrainImpl) target).setArmies(new SimpleArmy(qntyToAdd));
		} else {
			((TerrainImpl) target).getArmyObject().setQnty(
					target.getArmies() + qntyToAdd);
		}

		((AbstractPlayer) player).addExtraArmies(-qntyToAdd);
	}

	public void moveArmy(Terrain source, Terrain target, int qnty)
			throws BoardSystemException {

		// O jogador só pode movimentar exércitos para um territórios seu
		if (target.getOwner().equals(source.getOwner()) == false) {
			throw new InvalidMovementException(
					"Você não pode mover exércitos para um território não seu.");
		}

		// O jogador só pode movimentar exércitos entre territórios vizinhos
		if (!hasPathBetween(source, target)) {
			throw new InvalidMovementException(
					"Você só pode mover exércitos entre territórios vizinhos.");
		}

		// O jogador precisa deixar pelo menos um exército em seu território
		if (qnty >= source.getArmies()) {
			throw new InvalidMovementException(
					"Você deve manter pelo menos 1 exército em cada um dos seus território.");
		}

		((TerrainImpl) source).getArmyObject().setQnty(
				source.getArmies() - qnty);
		((TerrainImpl) target).getArmyObject().setQnty(
				target.getArmies() + qnty);
	}

	public Set<Terrain> getNeighbors(Terrain source)
			throws BoardSystemException {
		List<Terrain> neighbors = Graphs.neighborListOf(board.getGraph(),
				source);
		return new HashSet<Terrain>(neighbors);
	}

	public boolean isNeighbor(Terrain source, Terrain target)
			throws BoardSystemException {
		return this.getNeighbors(source).contains(target);
	}

	/**
	 * Retorna a distância entre dois territórios.<br />
	 * A busca é feita através do algoritmo de caminho mínimo de Dijkstra.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public Integer distanceBetween(Terrain source, Terrain target) {
		if (DijkstraShortestPath.findPathBetween(board.getGraph(), source,
				target) == null) {
			return 0;
		}

		return DijkstraShortestPath.findPathBetween(board.getGraph(), source,
				target).size();
	}

	@Override
	public int getTerrainType(Terrain terrain) throws BoardSystemException {
		if (terrain != null) {
			if (isIsland(terrain)) {
				return TerrainType.ISLAND;
			} else if (isCore(terrain)) {
				return TerrainType.CORE;
			} else {
				return TerrainType.BORDER;
			}
		} else {
			return -1;
		}
	}

	@Override
	public boolean isCore(Terrain terrain) throws BoardSystemException {
		boolean isCore = true;

		for (Terrain neighbor : getNeighbors(terrain)) {
			if (terrain.getOwner().equals(neighbor.getOwner()) == false) {
				isCore = false;
				break;
			}
		}

		return isCore;
	}

	@Override
	public boolean isIsland(Terrain terrain) throws BoardSystemException {
		boolean isIsland = true;

		for (Terrain neighbor : getNeighbors(terrain)) {
			if (terrain.getOwner().equals(neighbor.getOwner()) == true) {
				isIsland = false;
				break;
			}
		}

		return isIsland;
	}

	@Override
	public boolean isBorder(Terrain terrain) throws BoardSystemException {
		return !(isCore(terrain) || isIsland(terrain));
	}

	@Override
	public List<Terrain> getEnemyNeighbor(Terrain terrain) {
		List<Terrain> enemies = new ArrayList<Terrain>();
		try {
			for (Terrain neighbor : getNeighbors(terrain)) {
				if (!neighbor.getOwner().equals(terrain.getOwner())) {
					enemies.add(neighbor);
				}
			}
		} catch (BoardSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return enemies;
	}

	public List<Terrain> getPlayerTerritoriesToAttack(Player playerOwner) {
		List<Terrain> terrainList = new ArrayList<Terrain>();
		List<Terrain> attackingList = new ArrayList<Terrain>();
		terrainList.addAll(board.getGraph().vertexSet());

		for (Terrain terrain : terrainList) {
			try {
				if (terrain.getOwner().equals(playerOwner)
						&& terrain.getArmies() > 1 && !isCore(terrain)) {
					attackingList.add(terrain);
				}
			} catch (BoardSystemException e) {
				e.printStackTrace();
			}
		}

		return attackingList;
	}

	public boolean hasPathBetween(Terrain t1, Terrain t2) {
		boolean hasPath = false;

		if (t1 == t2) {
			visitedTerrains = null;
			return false;
		}

		if (visitedTerrains == null) {
			visitedTerrains = new HashSet<Terrain>();
		}

		visitedTerrains.add(t1);

		try {
			if (isNeighbor(t1, t2) && t1.getOwner().equals(t2.getOwner())) {
				visitedTerrains = null;
				return true;
			} else {
				for (Terrain t3 : getNeighbors(t1)) {
					if (!visitedTerrains.contains(t3)
							&& t1.getOwner().equals(t3.getOwner())) {
						if (hasPathBetween(t3, t2)) {
							visitedTerrains = null;
							return true;
						}
					}
				}
			}
		} catch (BoardSystemException e) {
			return false;
		}

		if (hasPath) {
			visitedTerrains = null;
		}

		return hasPath;
	}

	@Override
	public Set<Terrain> getPlayersTerritories(Player owner) {
		Set<Terrain> playerTerritories = new HashSet<Terrain>();
		for (Terrain t : listTerrains()) {
			if (t.getOwner().equals(owner)) {
				playerTerritories.add(t);
			}
		}
		return playerTerritories;
	}

	@Override
	public List<Terrain> getPlayerTerritories(Player playerOwner,
			boolean noCore, boolean noBorder, boolean noIsland, boolean attack) {
		List<Terrain> terrainList = new ArrayList<Terrain>();
		List<Terrain> finalList = new ArrayList<Terrain>();
		terrainList.addAll(board.getGraph().vertexSet());

		for (Terrain terrain : terrainList) {
			if (terrain.getOwner().equals(playerOwner)) {
				finalList.add(terrain);
			}
		}

		if (attack) {
			terrainList = finalList;
			finalList.clear();
			for (Terrain terrain : terrainList) {
				try {
					if ((terrain.getArmies() > 1) && !isCore(terrain)) {
						finalList.add(terrain);
					}
				} catch (BoardSystemException e) {
					e.printStackTrace();
				}
			}

		}

		if (noCore) {
			terrainList = finalList;
			finalList.clear();
			for (Terrain terrain : terrainList) {
				try {
					if (!isCore(terrain)) {
						finalList.add(terrain);
					}
				} catch (BoardSystemException e) {
					e.printStackTrace();
				}
			}

		}
		if (noBorder) {
			terrainList = finalList;
			finalList.clear();
			for (Terrain terrain : terrainList) {
				try {
					if (!isBorder(terrain)) {
						finalList.add(terrain);
					}
				} catch (BoardSystemException e) {
					e.printStackTrace();
				}
			}

		}
		if (noIsland) {
			terrainList = finalList;
			finalList.clear();
			for (Terrain terrain : terrainList) {
				try {
					if (!isIsland(terrain)) {
						finalList.add(terrain);
					}
				} catch (BoardSystemException e) {
					e.printStackTrace();
				}
			}

		}

		return finalList;
	}

	public void setGameController(GameController gc) {
		this.gc = gc;
	}

	public String getMapImageFilepath() {
		return MAP_IMAGE_PATH;
	}
}
