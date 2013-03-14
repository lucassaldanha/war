package war.ui.entity;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import war.game.GameController;
import war.game.domain.Terrain;
import war.game.domain.impl.TerrainImpl;
import war.ui.component.interaction.ClickableComponent;
import war.ui.component.render.ArmyImageRenderComponent;

public class TerrainEntity extends Entity implements Observer {

	public Terrain terrain = null;
	ArmyImageRenderComponent cc = null;
	ClickableComponent cl = null;

	public TerrainEntity(Terrain terrain) {
		super(terrain.getName());
		this.terrain = terrain;
		this.setPosition(terrain.getPosition());

		try {
			this.cc = new ArmyImageRenderComponent(this, new Image(
					String.format("./data/army/standby_army_%s.png", terrain
							.getOwner().getColor())));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		addComponent(cc);

		this.cl = new ClickableComponent(this);
		addComponent(cl);

		((TerrainImpl) terrain).addObserver(this);
	}

	public void changeImage() {
		try {
			if (this.terrain.equals(GameController.getInstance()
					.requestSelectedTerrain())) {
				this.cc.setImage(new Image(String.format(
						"./data/army/selected_army_%s.png", terrain.getOwner()
								.getColor())/* , Color.white */));
			} else {
				this.cc.setImage(new Image(String.format(
						"./data/army/standby_army_%s.png", terrain.getOwner()
								.getColor())/* , Color.white */));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean showName() {
		return cl.isMouseOver();
	}

	public void checkCounter() {
	}

	public Terrain getTerrain() {
		return terrain;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TerrainImpl) {
			changeImage();
			checkCounter();
		}
	}
}
