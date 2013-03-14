package war.game.domain;

public interface Goal {

	public String getName();

	public String getDescription();

	public Player getPlayer();

	public boolean isCompleted();

}
