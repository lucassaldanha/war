package war.ui.state;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.entity.Entity;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState;
import de.lessvoid.nifty.slick2d.input.PlainSlickInputSystem;

public abstract class AbstractGameState extends NiftyOverlayBasicGameState {

	private List<Entity> entities = new ArrayList<Entity>();
	protected GameContainer container;

	static boolean started = false;

	@Override
	public void initGameAndGUI(GameContainer container, StateBasedGame game)
			throws SlickException {
		initNifty(container, game, new PlainSlickInputSystem());
//		getNifty().setDebugOptionPanelColors(true);
	}

	@Override
	protected void prepareNifty(Nifty nifty, StateBasedGame sbg) {
		getNifty().fromXmlWithoutStartScreen("./data/nifty/game_screens.xml");
	}

	@Override
	public void enterState(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void leaveState(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	@Override
	public void renderGame(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		for (Entity e : entities) {
			e.render(container, game, g);
		}
	}

	@Override
	protected void updateGame(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		for (Entity e : entities) {
		    e.update(container, game);
		}
	}

}
