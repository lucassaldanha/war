package war.game.board.builder.impl;

import java.util.ArrayList;

import war.game.domain.impl.TerrainImpl;

public class TerrainAuxiliaryOperations {

	protected static TerrainImpl getTerrain(String name, ArrayList<TerrainImpl> terrains) {
		for (TerrainImpl terrain : terrains)
			if (terrain.getName().equals(name))
				return terrain;
		return null;
	}

}
