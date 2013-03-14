package war.ui.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import war.ui.component.render.ImageRenderComponent;

public class StatisticTableEntity extends Entity {

	ImageRenderComponent irc = null;
	
	public StatisticTableEntity(String statisticsTable) {
		super("statistics_entity");
		
		try {
			this.irc = new ImageRenderComponent(new Image(statisticsTable));
		} catch (SlickException e) {
			e.printStackTrace();
		}

		addComponent(irc);
	}
	

}
