package war.ai;

import war.game.board.BoardOperations;
import war.game.domain.Player;

public interface AIImplementation {

	public boolean setMode(int mode);
	public boolean attack(BoardOperations mapa);
	public boolean redistrib(BoardOperations mapa);
	public boolean sendtroop(BoardOperations mapa, int num);
	public void setup(int mode, Player owner);
}
