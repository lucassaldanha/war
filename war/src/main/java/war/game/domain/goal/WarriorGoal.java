package war.game.domain.goal;

import java.util.List;

import war.game.domain.Player;
import war.game.domain.impl.AbstractPlayer;
import war.ui.util.ArmyColor;

public class WarriorGoal extends AbstractGoal {

	private ArmyColor targetColor = null;

	public WarriorGoal(ArmyColor targetColor) {
		this.targetColor = targetColor;
		setDescription("Destruir exércitos " + targetColor.getScreenName());
	}

	@Override
	public boolean isCompleted() {
		List<Player> playerList = gc.getRoundOperations().getPlayerList();
		boolean playerInGame = false;
		for (Player target : playerList) {
			if (target.getColor().equals(targetColor)) {
				playerInGame = true;

				LOG.debug(String.format(
						"[GOAL] %s needs to destroy %s armies (%d left).",
						getPlayer(), target, gc.getBoardOperations()
								.getPlayersTerritories(target).size()));

				if (gc.getBoardOperations().getPlayersTerritories(target)
						.size() == 0) {
					return true;
				}
			}
		}

		if (getPlayer().getColor().equals(targetColor) || !playerInGame) {
			DominationGoal newGoal = new DominationGoal(24);
			newGoal.setPlayer(getPlayer());
			((AbstractPlayer) getPlayer()).setGoal(newGoal);
			LOG.debug(String.format("[GOAL] New %s goal is: %s", getPlayer(),
					getDescription()));
			return getPlayer().getGoal().isCompleted();
		}

		return false;
	}
}
