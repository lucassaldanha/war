package war.ui.component.interaction;

import de.lessvoid.nifty.input.NiftyInputEvent;

public interface KeyInputHandler {
/**
 * handle a key event.
 * @param inputEvent key event to handle
 * @return true when the event has been consume0d and false if not
 */
	boolean keyEvent(NiftyInputEvent inputEvent);
}
