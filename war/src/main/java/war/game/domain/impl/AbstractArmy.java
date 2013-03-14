package war.game.domain.impl;

import war.game.board.movement.ArmyMovement;
import war.game.domain.Army;

public abstract class AbstractArmy implements Army {

	private ArmyMovement movementType;
	private Integer qnty;

	public AbstractArmy() {
	}

	public ArmyMovement getMovementType() {
		return movementType;
	}

	public void setMovementType(ArmyMovement movementType) {
		this.movementType = movementType;
	}

	public Integer getQnty() {
		return qnty;
	}

	public void setQnty(Integer qnty) {
		this.qnty = qnty;
	}

}
