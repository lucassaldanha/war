package war.ui.util;

import de.lessvoid.nifty.elements.Element;

public class ArmySelected {

	Element army;
	ArmyColor color;
	
	public ArmySelected(Element army, ArmyColor color){
		this.army = army;
		this.color = color;
	}
	
	public Element getArmy(){
		return army;
	}
	
	public ArmyColor getColor(){
		return color;
	}
	
}
