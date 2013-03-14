package war.game.domain.impl;

import war.game.board.movement.impl.SimpleArmyMovement;

public class SimpleArmy extends AbstractArmy {

	public SimpleArmy() {
		this(1);
	}

	public SimpleArmy(int qnty) {
		this.setMovementType(new SimpleArmyMovement());
		this.setQnty(qnty);
	}

}
