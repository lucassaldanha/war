package war.ui.component.interaction;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.component.AbstractComponent;

public abstract class MovementComponent extends AbstractComponent {

	public void setPosition(int x, int y) {
		Vector2f position = new Vector2f(x, y);
		owner.setPosition(position);
	}

	@Override
	public abstract void update(GameContainer gc, StateBasedGame sb, int delta);

}
