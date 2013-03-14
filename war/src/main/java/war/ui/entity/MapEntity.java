package war.ui.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import war.ui.component.render.ImageRenderComponent;

public class MapEntity extends Entity {

	ImageRenderComponent irc = null;

	public MapEntity(String mapImgFilepath) {
		super("map_entity");

		try {
			this.irc = new ImageRenderComponent(new Image(mapImgFilepath));
		} catch (SlickException e) {
			e.printStackTrace();
		}

		addComponent(irc);
	}

}
