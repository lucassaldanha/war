package war.game.domain.impl;

import war.game.domain.Goal;
import war.game.domain.Player;
import war.ui.util.ArmyColor;

public class NullPlayer implements Player {

	public void setName(String name) {
	}

	public String getName() {
		return null;
	}

	@Override
	public int getOrderKey() {
		return -1;
	}

	@Override
	public int getExtraArmies() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof NullPlayer;
	}

	@Override
	public void nextPhase() {
	}

	@Override
	public ArmyColor getColor() {
		return null;
	}

	@Override
	public String getColorName() {
		return null;
	}

	@Override
	public Goal getGoal() {
		return null;
	}

	@Override
	public int compareTo(Player otherPlayer) {
		return -1;
	}

}
