package war.game.domain;

import java.util.Set;

public interface Continent {

    String getName();

    int getBonus();

    Set<Terrain> getTerrains();

    Player getOwner();

}
