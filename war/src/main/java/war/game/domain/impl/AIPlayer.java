package war.game.domain.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import war.ai.AIController;
import war.ai.AIMaster;
import war.game.GameController;

public class AIPlayer extends AbstractPlayer {

	static ExecutorService ex = Executors.newFixedThreadPool(10);

	AIMaster myAI;

	public AIPlayer(int AIType, int AIMode, int index) {
		myAI = new AIController();
		myAI.AISetup(AIType, AIMode, this);
		setName("AI Player " + index);

		ex.execute(myAI);
	}

	public AIPlayer(int AIType, int AIMode, String name) {
		myAI = new AIController();
		myAI.AISetup(AIType, AIMode, this);
		setName(name);

		ex.execute(myAI);
	}

	public void play() {
		if (myAI.getGameEngine() == null) {
			myAI.setGameEngine(GameController.getInstance());
		}
		myAI.play();
	}

}
