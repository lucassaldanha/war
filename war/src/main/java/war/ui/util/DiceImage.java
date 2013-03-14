package war.ui.util;

import java.util.HashMap;
import java.util.Map;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.render.NiftyImage;

public class DiceImage {

	public static Map<Integer, NiftyImage> atkDices = new HashMap<Integer, NiftyImage>();
	public static Map<Integer, NiftyImage> defDices = new HashMap<Integer, NiftyImage>();

	public DiceImage(Nifty nifty) {
		atkDices.put(1, nifty.getRenderEngine().createImage("./data/dice/red1.png", false));
		atkDices.put(2, nifty.getRenderEngine().createImage("./data/dice/red2.png", false));
		atkDices.put(3, nifty.getRenderEngine().createImage("./data/dice/red3.png", false));
		atkDices.put(4, nifty.getRenderEngine().createImage("./data/dice/red4.png", false));
		atkDices.put(5, nifty.getRenderEngine().createImage("./data/dice/red5.png", false));
		atkDices.put(6, nifty.getRenderEngine().createImage("./data/dice/red6.png", false));
		
		defDices.put(1, nifty.getRenderEngine().createImage("./data/dice/yellow1.png", false));
		defDices.put(2, nifty.getRenderEngine().createImage("./data/dice/yellow2.png", false));
		defDices.put(3, nifty.getRenderEngine().createImage("./data/dice/yellow3.png", false));
		defDices.put(4, nifty.getRenderEngine().createImage("./data/dice/yellow4.png", false));
		defDices.put(5, nifty.getRenderEngine().createImage("./data/dice/yellow5.png", false));
		defDices.put(6, nifty.getRenderEngine().createImage("./data/dice/yellow6.png", false));
	}

	public NiftyImage getAtkImageDice(int diceValue) {
		return atkDices.get(diceValue);
	}
	
	public NiftyImage getDefImageDice(int diceValue) {
		return defDices.get(diceValue);
	}
}
