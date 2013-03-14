package war.ui.entity;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.component.Component;
import war.ui.component.render.RenderComponent;

public class Entity {

	private String id;
	private Vector2f position;
	private boolean selected = false;

	private RenderComponent renderComponent = null;
	private ArrayList<Component> components = null;

	public Entity(String id) {
		this.id = id;
		components = new ArrayList<Component>();
		position = new Vector2f(0, 0);
	}

	public void addComponent(Component component) {
		if (RenderComponent.class.isInstance(component)
				&& renderComponent == null) {
			renderComponent = (RenderComponent) component;
		}
		component.setOwnerEntity(this);
		components.add(component);
	}

	public Component getComponent(String id) {
		for (Component comp : components) {
			if (comp.getId().equalsIgnoreCase(id))
				return comp;
		}

		return null;
	}

	/**
	 * Método utilizado pelos componentes para se comunicarem
	 * 
	 * @param notifier
	 *            o Component que deseja notificar os outros
	 */
	public void notifyComponents(Component notifier) {
		for (Component c : components) {
			c.beNotified(notifier);
		}
	}

	public Vector2f getPosition() {
		return position;
	}

	public String getId() {
		return id;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void update(GameContainer gc, StateBasedGame sb) {
		for (Component component : components) {
			component.update(gc, sb, 0);
		}
	}

	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		if (renderComponent != null)
			renderComponent.render(gc, sb, gr);
	}

	public RenderComponent getRenderComponent() {
		return renderComponent;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected() {
		selected = true;
	}

	public void setUnselected() {
		selected = false;
	}

}
