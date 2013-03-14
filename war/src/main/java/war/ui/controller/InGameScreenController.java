package war.ui.controller;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import war.WarGameGUI;
import war.game.GameController;
import war.game.GameEngine;
import war.game.battle.BattleResult;
import war.game.domain.Continent;
import war.game.domain.GamePhase;
import war.game.domain.Goal;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.domain.impl.HumanPlayer;
import war.game.round.RoundOperations;
import war.game.util.Response;
import war.ui.component.AbstractComponent;
import war.ui.component.interaction.ClickableComponent;
import war.ui.entity.MapEntity;
import war.ui.entity.TerrainEntity;
import war.ui.state.AbstractGameState;
import war.ui.state.GameStates;
import war.ui.util.ArmyColor;
import war.ui.util.DiceImage;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.RadioButtonGroupStateChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;

public class InGameScreenController extends AbstractScreenController implements
		Observer {

	private GameEngine gc = GameController.getInstance();
	private TerrainEntity selectedTerrainEntity = null;
	private TerrainEntity clickedTerrainEntity = null;
	private int selectedArmyAtkRbtn = 1;
	private int selectedArmyMvmtRbtn = 1;

	Element atkDice1 = null;
	Element atkDice2 = null;
	Element atkDice3 = null;
	Element defDice1 = null;
	Element defDice2 = null;
	Element defDice3 = null;

	static DiceImage diceImages = null;

	NiftyImage newImage = null;

	boolean flap_down = true;
	boolean return_down = false;
	boolean new_game_down = false;
	boolean exit_down = false;

	Element exitPopup = null;

	// armies da Statistics
	Element army = null;
	// label da Statistics
	Element exer = null;
	Element terr = null;
	Element cont = null;

	@Override
	public void onStartScreen() {

		exitPopup = nifty.createPopup("exitPopup");

		diceImages = new DiceImage(nifty);

		gc.addObserver(this);

		gc.initGame();

		AbstractGameState ags = (AbstractGameState) WarGameGUI.instance()
				.getCurrentState();

		ags.addEntity(new MapEntity(gc.getBoardOperations()
				.getMapImageFilepath()));

		for (final Terrain t : gc.getBoardOperations().listTerrains()) {
			TerrainEntity te = new TerrainEntity(t);
			AbstractComponent cpnt = (AbstractComponent) te
					.getComponent("clickable");
			cpnt.addObserver(this);
			ags.addEntity(te);
		}

	}

	@Override
	public void onEndScreen() {
	}

	private int getAttackingQnty() {
		return selectedArmyAtkRbtn;
	}

	private int getMovingQnty() {
		return selectedArmyMvmtRbtn;
	}

	@NiftyEventSubscriber(id = "RadioGroup-1")
	public void onRadioGroup1Changed(final String id,
			final RadioButtonGroupStateChangedEvent event) {
		String selectedButton = event.getSelectedId();
		selectedArmyAtkRbtn = Integer.valueOf(selectedButton.replace(
				"atk-option-", ""));
	}

	@NiftyEventSubscriber(id = "RadioGroup-2")
	public void onRadioGroup2Changed(final String id,
			final RadioButtonGroupStateChangedEvent event) {
		String selectedButton = event.getSelectedId();
		selectedArmyMvmtRbtn = Integer.valueOf(selectedButton.replace(
				"mov-option-", ""));
	}

	private void doDeploy() {
		gc.requestAddArmy(clickedTerrainEntity.getTerrain(), 1);
	}

	private void doAttack() {
		Terrain clickedTerrain = clickedTerrainEntity.getTerrain();
		Response result = null;
		if (gc.requestSelectedTerrain() == null) {
			result = gc.requestSelectTerrain(clickedTerrain);
			if (result.equals(Response.OK)) {
				showSelected();
			}
		} else if (clickedTerrain.equals(gc.requestSelectedTerrain())) {
			result = gc.requestDeselectTerrain(clickedTerrain);
			if (result.equals(Response.OK)) {
				showUnselected();
			}
		} else if (!clickedTerrain.equals(gc.requestSelectedTerrain())) {
			gc.requestAttack(clickedTerrainEntity.getTerrain(),
					getAttackingQnty(), getMovingQnty());
		}
	}

	private void doMovement() {
		Terrain clickedTerrain = clickedTerrainEntity.getTerrain();
		Response result = null;
		if (gc.requestSelectedTerrain() == null) {
			result = gc.requestSelectTerrain(clickedTerrain);
			if (result.equals(Response.OK)) {
				clickedTerrainEntity.changeImage();
				selectedTerrainEntity = clickedTerrainEntity;
			}
		} else if (clickedTerrain.equals(gc.requestSelectedTerrain())) {
			result = gc.requestDeselectTerrain(clickedTerrain);
			if (result.equals(Response.OK)) {
				clickedTerrainEntity.changeImage();
				clickedTerrainEntity = null;
				selectedTerrainEntity = null;
			}
		} else if (!clickedTerrain.equals(gc.requestSelectedTerrain())) {
			gc.requestArmyMovement(clickedTerrainEntity.getTerrain(), 1);
		}
	}

	public void endDeploy() {
		if (gc.getRoundOperations().getCurrentPlayer() instanceof HumanPlayer) {
			if (gc.requestCurrentPhase().equals(GamePhase.DEPLOY)) {
				gc.requestAdvancePhase();
			} else {
				nc.showErrorMsg("Você não está na etapa de Deploy.");
			}
		}
	}

	public void endAttack() {
		if (gc.getRoundOperations().getCurrentPlayer() instanceof HumanPlayer) {
			if (gc.requestCurrentPhase().equals(GamePhase.ATTACK)) {
				if (clickedTerrainEntity != null
						&& gc.getBoardOperations().getSelectedTerrain() != null) {
					Response result = gc
							.requestDeselectTerrain(clickedTerrainEntity
									.getTerrain());
					if (result.equals(Response.OK)) {
						clickedTerrainEntity.changeImage();
					}
				}
				gc.requestAdvancePhase();
			} else {
				nc.showErrorMsg("Você não está na etapa de Ataque.");
			}
		}
	}

	public void endRound() {
		if (gc.getRoundOperations().getCurrentPlayer() instanceof HumanPlayer) {
			if (gc.requestCurrentPhase().equals(GamePhase.MOVE)) {
				if (clickedTerrainEntity != null) {
					Response result = gc
							.requestDeselectTerrain(clickedTerrainEntity
									.getTerrain());
					if (result.equals(Response.OK)) {
						clickedTerrainEntity.changeImage();
					}
				}
				gc.requestAdvancePhase();
			} else {
				nc.showErrorMsg("Você não está na etapa de Remanejamento.");
			}
		}
	}

	// Army + Exércitos + Territórios + Continentes
	public void showPlayersStat(ArmyColor color, int num1, int num2, int num3) {
		army = screen.findElementByName(String.format("army_%s_stat",
				color.getName()));
		newImage = nifty.getRenderEngine().createImage(
				String.format("./data/army/standby_army_%s.png",
						color.getName()), false);
		army.getRenderer(ImageRenderer.class).setImage(newImage);
		army.setVisible(true);
		exer = screen.findElementByName(String.format("army_%s_exer",
				color.getName()));
		exer.getRenderer(TextRenderer.class).setText(
				"    E " + String.valueOf(num1));
		exer.setVisible(true);
		terr = screen.findElementByName(String.format("army_%s_terr",
				color.getName()));
		terr.getRenderer(TextRenderer.class).setText(
				"      T " + String.valueOf(num2));
		terr.setVisible(true);
		cont = screen.findElementByName(String.format("army_%s_cont",
				color.getName()));
		cont.getRenderer(TextRenderer.class).setText(
				"      C " + String.valueOf(num3));
		cont.setVisible(true);
	}

	public void showArmyStat(ArmyColor color, boolean show) {
		army = screen.findElementByName(String.format("army_%s_stat",
				color.getName()));
		army.setVisible(show);
		exer = screen.findElementByName(String.format("army_%s_exer",
				color.getName()));
		exer.setVisible(show);
		terr = screen.findElementByName(String.format("army_%s_terr",
				color.getName()));
		terr.setVisible(show);
		cont = screen.findElementByName(String.format("army_%s_cont",
				color.getName()));
		cont.setVisible(show);
	}

	public boolean clickedButton(String name, boolean down) {
		Element button = screen.findElementByName(name);
		Element statistics = screen.findElementByName("statistics");

		if (down) {

			// TODO IMPLEMENTAR PAUSAR JOGO
			statistics.setVisible(true);

			showArmyStat(ArmyColor.BLACK, true);
			showArmyStat(ArmyColor.BLUE, true);
			showArmyStat(ArmyColor.GREEN, true);
			showArmyStat(ArmyColor.RED, true);
			showArmyStat(ArmyColor.WHITE, true);
			showArmyStat(ArmyColor.YELLOW, true);

			// Não tão todos os players na List!
			// Falta gets no player para número de exércitos, territórios e
			// continentes
			List<Player> playerList = gc.requestPlayerList();
			for (Player p : playerList) {
				int armies = countArmies(p);
				int terrain = countTerrains(p);
				int continents = countContinents(p);
				showPlayersStat(p.getColor(), armies, terrain, continents);
			}

			down = false;
		} else {
			newImage = nifty.getRenderEngine().createImage(
					String.format("./data/menu/statistic/%s_down.png", name),
					false);
			button.getRenderer(ImageRenderer.class).setImage(newImage);
			down = true;

			showArmyStat(ArmyColor.BLACK, false);
			showArmyStat(ArmyColor.BLUE, false);
			showArmyStat(ArmyColor.GREEN, false);
			showArmyStat(ArmyColor.RED, false);
			showArmyStat(ArmyColor.WHITE, false);
			showArmyStat(ArmyColor.YELLOW, false);

			statistics.setVisible(false);
			// IMPLEMENTAR RETORNAR AO JOGO
		}

		return down;
	}

	public void setReturn() throws InterruptedException {
		return_down = overButton("return", return_down, "in-game");
	}

	public void returnGame() {
		// IMPLEMENTAR RETORNAR AO JOGO

		ClickableComponent.enable();

		// sumindo com os botoes do menu in-game
		screen.findElementByName("paused").setVisible(false);
		screen.findElementByName("return").setVisible(false);
		screen.findElementByName("new_game").setVisible(false);
		screen.findElementByName("exit").setVisible(false);
	}

	public void setNewGame() throws InterruptedException {
		new_game_down = overButton("new_game", new_game_down, "in-game");
	}

	public void newGame() {
		// IMPLEMENTAR COMEÇAR NOVO JOGO

		WarGameGUI.instance().enterState(GameStates.INITIAL_STATE);
	}

	public void setExit() throws InterruptedException {
		exit_down = overButton("exit", exit_down, "in-game");
	}

	public void exitGame() {
		if (exit_down) {
			nifty.showPopup(screen, exitPopup.getId(), null);
			System.out.println("Clicked exit game!");
		}
	}

	public void cancelExit() {
		nifty.closePopup(exitPopup.getId());
	}

	public void confirmExit() {
		WarGameGUI.instance().getContainer().exit();
	}

	public void showStatistics() {
		flap_down = clickedButton("flap", flap_down);
	}

	public void showGoal() {
		Goal g = gc.requestPlayerGoal();
		nc.showErrorMsg(g.getDescription());
	}

	// Menu In-game
	public void showMenuInGame() {
		// IMPLEMENTAR PAUSAR JOGO

		ClickableComponent.disable();

		// mostrando os botoes do menu in-game
		screen.findElementByName("paused").setVisible(true);
		screen.findElementByName("return").setVisible(true);
		screen.findElementByName("new_game").setVisible(true);
		screen.findElementByName("exit").setVisible(true);
	}

	private void showDices(BattleResult result) {
		clearDices();

		Integer[] attkDices = result.getAttackingDiceResults();
		Integer[] defDices = result.getDefendingDiceResults();

		if (attkDices.length == 1) {
			atkDice1 = screen.findElementByName("atk-dice-1");
			atkDice1.getRenderer(ImageRenderer.class).setImage(
					diceImages.getAtkImageDice(attkDices[0]));
			atkDice1.setVisible(true);
		} else if (attkDices.length == 2) {
			atkDice1 = screen.findElementByName("atk-dice-1");
			atkDice1.getRenderer(ImageRenderer.class).setImage(
					diceImages.getAtkImageDice(attkDices[0]));
			atkDice1.setVisible(true);

			atkDice2 = screen.findElementByName("atk-dice-2");
			atkDice2.getRenderer(ImageRenderer.class).setImage(
					diceImages.getAtkImageDice(attkDices[1]));
			atkDice2.setVisible(true);
		} else if (attkDices.length == 3) {
			atkDice1 = screen.findElementByName("atk-dice-1");
			atkDice1.getRenderer(ImageRenderer.class).setImage(
					diceImages.getAtkImageDice(attkDices[0]));
			atkDice1.setVisible(true);

			atkDice2 = screen.findElementByName("atk-dice-2");
			atkDice2.getRenderer(ImageRenderer.class).setImage(
					diceImages.getAtkImageDice(attkDices[1]));
			atkDice2.setVisible(true);

			atkDice3 = screen.findElementByName("atk-dice-3");
			atkDice3.getRenderer(ImageRenderer.class).setImage(
					diceImages.getAtkImageDice(attkDices[2]));
			atkDice3.setVisible(true);
		}

		if (defDices.length == 1) {
			defDice1 = screen.findElementByName("def-dice-1");
			defDice1.getRenderer(ImageRenderer.class).setImage(
					diceImages.getDefImageDice(defDices[0]));
			defDice1.setVisible(true);
		} else if (defDices.length == 2) {
			defDice1 = screen.findElementByName("def-dice-1");
			defDice1.getRenderer(ImageRenderer.class).setImage(
					diceImages.getDefImageDice(defDices[1]));
			defDice1.setVisible(true);

			defDice2 = screen.findElementByName("def-dice-2");
			defDice2.getRenderer(ImageRenderer.class).setImage(
					diceImages.getDefImageDice(defDices[0]));
			defDice2.setVisible(true);

		} else if (defDices.length == 3) {
			defDice1 = screen.findElementByName("def-dice-1");
			defDice1.getRenderer(ImageRenderer.class).setImage(
					diceImages.getDefImageDice(defDices[2]));
			defDice1.setVisible(true);

			defDice2 = screen.findElementByName("def-dice-2");
			defDice2.getRenderer(ImageRenderer.class).setImage(
					diceImages.getDefImageDice(defDices[1]));
			defDice2.setVisible(true);

			defDice3 = screen.findElementByName("def-dice-3");
			defDice3.getRenderer(ImageRenderer.class).setImage(
					diceImages.getDefImageDice(defDices[0]));
			defDice3.setVisible(true);
		}

	}

	private void showSelected() {
		clickedTerrainEntity.changeImage();
		selectedTerrainEntity = clickedTerrainEntity;
	}

	private void showUnselected() {
		clickedTerrainEntity.changeImage();
		clickedTerrainEntity = null;
		selectedTerrainEntity = null;
	}

	private void clearSelection() {
		if (gc.getBoardOperations().getSelectedTerrain() != null) {
			Response status = gc.requestDeselectTerrain(gc.getBoardOperations()
					.getSelectedTerrain());

			if (status.equals(Response.OK)) {
				if (this.selectedTerrainEntity != null) {
					this.selectedTerrainEntity.changeImage();
					this.selectedTerrainEntity = null;
				}
				if (this.clickedTerrainEntity != null) {
					this.clickedTerrainEntity.changeImage();
					this.clickedTerrainEntity = null;
				}
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GameEngine) {
			if (arg instanceof Response) {
				Response resp = (Response) arg;
				if (resp.equals(Response.OK)) {
					showInfoMessage(resp.getMessage());
				} else if (resp.equals(Response.ERROR)) {
					nc.showErrorMsg(resp.getMessage());
				}

			} else if (arg instanceof BattleResult) {
				BattleResult result = (BattleResult) arg;
				showDices(result);
			}

			else if (arg instanceof GamePhase) {
				clearSelection();
				clearDices();
			}

			else if (arg instanceof RoundOperations) {
				nc.showErrorMsg("Fim de jogo!");
				// TODO vai para tela de fim de jogo!
			}

		} else if (o instanceof ClickableComponent) {
			clickedTerrainEntity = (TerrainEntity) arg;
			if (gc.requestCurrentPhase().equals(GamePhase.DEPLOY)) {
				doDeploy();
			} else if (gc.requestCurrentPhase().equals(GamePhase.ATTACK)) {
				doAttack();
			} else if (gc.requestCurrentPhase().equals(GamePhase.MOVE)) {
				doMovement();
			}
		}
	}

	private void clearDices() {
		if (atkDice1 != null) {
			atkDice1.setVisible(false);
		}
		if (atkDice2 != null) {
			atkDice2.setVisible(false);
		}
		if (atkDice3 != null) {
			atkDice3.setVisible(false);
		}
		if (defDice1 != null) {
			defDice1.setVisible(false);
		}
		if (defDice2 != null) {
			defDice2.setVisible(false);
		}
		if (defDice3 != null) {
			defDice3.setVisible(false);
		}
	}

	public class DiceTimer implements Runnable {

		double seconds = 0;
		Element[] elements = null;

		public DiceTimer(double waitSeconds, Element... elements) {
			this.elements = elements;
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
					for (Element e : elements) {
						if (e != null && e.isVisible()) {
							e.setVisible(false);
						}
					}
				}
			}
		}
	}

	private int countArmies(Player p) {
		int armies = 0;

		for (Terrain t : gc.getBoardOperations().getPlayersTerritories(p)) {
			armies += t.getArmies();
		}

		return armies;
	}

	private int countTerrains(Player p) {
		return gc.getBoardOperations().getPlayersTerritories(p).size();
	}

	private int countContinents(Player p) {
		Map<String, Continent> continents = gc.getBoardOperations().getBoard()
				.getContinents();
		int cs = 0;
		for (Continent c : continents.values()) {
			if (c.getOwner() != null && c.getOwner().equals(p)) {
				cs++;
			}
		}

		return cs;
	}
}
