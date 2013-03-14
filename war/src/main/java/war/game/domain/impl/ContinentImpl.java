package war.game.domain.impl;

import java.util.Set;

import war.game.domain.Continent;
import war.game.domain.Player;
import war.game.domain.Terrain;

public class ContinentImpl implements Continent {

    private String name = null;
    private int bonus = 0;
    private Set<Terrain> terrains = null;

    public ContinentImpl() {
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setBonus(int bonus) {
	this.bonus = bonus;
    }

    public void setTerrains(Set<Terrain> terrains) {
	this.terrains = terrains;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public int getBonus() {
	return bonus;
    }

    @Override
    public Set<Terrain> getTerrains() {
	return terrains;
    }

    @Override
    public Player getOwner() {
	Player candidate = null;
	for (Terrain t : terrains) {
	    if (candidate == null) {
		candidate = t.getOwner();
	    } else {
		if (!candidate.equals(t.getOwner())) {
		    return null;
		}
	    }
	}
	return candidate;
    }

}
