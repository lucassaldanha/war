package war.ai;

import java.util.List;

import war.game.board.BoardOperations;
import war.game.domain.Player;
import war.game.domain.Terrain;

public class DeterministicAI implements AIImplementation {
	int AImode;
	Player playerOwner;
	AttackVars attvar;
	RedistribVars redvar;
	SendTroopVars sendvar;

	@Override
	public boolean setMode(int mode) {
		AImode = mode;
		setVars();
		return false;
	}

	private void setVars() {
		switch (AImode) {
		case 1: {
			break;
		}
		case 2: {
			break;
		}
		}
	}

	@Override
	public boolean attack(BoardOperations mapa) {
		AttackDeter(mapa);
		return false;
	}

	private void AttackDeter(BoardOperations mapa) {
		List<Terrain> bases = mapa.getPlayerTerritoriesToAttack(playerOwner);
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean redistrib(BoardOperations mapa) {
		RedisDeter(mapa);
		return false;
	}

	private void RedisDeter(BoardOperations mapa) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean sendtroop(BoardOperations mapa, int num) {
		SendDeter(mapa);
		return false;
	}

	private void SendDeter(BoardOperations mapa) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setup(int mode, Player owner) {
		AImode = mode;
		playerOwner = owner;
	}

}
