package war.game.round;

import java.util.List;

import war.game.domain.GamePhase;
import war.game.domain.Player;
import war.game.util.PlayerType;
import war.ui.util.ArmyColor;

public interface RoundOperations {

	public void addplayer(PlayerType type, String playerName, ArmyColor color);

	public void initRoundSystem();

	public void advancePhase();

	public Player getCurrentPlayer();

	public GamePhase getCurrentPhase();

	public List<Player> getPlayerQueue();

	public List<Player> getPlayerList();

}
