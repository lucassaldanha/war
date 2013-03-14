package war.game.domain;

import org.newdawn.slick.geom.Vector2f;

public interface Terrain {

	public String getName();

	public Player getOwner();

	public int getArmies();

	public Army getArmyObject();

	public Vector2f getPosition();

	public boolean isSelected();

}
