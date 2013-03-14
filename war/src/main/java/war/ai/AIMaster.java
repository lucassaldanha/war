package war.ai;

import java.util.Observer;

import war.game.GameEngine;
import war.game.board.BoardOperations;
import war.game.domain.Player;

public interface AIMaster extends Runnable, Observer {

	public void setGameEngine(GameEngine ge);

	public GameEngine getGameEngine();

	public boolean setAIType(int type);

	public boolean setAIMode(int mode);

	public boolean AISetup(int type, int mode, Player owner);

	public boolean AIattack(BoardOperations mapa);

	public boolean AIredistrib(BoardOperations mapa);

	public boolean AIsendtroop(BoardOperations mapa);

	public boolean play();
}
