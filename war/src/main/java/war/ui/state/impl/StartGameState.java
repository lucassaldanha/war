package war.ui.state.impl;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.state.AbstractGameState;
import war.ui.state.GameStates;

public class StartGameState extends AbstractGameState {

	@Override
	public void enterState(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enterState(container, game);
		getNifty().gotoScreen("start");
	}

	@Override
	public int getID() {
		return GameStates.INITIAL_STATE;
	}

}
