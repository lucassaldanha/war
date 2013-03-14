package war.ui.component;

import java.util.Observable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.entity.Entity;

public abstract class AbstractComponent extends Observable implements Component {

	protected String id = "abstract_component";
	protected Entity owner;

	public AbstractComponent(String id) {
		this.id = id;
	}

	public AbstractComponent() {
	}

	public String getId() {
		return id;
	}

	public void setOwnerEntity(Entity entity) {
		this.owner = entity;
	}

	@Override
	public void sendNotification() {
		this.owner.notifyComponents(this);
	}

	@Override
	public void beNotified(Component notifier) {
	}

	public abstract void update(GameContainer gc, StateBasedGame sb, int delta);

}
