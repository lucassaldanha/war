package war.ui.component.interaction;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.component.Component;
import war.ui.component.render.ImageRenderComponent;

public class MouseMovementComponent extends MovementComponent {

	private boolean canMove = false;
	private int imageSizeX = -1;
	private int imageSizeY = -1;

	public MouseMovementComponent() {
	}

	@Override
	public void beNotified(Component notifier) {
//		if (notifier instanceof ClickableComponent) {
//			canMove = ((ClickableComponent) notifier).isSelected();
//		}

		if (notifier instanceof ImageRenderComponent) {
			imageSizeX = ((ImageRenderComponent) notifier).getImage()
					.getWidth();
			imageSizeY = ((ImageRenderComponent) notifier).getImage()
					.getHeight();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		if (canMove && gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			setPosition(gc.getInput().getMouseX() - imageSizeX / 2, gc
					.getInput().getMouseY() - imageSizeY / 2);
		}
	}

}
