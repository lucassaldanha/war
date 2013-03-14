package war.ui.component.interaction;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;


public class DefaultInputMapping implements NiftyInputMapping {

	public NiftyInputEvent convert(final KeyboardInputEvent inputEvent) {
//		if (inputEvent.isKeyDown()) {
//			if (inputEvent.getKey() == KeyboardInputEvent.KEY_F1) {
//				return NiftyInputEvent.ConsoleToggle;
//			} else if (inputEvent.getKey() == KeyboardInputEvent.KEY_RETURN) {
//				return NiftyInputEvent.Activate;
//			} else if (inputEvent.getKey() == KeyboardInputEvent.KEY_SPACE) {
//				return NiftyInputEvent.Activate;
//			} else if (inputEvent.getKey() == KeyboardInputEvent.KEY_TAB) {
//				if (inputEvent.isShiftDown()) {
//					return NiftyInputEvent.PrevInputElement;
//				} else {
//					return NiftyInputEvent.NextInputElement;
//				}
//			}
//		}	

		if (inputEvent.isKeyDown()) {
			if (inputEvent.getKey() == KeyboardInputEvent.KEY_P) {
				return NiftyInputEvent.ConsoleToggle;
			}
		}
		
		return null;
	}
}
