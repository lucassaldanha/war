package war.game.command;

import war.game.GameController;
import war.game.GameEngine;

public abstract class GameControllerCommand implements Command {

	protected GameEngine gc = GameController.getInstance();

}
