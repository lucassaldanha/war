package war.game.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import war.game.GameController;
import war.game.board.graph.Board;
import war.game.domain.Continent;
import war.game.domain.Goal;
import war.game.domain.goal.ConquerGoal;
import war.game.domain.goal.DominationGoal;
import war.game.domain.goal.WarriorGoal;
import war.ui.util.ArmyColor;

public class GoalUtils {

	private static GoalUtils SOLE_INSTANCE = null;
	private int TOTAL_GOALS = 0;

	private static List<Goal> gameGoals = new ArrayList<Goal>();

	private GoalUtils() {
		DominationGoal d1 = new DominationGoal(18, 2);
		DominationGoal d2 = new DominationGoal(24);

		Board b = GameController.getInstance().getBoardOperations().getBoard();
		Set<Continent> continents = new HashSet<Continent>();

		// Conquistar na totalidade a EUROPA, a OCEANIA e mais um terceiro.
		continents.add(b.getContinentByName("Europa"));
		continents.add(b.getContinentByName("Oceania"));
		ConquerGoal c1 = new ConquerGoal(continents, true);

		// Conquistar na totalidade a ASIA e a AMÉRICA DO SUL.
		continents = new HashSet<Continent>();
		continents.add(b.getContinentByName("Ásia"));
		continents.add(b.getContinentByName("América do Sul"));
		ConquerGoal c2 = new ConquerGoal(continents);

		// Conquistar na totalidade a EUROPA, a AMÉRICA DO SUL e mais um
		// terceiro.
		continents = new HashSet<Continent>();
		continents.add(b.getContinentByName("Europa"));
		continents.add(b.getContinentByName("América do Sul"));
		ConquerGoal c3 = new ConquerGoal(continents, true);

		// Conquistar na totalidade a ASIA e a ÁFRICA.
		continents = new HashSet<Continent>();
		continents.add(b.getContinentByName("Ásia"));
		continents.add(b.getContinentByName("África"));
		ConquerGoal c4 = new ConquerGoal(continents);

		// Conquistar na totalidade a ASIA e a AMÉRICA DO SUL.
		continents = new HashSet<Continent>();
		continents.add(b.getContinentByName("Ásia"));
		continents.add(b.getContinentByName("América do Sul"));
		ConquerGoal c5 = new ConquerGoal(continents);

		// Conquistar na totalidade a AMÉRICA DO NORTE e a OCEANIA.
		continents = new HashSet<Continent>();
		continents.add(b.getContinentByName("América do Norte"));
		continents.add(b.getContinentByName("Oceania"));
		ConquerGoal c6 = new ConquerGoal(continents);

		// Conquistar na totalidade a AMÉRICA DO NORTE e a ÁFRICA.
		continents = new HashSet<Continent>();
		continents.add(b.getContinentByName("América do Norte"));
		continents.add(b.getContinentByName("África"));
		ConquerGoal c7 = new ConquerGoal(continents);

		WarriorGoal w1 = new WarriorGoal(ArmyColor.BLACK);
		WarriorGoal w2 = new WarriorGoal(ArmyColor.BLUE);
		WarriorGoal w3 = new WarriorGoal(ArmyColor.GREEN);
		WarriorGoal w4 = new WarriorGoal(ArmyColor.RED);
		WarriorGoal w5 = new WarriorGoal(ArmyColor.WHITE);
		WarriorGoal w6 = new WarriorGoal(ArmyColor.YELLOW);

		gameGoals.add(d1);
		gameGoals.add(d2);
		gameGoals.add(w1);
		gameGoals.add(w2);
		gameGoals.add(w3);
		gameGoals.add(w4);
		gameGoals.add(w5);
		gameGoals.add(w6);
		gameGoals.add(c1);
		gameGoals.add(c2);
		gameGoals.add(c3);
		gameGoals.add(c4);
		gameGoals.add(c5);
		gameGoals.add(c6);
		gameGoals.add(c7);

		TOTAL_GOALS = gameGoals.size();
	}

	public static GoalUtils getInstance() {
		if (SOLE_INSTANCE == null) {
			SOLE_INSTANCE = new GoalUtils();
		}
		return SOLE_INSTANCE;
	}

	public Goal getGoal() {
		Goal g = null;
		Collections.shuffle(gameGoals);
		g = gameGoals.get(0);
		gameGoals.remove(g);
		return g;
	}

}
