package war.ia;

import war.game.GameController;
import war.game.util.PlayerType;
import war.ui.util.ArmyColor;

public class TestIA {

	public static void main(String[] args) {

		GameController gc = GameController.getInstance();

		gc.requestAddPlayer(PlayerType.COMPUTER_EASY, null, ArmyColor.BLACK);
		gc.requestAddPlayer(PlayerType.COMPUTER_EASY, null, ArmyColor.WHITE);

		gc.initGame();

	}
}
