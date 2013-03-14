package war.ui.component.interaction;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.component.AbstractComponent;
import war.ui.component.Component;
import war.ui.component.render.ImageRenderComponent;
import war.ui.entity.Entity;
import war.ui.entity.TerrainEntity;

public class ClickableComponent extends AbstractComponent {

	static Set<Entity> selectableEntities = new HashSet<Entity>();

	static boolean enabled = true;

	private TerrainEntity owner = null;

	private Image image;
	private Rectangle area;

	private boolean mouseOver;

	public ClickableComponent(TerrainEntity entity) {
		super("clickable");
		this.owner = entity;
		selectableEntities.add(entity);
	}

	@Override
	public void beNotified(Component notifier) {
		if (notifier instanceof ImageRenderComponent) {
			image = ((ImageRenderComponent) notifier).getImage();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		syncPosition();
		if (enabled) {
			Input input = gc.getInput();
			checkMouseOver(input);
			if (mouseOver) {
				checkSelected(input);
			}
		}
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	private void syncPosition() {
		Vector2f position = owner.getPosition();
		if (image != null) {
			area = new Rectangle(position.x, position.y, image.getWidth(),
					image.getHeight());
		}
	}

	private void checkMouseOver(Input input) {
		if (area != null) {
			mouseOver = area.contains(input.getMouseX(), input.getMouseY());
		}
	}

	private void checkSelected(Input input) {
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.setChanged();
			notifyObservers(owner);
		}
	}

	public static void enable() {
		enabled = true;
	}

	public static void disable() {
		enabled = false;
	}
}
