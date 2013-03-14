package war.game.board.territories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.domain.impl.NullPlayer;
import war.game.domain.impl.SimpleArmy;
import war.game.domain.impl.TerrainImpl;

public class TerritoryOccupation {

	/**
	 * Este método distribui territórios entre jogadores. Inicialmente,
	 * distribui sororalmente os territórios entre os jogadores. Posteriormente,
	 * distribui randomicamente os territórios restantes (caso existam) entre os
	 * jogadores. <br>
	 * 
	 * @param players
	 *            Lista de jogadores
	 * @param terrains
	 *            Lista de terrenos
	 * @author wladimir
	 */
	public static void TerrainDistributor(List<Player> players,
			List<Terrain> terrains) {
		Set<Terrain> unsorted = new HashSet<Terrain>(terrains);
		int max = terrains.size() - (terrains.size() % players.size());
		Terrain t;
		// Sorteio sororal dos territórios
		for (Player player : players) {
			int i = 1;
			while (i <= max / players.size()) {
				// do {
				t = terrains.get((int) (Math.random() * terrains.size()));
				if (unsorted.contains(t)) {
					((TerrainImpl) t).setOwner(player);
					unsorted.remove(t);
					i++;
				}
				// } while (!(t.getOwner() instanceof NullPlayer || t.getOwner()
				// == null));

			}
		}
		// Sorteio randômico dos territórios restantes (se existirem)
		for (Terrain terrain : unsorted) {
			if (terrain.getOwner() instanceof NullPlayer) {
				Player player = players.get((int) (Math.random() * players
						.size()));
				((TerrainImpl) terrain).setOwner(player);
			}
		}
	}

	/**
	 * Este método preenche os territórios com tropas aleatoriamente. <br>
	 * 
	 * @param terrains
	 *            Lista de terrenos
	 * @param max
	 *            Quantidade máxima de tropas
	 * @param min
	 *            Quantidade mínima de tropas
	 * @author wladimir
	 */
	public static void fillTerritory(List<Terrain> terrains, int max, int min) {
		for (Terrain terrain : terrains) {
			((TerrainImpl) terrain).setArmies(new SimpleArmy((int) (Math
					.random() * max + min)));
		}
	}
}
