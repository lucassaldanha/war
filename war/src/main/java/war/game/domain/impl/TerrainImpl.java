package war.game.domain.impl;

import java.util.Observable;

import org.newdawn.slick.geom.Vector2f;

import war.game.GameController;
import war.game.domain.Army;
import war.game.domain.Player;
import war.game.domain.Terrain;

public class TerrainImpl extends Observable implements Terrain {

	private String name;
	private Player owner;
	private Army armies;
	private Vector2f position;

	public TerrainImpl() {
	}

	public TerrainImpl(String name) {
		this.name = name;
	}

	public TerrainImpl(String name, Player owner) {
		this(name);
		this.owner = owner;
		this.armies = new NullArmy();
		this.position = new Vector2f(0, 0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
		setChanged();
		notifyObservers();
	}

	public Army getArmyObject() {
		return armies;
	}

	public int getArmies() {
		return armies.getQnty();
	}

	public void setArmies(Army armies) {
		this.armies = armies;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public boolean isSelected() {
		return equals(GameController.getInstance().requestSelectedTerrain());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TerrainImpl other = (TerrainImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s[%d]", getName(), getArmies());
	}

}
