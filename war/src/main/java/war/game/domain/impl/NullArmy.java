package war.game.domain.impl;

import war.game.board.exception.InvalidMovementException;
import war.game.board.movement.ArmyMovement;
import war.game.domain.Army;

public class NullArmy implements Army {

	public ArmyMovement getMovementType() {
		return new ArmyMovement() {

			public boolean canMove(TerrainImpl source, TerrainImpl target)
					throws InvalidMovementException {
				return false;
			}
		};
	}

	public void setMovementType(ArmyMovement movementType) {
	}

	public Integer getQnty() {
		return 0;
	}

	public void setQnty(Integer qnty) {
	}

}
