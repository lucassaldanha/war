package war.ui.component.render;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ImageRenderComponent extends RenderComponent {

	protected Image image;

	public ImageRenderComponent(Image image) {
		super("image_render_component");
		this.image = image;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		Vector2f pos = owner.getPosition();
		image.draw(pos.x, pos.y);
		sendNotification();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public String toString() {
		return image.toString();
	}
}
