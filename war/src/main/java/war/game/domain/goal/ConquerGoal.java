package war.game.domain.goal;

import java.util.Set;

import war.game.domain.Continent;

public class ConquerGoal extends AbstractGoal {

	private Set<Continent> requiredContinents = null;
	private boolean randomOne = false;

	public ConquerGoal(Set<Continent> continents) {
		this(continents, false);
	}

	public ConquerGoal(Set<Continent> continents, boolean randomOne) {
		this.requiredContinents = continents;
		this.randomOne = randomOne;
		String description = "Conquistar: ";
		for (Continent c : continents) {
			description += c.getName() + ", ";
		}
		description = description.substring(0, description.length() - 2);

		if (randomOne) {
			description += " e mais um continente a sua escolha";
		}
		description += ".";

		setDescription(description);
	}

	@Override
	public boolean isCompleted() {
		boolean completed = false;
		int continents = 0;

		for (Continent c : requiredContinents) {
			if (c.getOwner() != null && c.getOwner().equals(getPlayer())) {
				continents++;
			}
		}

		if (!randomOne) {
			completed = (continents >= requiredContinents.size());
		} else {
			for (Continent c : gc.getBoardOperations().getBoard()
					.getContinents().values()) {
				if (c.getOwner() != null && c.getOwner().equals(getPlayer())
						&& !requiredContinents.contains(c)) {
					continents++;
				}
			}
			completed = (continents >= (requiredContinents.size() + 1));
		}

		LOG.debug(String
				.format("[GOAL] %s - %s", getPlayer(), getDescription()));

		return completed;

	}
}
