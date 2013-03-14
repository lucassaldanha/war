package war.game.board.movement;

import war.game.board.exception.InvalidMovementException;
import war.game.domain.impl.TerrainImpl;

public interface ArmyMovement {

	public boolean canMove(TerrainImpl source, TerrainImpl target)
			throws InvalidMovementException;

}
