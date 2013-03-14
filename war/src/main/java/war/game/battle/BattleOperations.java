package war.game.battle;

import war.game.domain.Terrain;
import war.game.util.GameSystemException;

public interface BattleOperations {

	public BattleResult attack(Terrain source, Terrain target)
			throws GameSystemException;

	public BattleResult attack(Terrain source, Terrain target,
			int attackingArmies) throws GameSystemException;
	
	public BattleResult attack(Terrain source, Terrain target,
			int attackingArmies, int movingArmies) throws GameSystemException;

}
