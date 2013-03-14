package war.game.domain;

import war.ui.util.ArmyColor;

public interface Player extends Comparable<Player> {

	public int getOrderKey();

	public String getName();

	public ArmyColor getColor();

	public String getColorName();

	public int getExtraArmies();

	public Goal getGoal();

	public boolean equals(Object obj);

	public abstract void nextPhase();

}
