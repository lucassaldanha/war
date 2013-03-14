package war.game.command;

import war.game.domain.Terrain;
import war.game.util.Response;

public class AttackCommand extends GameControllerCommand {

	Terrain source;
	Terrain target;
	int attackQnty;

	public AttackCommand(Terrain source, Terrain target) {
		this.source = source;
		this.target = target;
	}

	public AttackCommand(Terrain source, Terrain target, int attackingQnty) {
		this(source, target);
		this.attackQnty = attackingQnty;
	}

	@Override
	public Response execute() {
		throw new UnsupportedOperationException();
	}

}
