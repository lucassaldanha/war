package war.game.domain.impl;

import war.game.GameController;
import war.game.domain.Goal;
import war.game.domain.Player;
import war.ui.util.ArmyColor;

public class AbstractPlayer implements Player {

	private int orderKey;
	private String name;
	private ArmyColor color;
	private int extraArmies = 0;
	private Goal goal = null;

	@Override
	public int getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(int orderKey) {
		this.orderKey = orderKey;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public ArmyColor getColor() {
		return color;
	}

	public void setColor(ArmyColor color) {
		this.color = color;
	}

	@Override
	public String getColorName() {
		return color.getScreenName();
	}

	@Override
	public int getExtraArmies() {
		return extraArmies;
	}

	public void addExtraArmies(int qnty) {
		this.extraArmies += qnty;
	}

	@Override
	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	@Override
	public void nextPhase() {
		GameController.getInstance().requestAdvancePhase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + orderKey;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPlayer other = (AbstractPlayer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderKey != other.orderKey)
			return false;
		return true;
	}

	@Override
	public int compareTo(Player player) {
		if (!this.equals(player)) {
			if (this.getOrderKey() < player.getOrderKey()) {
				return -1;
			} else if (player.getOrderKey() < this.getOrderKey()) {
				return 1;
			} else {
				throw new RuntimeException(
						String.format(
								"Something is wrong... Two different players with the same order key. [%s, %s]",
								this.getName(), player.getName()));
			}
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", name, color.getScreenName()
				.toLowerCase());
	}
}
