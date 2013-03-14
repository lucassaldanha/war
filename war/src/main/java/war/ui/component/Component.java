package war.ui.component;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.entity.Entity;

public interface Component {

	public String getId();

	public void setOwnerEntity(Entity entity);

	/**
	 * Cada Entity está associado a um ou mais Component's. Através deste método
	 * Component's de um mesmo Entity podem se comunicar.
	 */
	public void sendNotification();

	/**
	 * Esse método é disparado pelo Entity owner do Component ao receber -de
	 * outro Component um pedido de notificação.
	 * 
	 * @param notifier
	 *            o Component que disparou o pedido de notificação
	 */
	public void beNotified(Component notifier);

	public void update(GameContainer gc, StateBasedGame sb, int delta);
}
