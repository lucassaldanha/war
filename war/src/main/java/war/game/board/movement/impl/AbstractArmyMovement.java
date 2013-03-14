package war.game.board.movement.impl;

import war.game.board.BoardController;
import war.game.board.exception.InvalidMovementException;
import war.game.board.movement.ArmyMovement;
import war.game.domain.impl.TerrainImpl;

public abstract class AbstractArmyMovement implements ArmyMovement {

	// Determina a distância mínima do movimento
	private Integer maxDistance;

	public AbstractArmyMovement(Integer maxDistance) {
		this.maxDistance = maxDistance;
	}

	// Verifica se o exército pode andar a distância solicitada
	public boolean canMove(TerrainImpl source, TerrainImpl target)
			throws InvalidMovementException {
		return (BoardController.getInstance().distanceBetween(source, target) <= this.maxDistance);
	}

}
