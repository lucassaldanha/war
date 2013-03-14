package war.ui.state.impl;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.state.AbstractGameState;
import war.ui.state.GameStates;
import de.lessvoid.nifty.Nifty;

public class EndGameState extends AbstractGameState {

	@Override
	public void enterState(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initGameAndGUI(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveState(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepareNifty(Nifty arg0, StateBasedGame arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderGame(GameContainer arg0, StateBasedGame arg1,
			Graphics arg2) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGame(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return GameStates.FINAL_STATE;
	}

}
