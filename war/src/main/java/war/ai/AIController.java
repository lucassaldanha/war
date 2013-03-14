package war.ai;

import java.util.Observable;

import war.game.GameEngine;
import war.game.board.BoardOperations;
import war.game.domain.Player;
import war.game.round.RoundOperations;

public class AIController implements AIMaster {

	private static final float DEFAULT_THINK_TIME = 1f;

	AIImplementation AI;
	GameEngine ge = null;
	private boolean waitingForAction = false;

	@Override
	public void setGameEngine(GameEngine ge) {
		this.ge = ge;
		ge.addObserver(this);
	}

	@Override
	public GameEngine getGameEngine() {
		return ge;
	}

	public boolean setAIType(int type) {
		switch (type) {
		case 1: {
			AI = new ProbabilisticAI();
			break;
		}
		case 2: {
			AI = new DeterministicAI();
			break;
		}
		case 3: {
			AI = new MulticlassAI();
			break;
		}
		case 4: {
			AI = new HeuristicAI();
			break;
		}
		default: {
			return false;
		}
		}
		return true;
	}

	public boolean setAIMode(int mode) {
		AI.setMode(mode);
		return false;
	}

	public boolean AIattack(BoardOperations mapa) {
		AI.attack(mapa);
		return false;
	}

	public boolean AIredistrib(BoardOperations mapa) {
		AI.redistrib(mapa);
		return false;
	}

	public boolean AIsendtroop(BoardOperations mapa) {
		AI.sendtroop(mapa, ge.getRoundOperations().getCurrentPlayer()
				.getExtraArmies());
		return false;
	}

	public boolean AISetup(int type, int mode, Player owner) {
		setAIType(type);
		AI.setup(mode, owner);
		return false;
	}

	@Override
	public synchronized boolean play() {
		this.waitingForAction = true;
		this.notifyAll();
		return false;
	}

	public static void think() {
		think(DEFAULT_THINK_TIME);
	}

	public static void think(float seconds) {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < (seconds * 1000)) {
		}
	}

	@Override
	public synchronized void run() {
		while (true) {
			if (!waitingForAction) {
				waitPlay();
			}

			think();

			switch (ge.getRoundOperations().getCurrentPhase()) {
			case DEPLOY: {
				AIsendtroop(ge.getBoardOperations());
				break;
			}
			case ATTACK: {
				AIattack(ge.getBoardOperations());
				break;
			}
			case MOVE: {
				AIredistrib(ge.getBoardOperations());
				break;
			}
			}
			waitingForAction = false;

			think();

			ge.requestAdvancePhase();
		}
	}

	public synchronized void waitPlay() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GameEngine) {
			if (arg instanceof RoundOperations) {
				// TODO o que fazer quando acabar o jogo?
				try {
					System.out.println("Recebeu aviso de fim de jogo");
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
