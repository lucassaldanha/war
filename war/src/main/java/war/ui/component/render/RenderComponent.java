package war.ui.component.render;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.component.AbstractComponent;

public abstract class RenderComponent extends AbstractComponent {

	public RenderComponent(String id) {
		this.id = id;
	}

	public abstract void render(GameContainer gc, StateBasedGame sb, Graphics gr);
}
