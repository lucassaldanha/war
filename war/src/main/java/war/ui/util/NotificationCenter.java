package war.ui.util;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import war.ui.component.interaction.ClickableComponent;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;

public class NotificationCenter {

	private Nifty nifty = null;
	private Screen screen = null;

	protected Element errorMsg = null;

	private Element lastInfo = null;

	static final ExecutorService service = Executors.newFixedThreadPool(1);

	public NotificationCenter(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
	}

	public void showInfoMessage(final String msg, final double secondsToShow) {

		if (lastInfo != null && lastInfo.isVisible()) {
			nifty.removeElement(screen, lastInfo);
		}

		Element topPanelGUI = screen.findElementByName("top_panel");

		Element infoMsg = new PanelBuilder("info_panel"
				+ UUID.randomUUID().toString()) {
			{
				childLayoutCenter();
				alignCenter();
				valignTop();
				paddingTop("10px");
				width("40%");
				height("90px");
				style("nifty-panel");
				interactOnClick("dimissMsg(info)");
				text(new TextBuilder("infoMsg_text") {
					{
						childLayoutCenter();
						font("./data/font/calibri_18.fnt");
						width("90%");
						height("90%");
						wrap(true);
						alignCenter();
						text(msg);
					}
				});
			}
		}.build(nifty, screen, topPanelGUI);

		topPanelGUI.addChild(infoMsg);

		infoMsg.findElementById("infoMsg_text")
				.getRenderer(TextRenderer.class).setText(msg);

		infoMsg.setVisible(true);

		lastInfo = infoMsg;

		/*
		 * Executa um contador para fechar a mensagem de informação
		 */
		service.execute(new PanelTimer(infoMsg, secondsToShow));
	}

	public void showErrorMsg(final String msg) {
		Element midPanelGUI = screen.findElementByName("error_panel");

		if (errorMsg == null) {

			errorMsg = new PanelBuilder("error_panel") {
				{
					childLayoutCenter();
					alignCenter();
					width("30%");
					height("45%");
					// paddingBottom("30px");
					style("nifty-panel");
					text(new TextBuilder("errorMsg_text") {
						{
							childLayoutCenter();
							font("./data/font/calibri_18.fnt");
							width("90%");
							height("90%");
							wrap(true);
							alignCenter();
							text(msg);
						}
					});
					control(new ButtonBuilder("closeError") {
						{
							childLayoutCenter();
							valignBottom();
							width("100px");
							height("20px");
							label("Ok");
							focusable(false);
							interactOnClick("dimissMsg(error)");
						}
					});
				}
			}.build(nifty, screen, midPanelGUI);

			midPanelGUI.addChild(errorMsg);
		}

		errorMsg.findElementById("errorMsg_text")
				.getRenderer(TextRenderer.class).setText(msg);

		errorMsg.setVisible(true);

		ClickableComponent.disable();
	}

	public void dimissMsg(String type) {
		if (type.equals("info")) {
			// infoMsg.setVisible(false);
		}
		if (type.equals("error")) {
			errorMsg.setVisible(false);
			ClickableComponent.enable();
		}
	}

	/**
	 * Thread que faz um papel de contador para fechar a mensagem de informação
	 * após algum tempo (em segundos) que ela foi exibida
	 * 
	 * @author Lucas
	 * 
	 */
	public class PanelTimer implements Runnable {

		double seconds = 0;
		Element infoMsg = null;

		public PanelTimer(Element infoMsg, double waitSeconds) {
			this.infoMsg = infoMsg;
			this.seconds = waitSeconds;
		}

		@Override
		public void run() {
			long startTime = nifty.getTimeProvider().getMsTime();
			boolean show = true;
			while (show) {
				long showedTime = nifty.getTimeProvider().getMsTime()
						- startTime;
				if (showedTime > (seconds * 1000)) {
					if (infoMsg != null && infoMsg.isVisible()) {
						dimissMsg("info");
						nifty.removeElement(screen, infoMsg);
						show = false;
					}
				}
			}
		}
	}
}
