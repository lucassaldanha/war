package war.game.domain.goal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import war.game.GameController;
import war.game.domain.Goal;
import war.game.domain.Player;

public abstract class AbstractGoal implements Goal {

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	protected GameController gc = GameController.getInstance();

	private String name;
	private String description;
	private Player player;

	public AbstractGoal() {
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public abstract boolean isCompleted();

}
