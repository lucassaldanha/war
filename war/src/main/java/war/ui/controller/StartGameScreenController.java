package war.ui.controller;

import java.util.ArrayList;
import java.util.List;

import war.WarGameGUI;
import war.game.GameController;
import war.game.GameEngine;
import war.game.util.PlayerType;
import war.ui.state.GameStates;
import war.ui.util.ArmyColor;
import war.ui.util.ArmySelected;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;

public class StartGameScreenController extends AbstractScreenController {

	Element configurePopup = null;
	Element helpPopup = null;
	Element creditsPopup = null;
	Element exitPopup = null;		
	Element errorPopup = null;		//Cor já selecionada	
	Element errorPopup1 = null;		//Players insuficientes
	Element errorPopup2 = null;		//Nenhum AI Player selecionado
	
//	Music music = null;
	NiftyImage newImage = null;	
	boolean sound_on = true;
	boolean play_down = false;
	boolean help_down = false;
	boolean credits_down = false;
	boolean exit_down = false;
	boolean army_down = false;
	
	// Armies do popup de configuração de um novo jogo
	Element army = null;	
	
	// Opções da AI_levelBar	
	Element AI_bar = null;
	
	
	boolean army_black = false;
	boolean army_blue = false;
	boolean army_green = false;
	boolean army_red = false;
	boolean army_white = false;
	boolean army_yellow = false;
	
	boolean army_blackAI = false;
	boolean army_blueAI = false;
	boolean army_greenAI = false;
	boolean army_redAI = false;
	boolean army_whiteAI = false;
	boolean army_yellowAI = false;
	
	
	
	List<ArmySelected> playersHUMAN = new ArrayList<ArmySelected>();
	List<ArmySelected> playersCOMPUTER_EASY = new ArrayList<ArmySelected>();
	List<ArmySelected> playersCOMPUTER_NORMAL = new ArrayList<ArmySelected>();
	List<ArmySelected> playersCOMPUTER_HARD = new ArrayList<ArmySelected>();

	
	@Override
	public void onStartScreen() {
		configurePopup = nifty.createPopup("configurePopup");
		helpPopup = nifty.createPopup("helpPopup");
		creditsPopup = nifty.createPopup("creditsPopup");
		exitPopup = nifty.createPopup("exitPopup");			
		errorPopup = nifty.createPopup("errorPopup");
		errorPopup1 = nifty.createPopup("errorPopup1");
		errorPopup2 = nifty.createPopup("errorPopup2");
		
//		try {
//			music = new Music("./data/sound/41bd6164eaac5c8541d7d45f5bad-orig.wav");
//		} catch (SlickException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		music.loop();	
	}

	@Override
	public void onEndScreen() {
		GameEngine gc = GameController.getInstance();
		
		if(!playersHUMAN.isEmpty())				initPlayersHuman(gc);
		if(!playersCOMPUTER_EASY.isEmpty())		initPlayersComputer(playersCOMPUTER_EASY, PlayerType.COMPUTER_EASY, gc);
		if(!playersCOMPUTER_NORMAL.isEmpty())	initPlayersComputer(playersCOMPUTER_NORMAL, PlayerType.COMPUTER_NORMAL, gc);
		if(!playersCOMPUTER_HARD.isEmpty())		initPlayersComputer(playersCOMPUTER_HARD, PlayerType.COMPUTER_HARD, gc);
	}
	
	public void initPlayersHuman(GameEngine gc){
		int i = 0;
		while(i < playersHUMAN.size()){
			gc.requestAddPlayer(PlayerType.HUMAN, "Player "+(i+1), playersHUMAN.get(i).getColor());	
			i++;
		}
	}
	
	public void initPlayersComputer(List<ArmySelected> players, PlayerType playerType, GameEngine gc){			
		int i = 0;
		while(i < players.size()){
			gc.requestAddPlayer(playerType, "AI Player "+(i+1), players.get(i).getColor());
			i++;
		}
	}	
	
	//Muda o estado do som settando a imagem do button_sound
	public void setSound() {
		Element sound = screen.findElementByName("sound");		
		if(sound_on){		
			newImage = nifty.getRenderEngine().createImage("./data/menu/initial/button_sound_off.png", false);			
			sound.getRenderer(ImageRenderer.class).setImage(newImage);		
//			music.stop();
			sound_on = false;
		}
		else{
			newImage = nifty.getRenderEngine().createImage("./data/menu/initial/button_sound_on.png", false);			
			sound.getRenderer(ImageRenderer.class).setImage(newImage);
//			music.play();
			sound_on = true;
		}
	}

	public void setPlay()throws InterruptedException {
		play_down = overButton("play", play_down, "initial");
	}
	
	public void configureGame(){
		if(play_down){
			nifty.showPopup(screen, configurePopup.getId(), null);
		}
	}		
	
	public void deselectAI(String level){
		AI_bar = screen.findElementByName(String.format("COMPUTER_%s", level));
		newImage = nifty.getRenderEngine().createImage(String.format("./data/menu/initial/AI_bar_%s_deselected.png", level), false);
		AI_bar.getRenderer(ImageRenderer.class).setImage(newImage);	//deseleciona a parte da barra correspodente à COMPUTER level
		AI_bar.setVisibleToMouseEvents(false);		//não deixa selecionar esse nível novamente		
	}
	
	public void selectAI(String level){
		AI_bar = screen.findElementByName(String.format("COMPUTER_%s", level));
		newImage = nifty.getRenderEngine().createImage(String.format("./data/menu/initial/AI_bar_%s.png", level), false);
		AI_bar.getRenderer(ImageRenderer.class).setImage(newImage);	//seleciona a parte da barra correspodente à COMPUTER level
		AI_bar.setVisibleToMouseEvents(false);	//não deixa deselecionar esse nível novamente		
	}		
	
	//seleciona COMPUTER_NORMAL na AI_levelBar
	public void setNormal(){
		if(playersCOMPUTER_EASY.isEmpty()){		//nenhum AI Player foi selecionado
			nifty.showPopup(screen, errorPopup2.getId(), null);	
		}else{
			deselectAI("EASY");
			selectAI("NORMAL");		
			playersCOMPUTER_NORMAL.addAll(playersCOMPUTER_EASY); 
			playersCOMPUTER_EASY.removeAll(playersCOMPUTER_EASY);
		}
	}	
	
	//COMPUTER_HARD
	public void setHard(){
		if(playersCOMPUTER_NORMAL.isEmpty()){
			nifty.showPopup(screen, errorPopup2.getId(), null);	
		}else{
			deselectAI("NORMAL");
			selectAI("HARD");		
			playersCOMPUTER_HARD.addAll(playersCOMPUTER_NORMAL); 
			playersCOMPUTER_NORMAL.removeAll(playersCOMPUTER_NORMAL);
		}		
	}
	
	public void closeError2(){
		nifty.closePopup(errorPopup2.getId());
	}	
	
	//Verifica se já não foi selecionado um exército da mesma cor
	public boolean checkArmy(ArmyColor color, List<ArmySelected> players){
		int i = 0;
		while(i<players.size()){
			if(players.get(i).getColor() == color){
				System.out.println("Army já selecionado!");				
				return true;
			}
			i++;
		}		
		return false;
	}	
	
	public boolean armyMouseOver(ArmyColor color, boolean down){
		army = screen.findElementByName(String.format("army_%s", color.getName()));
		
		if(!down){
			newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s.png", color.getName()), false);
			army.getRenderer(ImageRenderer.class).setImage(newImage);
			down = true;			
		}
		else{
			newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s_clear.png", color.getName()), false);			
			army.getRenderer(ImageRenderer.class).setImage(newImage);			
			down = false;
		}	
		return down;
	}
	
	public boolean armyAIMouseOver(ArmyColor color, boolean down){
		army = screen.findElementByName(String.format("army_%s_AI", color.getName()));
		
		if(!down){
			newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s.png", color.getName()), false);
			army.getRenderer(ImageRenderer.class).setImage(newImage);
			down = true;			
		}
		else{
			newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s_clear.png", color.getName()), false);			
			army.getRenderer(ImageRenderer.class).setImage(newImage);			
			down = false;
		}	
		return down;
	}
	
	
	//Muda a imagem do army de HUMAN quando clicado no popup de configuração de um novo jogo e adicona aos List correspondentes
	public void setArmyHuman(ArmyColor color){
		army = screen.findElementByName(String.format("army_%s", color.getName()));			
		ArmySelected player = new ArmySelected(army,color);
		playersHUMAN.add(player);		
		newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s.png", color.getName()), false);
		army.getRenderer(ImageRenderer.class).setImage(newImage);				//setta a imagem do army selecionado
		army.setVisibleToMouseEvents(false);		//não deixa deselecionar o army		
	}
	
	//COMPUTER 
	public void setArmyComputer(ArmyColor color, List<ArmySelected> players){
		army = screen.findElementByName(String.format("army_%s_AI", color.getName()));
		ArmySelected player = new ArmySelected(army,color);
		players.add(player);		
		newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s.png", color.getName()), false);
		army.getRenderer(ImageRenderer.class).setImage(newImage);				
		army.setVisibleToMouseEvents(false);	
	}		
	
				
	public void setArmyBlack()throws InterruptedException {	
		if(!checkArmy(ArmyColor.BLACK, playersCOMPUTER_EASY)){		//não foi selecionado em AIPlayers ainda
			setArmyHuman(ArmyColor.BLACK);
		}else{													//já foi selecionado em AIPLayers
			showError();
		}
	}
	
	public void mouseOverBlack(){
		army_black = armyMouseOver(ArmyColor.BLACK, army_black);
	}
	
	public void setArmyBlue()throws InterruptedException {
		if(!checkArmy(ArmyColor.BLUE, playersCOMPUTER_EASY)){			
			setArmyHuman(ArmyColor.BLUE);
		}else{
			showError();
		}
	}
	
	public void mouseOverBlue(){
		army_blue = armyMouseOver(ArmyColor.BLUE, army_blue);
	}
	
	public void setArmyGreen()throws InterruptedException {
		if(!checkArmy(ArmyColor.GREEN, playersCOMPUTER_EASY)){
			setArmyHuman(ArmyColor.GREEN);
		}else{
			showError();
		}
	}
	
	public void mouseOverGreen(){
		army_green = armyMouseOver(ArmyColor.GREEN, army_green);
	}
	
	public void setArmyRed()throws InterruptedException {
		if(!checkArmy(ArmyColor.RED, playersCOMPUTER_EASY)){
			setArmyHuman(ArmyColor.RED);
		}else{
			showError();
		}
	}
	
	public void mouseOverRed(){
		army_red = armyMouseOver(ArmyColor.RED, army_red);
	}
	
	public void setArmyWhite()throws InterruptedException {
		if(!checkArmy(ArmyColor.WHITE, playersCOMPUTER_EASY)){
			setArmyHuman(ArmyColor.WHITE);
		}else{
			showError();
		}
	}
	
	public void mouseOverWhite(){
		army_white = armyMouseOver(ArmyColor.WHITE, army_white);
	}
	
	public void setArmyYellow()throws InterruptedException {
		if(!checkArmy(ArmyColor.YELLOW, playersCOMPUTER_EASY)){
			setArmyHuman(ArmyColor.YELLOW);
		}else{
			showError();
		}
	}
	
    public void mouseOverYellow(){
    	army_yellow = armyMouseOver(ArmyColor.YELLOW, army_yellow);
	}
	
	public void setArmyBlackAI()throws InterruptedException {		//não foi selecionado em Players ainda
		if(!checkArmy(ArmyColor.BLACK, playersHUMAN)){
			setArmyComputer(ArmyColor.BLACK, playersCOMPUTER_EASY);
		}else{														//já foi selecionado em Players
			showError();
		}
	}
	
	public void mouseOverBlackAI(){
		army_blackAI = armyAIMouseOver(ArmyColor.BLACK, army_blackAI);
	}
	
	public void setArmyBlueAI()throws InterruptedException {
		if(!checkArmy(ArmyColor.BLUE, playersHUMAN)){
			setArmyComputer(ArmyColor.BLUE, playersCOMPUTER_EASY);
		}else{
			showError();
		}
	}
	
	public void mouseOverBlueAI(){
		army_blueAI = armyAIMouseOver(ArmyColor.BLUE, army_blueAI);
	}
	
	public void setArmyGreenAI()throws InterruptedException {
		if(!checkArmy(ArmyColor.GREEN, playersHUMAN)){
			setArmyComputer(ArmyColor.GREEN, playersCOMPUTER_EASY);
		}else{
			showError();
		}
	}
    
	public void mouseOverGreenAI(){
		army_greenAI = armyAIMouseOver(ArmyColor.GREEN, army_greenAI);
	}
	
	public void setArmyRedAI()throws InterruptedException {
		if(!checkArmy(ArmyColor.RED, playersHUMAN)){
			setArmyComputer(ArmyColor.RED, playersCOMPUTER_EASY);
		}else{
			showError();
		}
	}
	
	public void mouseOverRedAI(){
		army_redAI = armyAIMouseOver(ArmyColor.RED, army_redAI);
	}
	
	public void setArmyWhiteAI()throws InterruptedException {
		if(!checkArmy(ArmyColor.WHITE, playersHUMAN)){
			setArmyComputer(ArmyColor.WHITE, playersCOMPUTER_EASY);
		}else{
			showError();
		}
	}
	
	public void mouseOverWhiteAI(){
		army_whiteAI = armyAIMouseOver(ArmyColor.WHITE, army_whiteAI);
	}
	
	public void setArmyYellowAI()throws InterruptedException {
		if(!checkArmy(ArmyColor.YELLOW, playersHUMAN)){
			setArmyComputer(ArmyColor.YELLOW, playersCOMPUTER_EASY);
		}
		else{
			showError();
		}
	}
	
	public void mouseOverYellowAI(){
		army_yellowAI = armyAIMouseOver(ArmyColor.YELLOW, army_yellowAI);
	}
	
	//Deseleciona os armies HUMAN selecionados no popup de configuração
	public void clearArmiesHuman(){
		int i = 0;		
		while(i < playersHUMAN.size()){
			ArmyColor color = playersHUMAN.get(i).getColor();
			newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s_clear.png",color.getName()), false);
			Element army = playersHUMAN.get(i).getArmy();			
			army.getRenderer(ImageRenderer.class).setImage(newImage);		
			army.setVisibleToMouseEvents(true);
			Element armyCo = screen.findElementByName(String.format("army_%s_AI", color.getName()));
			armyCo.setVisibleToMouseEvents(true);
			i++;
		}		
		playersHUMAN.removeAll(playersHUMAN);
	}
	
	//COMPUTER
	public void clearArmiesComputer(List<ArmySelected> players){
		int i = 0;		
		while(i < players.size()){
			ArmyColor color = players.get(i).getColor();
			newImage = nifty.getRenderEngine().createImage(String.format("./data/army/standby_army_%s_clear.png", color.getName()), false);
			Element army = players.get(i).getArmy();			
			army.getRenderer(ImageRenderer.class).setImage(newImage);
			army.setVisibleToMouseEvents(true);
			Element armyCo = screen.findElementByName(String.format("army_%s", color.getName()));
			armyCo.setVisibleToMouseEvents(true);
			i++;
		}				
		players.removeAll(players);
	}	
	
	//Limpa AI
	public void clearAI(String level){
		AI_bar = screen.findElementByName(String.format("COMPUTER_%s", level));
		newImage = nifty.getRenderEngine().createImage(String.format("./data/menu/initial/AI_bar_%s_deselected.png", level), false);
		AI_bar.getRenderer(ImageRenderer.class).setImage(newImage);	//seleciona a parte da barra correspodente à COMPUTER level
		AI_bar.setVisibleToMouseEvents(true);	//deixa deselecionar esse nível novamente	
	}
	
	public void clearAI_EASY(){
		AI_bar = screen.findElementByName("COMPUTER_EASY");
		newImage = nifty.getRenderEngine().createImage("./data/menu/initial/AI_bar_EASY", false);
		AI_bar.getRenderer(ImageRenderer.class).setImage(newImage);	//seleciona a parte da barra correspodente à COMPUTER level		
		AI_bar.setVisibleToMouseEvents(true);	//deixa deselecionar esse nível novamente	
	}
		
	public void closeConfigureGame() {			
		if(!playersHUMAN.isEmpty())	{
			clearArmiesHuman();
		}
		if(!playersCOMPUTER_EASY.isEmpty())	{
			clearAI_EASY();
			clearArmiesComputer(playersCOMPUTER_EASY); 
			System.out.println("EASY NÃO VAZIO");
		}
		if(!playersCOMPUTER_NORMAL.isEmpty()) {
			clearAI("NORMAL");
			clearArmiesComputer(playersCOMPUTER_NORMAL);
			System.out.println("NORMAL NÃO VAZIO");
		}
		if(!playersCOMPUTER_HARD.isEmpty())	{
			clearAI("HARD");
			clearArmiesComputer(playersCOMPUTER_HARD); 	
			System.out.println("HARD NÃO VAZIO");
		}		
		
		nifty.closePopup(configurePopup.getId());
	}
	
	public void showError(){
		nifty.showPopup(screen, errorPopup.getId(), null);
	}	
	
	public void closeError(){
		nifty.closePopup(errorPopup.getId());
	}

	public void startGame() {
		if(playersHUMAN.isEmpty()){					//tem q ter pelo menos um playerHUMAN
			nifty.showPopup(screen, errorPopup1.getId(), null);			
		}else if(playersHUMAN.size() < 2 && 		//tem que ter pelo menos dois playersHUMAN OU X playerHUMAN  mais X playersCOMPUTER_*
				playersCOMPUTER_EASY.isEmpty() && 	
				playersCOMPUTER_NORMAL.isEmpty() && 
				playersCOMPUTER_HARD.isEmpty()){
			nifty.showPopup(screen, errorPopup1.getId(), null);
		}		
		else{
			nifty.closePopup(configurePopup.getId());
			WarGameGUI.instance().enterState(GameStates.IN_GAME_STATE);
		}
	}
	
	public void closeError1(){
		nifty.closePopup(errorPopup1.getId());
	}
	
	public void setHelp()throws InterruptedException {
		help_down = overButton("help", help_down, "initial");
	}

	public void showHelp() {
		if(help_down) 
			nifty.showPopup(screen, helpPopup.getId(), null);
	}
	
	public void closeHelp() {
		nifty.closePopup(helpPopup.getId());
	}
	
	public void setCredits()throws InterruptedException {		
		credits_down = overButton("credits", credits_down, "initial");
	}	

	public void showCredits() {
		if(credits_down) 
			nifty.showPopup(screen, creditsPopup.getId(), null);
	}

	public void closeCredits() {
		nifty.closePopup(creditsPopup.getId());
	}
	
	public void setExit()throws InterruptedException {		
		exit_down = overButton("exit", exit_down, "initial");
	}			
	
	public void exitGame() {
		if(exit_down) {
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
}
