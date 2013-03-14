package war.ui.component.interaction;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;

public interface NiftyInputMapping {
/**
 * Convert the given KeyboardInputEvent into a NiftyInputEvent.
 * @param inputEvent KeyboardInputEvent to convert
 * @return converted NiftInputEvent
 */
	NiftyInputEvent convert(KeyboardInputEvent inputEvent);
}