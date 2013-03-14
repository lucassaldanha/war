package war.game.board.exception;

@SuppressWarnings("serial")
public class InvalidArmyPlacementException extends BoardSystemException {

	public InvalidArmyPlacementException() {
		super("Você não pode colocar exércitos neste território. Quem é o dono desse território?");
	}

}
