package war.ui.state.impl;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.state.AbstractGameState;
import war.ui.state.GameStates;

public class InGameState extends AbstractGameState {

	@Override
	public void enterState(GameContainer container, StateBasedGame game) {
		super.enterState(container, game);
		getNifty().gotoScreen("in_game");
	}

	@Override
	public int getID() {
		return GameStates.IN_GAME_STATE;
	}
}
