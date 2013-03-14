package war;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import war.ui.state.AbstractGameState;
import war.ui.state.impl.EndGameState;
import war.ui.state.impl.InGameState;
import war.ui.state.impl.StartGameState;
import de.lessvoid.nifty.slick2d.NiftyStateBasedGame;

public class WarGameGUI extends NiftyStateBasedGame {

	private static WarGameGUI SOLE_INSTANCE = null;

	private AppGameContainer container;

	private WarGameGUI() {
		super("War", true);
	}

	public AppGameContainer getGameContainer() {
		return container;
	}

	public void enterState(AbstractGameState state) {
		enterState(new StartGameState());
	}

	public static WarGameGUI instance() {
		if (SOLE_INSTANCE == null) {
			SOLE_INSTANCE = new WarGameGUI();
		}
		return SOLE_INSTANCE;
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartGameState());
		addState(new InGameState());
		addState(new EndGameState());
	}

	public static void main(String[] args) throws Exception {
		java.util.logging.Logger.getAnonymousLogger().getParent()
				.setLevel(java.util.logging.Level.SEVERE);
		java.util.logging.Logger.getLogger("de.lessvoid.nifty.*").setLevel(
				java.util.logging.Level.SEVERE);

		AppGameContainer app = new AppGameContainer(instance());
		instance().container = app;
		app.setDisplayMode(1024, 600, false);
		app.setTargetFrameRate(60);
		app.setShowFPS(false);
		app.start();
	}

}
