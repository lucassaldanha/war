package war.ui.component.interaction;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;


public class DefaultInputMapping implements NiftyInputMapping {

	public NiftyInputEvent convert(final KeyboardInputEvent inputEvent) {
		if (inputEvent.isKeyDown()) {
			if (inputEvent.getKey() == KeyboardInputEvent.KEY_P) {
				return NiftyStandardInputEvent.ConsoleToggle;
			}
		}
		
		return null;
	}
}
