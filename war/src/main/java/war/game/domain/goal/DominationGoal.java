package war.game.domain.goal;

import war.game.domain.Terrain;

public class DominationGoal extends AbstractGoal {

	int qnty = 0;
	int minArmies = 1;

	public DominationGoal(int qnty) {
		this.qnty = qnty;
		setDescription("Conquistar " + qnty + " territórios.");
	}

	public DominationGoal(int qnty, int minArmies) {
		this.qnty = qnty;
		this.minArmies = minArmies;
		setDescription("Conquistar " + qnty + " territórios com pelo menos "
				+ minArmies + " exércitos em cada.");
	}

	@Override
	public boolean isCompleted() {
		boolean minArmiesCheck = false;
		int terrainOk = 0;
		if (minArmies > 1) {
			for (Terrain t : gc.getBoardOperations().getPlayersTerritories(
					getPlayer())) {
				if (t.getArmies() >= minArmies) {
					terrainOk++;
				}
			}
		} else {
			minArmiesCheck = true;
		}

		if (terrainOk >= qnty) {
			minArmiesCheck = true;
		}

		LOG.debug(String.format(
				"[GOAL] %s needs %d Terrains (has %d) with %d armies in each.",
				getPlayer(), qnty, gc.getBoardOperations()
						.getPlayersTerritories(getPlayer()).size(), minArmies));

		return ((gc.getBoardOperations().getPlayersTerritories(getPlayer())
				.size() >= qnty) && (minArmiesCheck));
	}
}
