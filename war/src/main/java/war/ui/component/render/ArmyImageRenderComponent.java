package war.ui.component.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import war.ui.entity.TerrainEntity;

public class ArmyImageRenderComponent extends ImageRenderComponent {

	TerrainEntity te = null;

	public ArmyImageRenderComponent(TerrainEntity te, Image image) {
		super(image);
		this.te = te;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		gc.getGraphics().setColor(Color.white);
		gc.getGraphics().drawString(
				String.valueOf(te.getTerrain().getArmies()),
				(te.getTerrain().getPosition().x + 20),
				(te.getTerrain().getPosition().y) + 20);

		if (te.showName()) {
			gc.getGraphics().drawString(te.getTerrain().getName(),
					te.getTerrain().getPosition().x - 16,
					te.getTerrain().getPosition().y - 16);
		}

		super.render(gc, sb, gr);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
	}

}
