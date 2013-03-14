package war.game.domain;

import war.game.board.movement.ArmyMovement;

public interface Army {

	public ArmyMovement getMovementType();

	public void setMovementType(ArmyMovement movementType);

	public Integer getQnty();

	public void setQnty(Integer qnty);

}
