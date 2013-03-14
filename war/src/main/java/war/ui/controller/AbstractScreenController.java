package war.ui.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import war.ui.util.NotificationCenter;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public abstract class AbstractScreenController implements ScreenController {

	protected Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	protected Nifty nifty = null;
	protected Screen screen = null;

	protected NotificationCenter nc = null;

	protected Element errorMsg = null;
	// protected Element infoMsg = null;		
	Element popup = null;
	
	NiftyImage newImage = null;

	static final ExecutorService service = Executors.newFixedThreadPool(1);

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		this.nc = new NotificationCenter(nifty, screen);
	}

	protected void showInfoMessage(final String msg) {
		nc.showInfoMessage(msg, 1.5);
	}

	public void dimissMsg(String type) {
		nc.dimissMsg(type);
	}

	protected class menuItem {
		public String id;
		public String name;

		public menuItem(String id, String name) {
			this.id = id;
			this.name = name;
		}
	}
	
	//Muda a cor dos botoes do menu ao passar o cursor do mouse por cima	
	public boolean overButton(String name, boolean down, String menu){
		Element button = screen.findElementByName(name);
		if(!down){
			newImage = nifty.getRenderEngine().createImage(String.format("./data/menu/%s/button_%s_down.png", menu, name), false);
			button.getRenderer(ImageRenderer.class).setImage(newImage);
			down = true;			
		}
		else{
			newImage = nifty.getRenderEngine().createImage(String.format("./data/menu/%s/button_%s.png", menu, name), false);			
			button.getRenderer(ImageRenderer.class).setImage(newImage);			
			down = false;
		}	
		return down;
	}

}
